name := "happy-ninjas"

version := "0.1"

scalaVersion := "2.13.5"

val scalacticVersion = "3.2.5"

libraryDependencies ++= Seq(
  "org.scalactic" %% "scalactic" % scalacticVersion,
  "org.scalatest" %% "scalatest" % scalacticVersion % "test"
)