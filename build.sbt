name := "spark_twitter_feed"

version := "0.1"

scalaVersion := "2.11.0"

libraryDependencies ++= Seq(
  
  // Spark
  "org.apache.spark" % "spark-core_2.11" % "2.4.3" % "provided",
  "org.apache.spark" % "spark-streaming_2.11" % "2.4.3" % "provided",
  "org.apache.bahir" %% "spark-streaming-twitter" % "2.4.0",
  "org.apache.spark" % "spark-streaming-kafka-0-10_2.11" % "2.4.3" % "provided",

  //twitter streaming
  "org.twitter4j" % "twitter4j-core" % "4.0.4",
  "org.twitter4j" % "twitter4j-stream" % "4.0.4",

  // Kafka
  "org.apache.kafka" %% "kafka" % "2.3.0",

  // Others
  "com.google.code.gson" % "gson" % "2.8.6"
)

assemblyMergeStrategy in assembly := {
  case PathList("org", "apache", xs @ _*) => MergeStrategy.last
  case x =>
    val oldStrategy = (assemblyMergeStrategy in assembly).value
    oldStrategy(x)
}
