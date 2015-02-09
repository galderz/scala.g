scalaVersion := "2.11.0"

libraryDependencies ++= Seq(
  "org.scalaz" %% "scalaz-core" % "7.0.6",
  "org.scalaz" %% "scalaz-effect" % "7.0.6",
  "org.scalaz" %% "scalaz-typelevel" % "7.0.6",
  "org.scalaz" %% "scalaz-scalacheck-binding" % "7.0.6" % "test",
  "org.scalaz.stream" %% "scalaz-stream" % "0.4.1"
)

resolvers += "Scalaz Bintray Repo" at "http://dl.bintray.com/scalaz/releases"

scalacOptions += "-feature"

initialCommands in console := "import scalaz._, Scalaz._"
