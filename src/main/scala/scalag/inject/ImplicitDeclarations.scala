package scalag.inject

/**
 * Dependency injection using implicit parameters extracted from:
 * http://jonasboner.com/2008/10/06/real-world-scala-dependency-injection-di/
 */
object ImplicitDeclarations {

   def main(args : Array[String]) {
      // import the services into the current scope and the wiring
      // is done automatically using the implicits
      implicit val potSensor = new PotSensor
      implicit val heater = new Heater

      val warmer = new ImplicitWarmer
      warmer.trigger
   }

}

// =======================
// service declaring two dependencies that it wants injected
class ImplicitWarmer (
        implicit val sensor: SensorDevice,
        implicit val onOff: OnOffDevice) {

   def trigger = {
      if (sensor.isCoffeePresent) onOff.on
      else onOff.off
   }
}

