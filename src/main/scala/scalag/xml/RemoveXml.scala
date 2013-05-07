package scalag.xml

import scala.xml.transform.{RuleTransformer, RewriteRule}
import scala.xml.{Elem, NodeSeq, Node}

/**
 * // TODO: Document this
 * @author Galder Zamarreño
 * @since // TODO
 */
object RemoveXml extends App {

   val cfg =
      <server xmlns="urn:jboss:domain:1.4">
         <extensions>
            <extension module="org.jboss.as.logging"/>
            <extension module="org.jboss.as.mail"/>
         </extensions>
         <profile>
            <subsystem xmlns="urn:jboss:domain:logging:1.2">
            </subsystem>
            <subsystem xmlns="urn:jboss:domain:mail:1.0"/>
         </profile>
      </server>

   val removeLoggingExtension = new RewriteRule {
      override def transform(n: Node): NodeSeq = n match {
         case e: Elem if e.label == "extension"
                 && (e \ "@module").text == "org.jboss.as.logging" => NodeSeq.Empty
         case n => n
      }
   }

   val removeLoggingSubsystem = new RewriteRule {
      override def transform(n: Node): NodeSeq = n match {
         case e: Elem if e.label == "subsystem"
                 && e.namespace.contains("jboss:domain:logging") => NodeSeq.Empty
         case n => n
      }
   }

   println(new RuleTransformer(removeLoggingExtension, removeLoggingSubsystem).transform(cfg))

}
