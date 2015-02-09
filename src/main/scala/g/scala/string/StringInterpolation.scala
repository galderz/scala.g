package g.scala.string

import java.util.{Calendar, Date}

/**
 * // TODO: Document this
 * @author Galder Zamarreño
 * @since // TODO
 */
object StringInterpolation {

   val VERSION = 10

   def main(args : Array[String]) {
      val major = 2
      val minor = 10
      val micro = 0

      println("1) format: %s%s%s".format(major, minor, micro))
      println(f"1) interpolation: $major%s$minor%s$micro%s")

      println("2) format: %s_%s".format(major, minor))
      println(f"2) interpolation: $major%s_$minor%s")

      println("3) format: %s".format(Calendar.getInstance().get(1)))
      println(s"3) interpolation: ${Calendar.getInstance().get(1)}")

      println("4) format: /%s/%s/%s".format(major, minor, micro))
      println(raw"4) interpolation: /$major/$minor/$micro")

      println("5) format: /%s/%s/%s".format(major, minor, micro))
      println(s"5) interpolation: /$major/$minor/$micro")

      println("""|-------------------
                | Assemble Escalante
                |-------------------
                | major = %s
                | minor = %s
                | micro = %s
              """.format(major, minor, micro).stripMargin)
      println(s"""|-------------------
                | Assemble Escalante
                |-------------------
                | major = $major
                | minor = $minor
                | micro = $micro
              """.stripMargin)

      println("%1$s/org/jboss/as/jboss-as-dist/%2$s/jboss-as-dist-%2$s.zip"
              .format(major, minor))
      println(s"$major/org/jboss/as/jboss-as-dist/$VERSION/jboss-as-dist-$VERSION.zip")
   }

}
