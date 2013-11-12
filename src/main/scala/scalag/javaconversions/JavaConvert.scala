package scalag.javaconversions

import java.util.concurrent.ConcurrentHashMap
import scala.collection.JavaConversions.mapAsScalaConcurrentMap

/**
 * // TODO: Document this
 * @author Galder Zamarreño
 * @since // TODO
 */
object JavaConvert extends App {

  val javaConcurrentMap = new ConcurrentHashMap[Int, String]()
  javaConcurrentMap.put(1, "v1")
  javaConcurrentMap.put(2, "v2")
  javaConcurrentMap.put(3, "v3")

  val scalaConcurrentMap = mapAsScalaConcurrentMap(javaConcurrentMap)
  println(scalaConcurrentMap)

  javaConcurrentMap.put(4, "v4")
  println(scalaConcurrentMap)
  println(mapAsScalaConcurrentMap(javaConcurrentMap))
}
