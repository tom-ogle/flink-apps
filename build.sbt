name := "flink-app"

version := "1.0"

scalaVersion := "2.11.8"

val flinkVersion = "1.1.4"

libraryDependencies ++= Seq(
  "org.apache.flink" % "flink-scala_2.11" % flinkVersion,
  "org.apache.flink" % "flink-streaming-scala_2.11" % flinkVersion,
  "org.apache.flink" % "flink-clients_2.11" % flinkVersion
)
