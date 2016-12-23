lazy val root = (project in file(".")).
  settings(
    name := "fb-bot",
    version := "1.0-SNAPSHOT",
    scalaVersion := "2.11.7"
  )

libraryDependencies ++= Seq(
  // jdbc,
  // "org.postgresql" % "postgresql" % "9.4-1201-jdbc41",
  "com.typesafe.akka" %% "akka-http" % "10.0.1"
)

libraryDependencies <+= scalaVersion("org.scala-lang" % "scala-compiler" % _ )

// manually set the mainClass for all tasks
mainClass in Compile := Some("fbbot.App")

enablePlugins(JavaAppPackaging)
