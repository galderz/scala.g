package g.scala.iteratee

/**
 * Iteratee examples based on the code in:
 * http://rbsomeg.blogspot.in/2012/12/iteratee-fundamentals-for-beginer.html
 *
 * @author Galder Zamarreño
 */
object IterateesForBeginners {

   trait Input[+E]

   // The enumerator(aka producer) has produced an Empty chunk
   object Empty extends Input[Nothing]

   // The Producer has finished producing
   object EOF extends Input[Nothing]

   //The producer has produced a chunk
   case class Elem[+E](e: E) extends Input[E]

   trait Iteratee[E,+A] {
      def run: A = this match {
         // Run will get us the result computed thus far
         // - sending a EOF to itself if needed
         case Done(a, _) => a
         case Cont(k) => k(EOF).run
      }
   }

   case class Done[E,+A](a: A, next: Input[E]) extends Iteratee[E,A]

   // Cont state holds a function, which given an input, would return a new iteratee instance(Done or Cont)
   case class Cont[E,+A](k: Input[E] => Iteratee[E,A]) extends Iteratee[E,A]

   def enumerate[E,A](produce: List[E], itr:Iteratee[E,A]): Iteratee[E,A] = {
      produce match {
         // No produce - return the Iteratee as it is
         case Nil => itr
         case e :: elems => itr match {//produced an elem/chunk
            case i@Done(_,_) => i//if Done - return current Iteratee
            case Cont(k) => enumerate(elems, k(Elem(e)))//Not yet `Done` continue feeding chunks of produce
         }
      }
   }

   /**
    * An iteratee which returns the head from an enumerator's produce.
    */
   def head[E]: Iteratee[E, Option[E]] = {
      def step[E](in: Input[E]): Iteratee[E, Option[E]] = in match {
         //Got an elem - return a Done iteratee right away
         case Elem(e) => Done(Some(e),Empty)
         //Cont iteratee waiting for an input
         case Empty => Cont(step)
         case EOF => Done(None, EOF)
      }
      Cont(step)
   }

   /**
    * Iteratee that computes the length of the produce of an enumerator.
    */
   def length[E]: Iteratee[E,Int] = {
      def step[E](acc: Int): Input[E] => Iteratee[E,Int] = {
         case Elem(e) => Cont(step(acc+1))
         case Empty => Cont(step(acc))
         case EOF => Done(acc, EOF)
      }
      Cont(step(0))
   }

   /**
    * Iteratee that will return a List containing every alternate element
    * starting with the first.
    */
   def alternate[E]: Iteratee[E, List[E]] = {
      def step(flag: Boolean, xs: List[E]): Input[E] => Iteratee[E, List[E]] = {
         case Elem(e) if(flag) => Cont(step(false,xs ::: List(e)))
         case Elem(e) if(!flag) => Cont(step(true, xs))
         case Empty => Cont(step(flag,xs))
         case EOF => Done(xs, EOF)
      }
      Cont(step(true,Nil))
   }

   def main (args: Array[String]) {
      val v =  enumerate(List(1,2,3), head[Int])
      val result = v.run
      println(result)

      val lenItr = enumerate(List(1,2,3,4,5,6), length[Int])
      val len = lenItr.run
      println(len)

      val altItr = enumerate(List(1,2,3,4,5,6,7), alternate[Int])
      val altList = altItr.run
      println(altList)
   }

}
