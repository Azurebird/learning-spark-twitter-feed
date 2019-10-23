name := "spark_twitter_feed"

version := "0.1"

scalaVersion := "2.12.8"

libraryDependencies ++= Seq(
  
  // Spark
  "org.apache.spark" % "spark-core_2.12" % "2.4.4",
  "org.apache.spark" % "spark-streaming_2.12" % "2.4.4",
  "org.apache.bahir" %% "spark-streaming-twitter" % "2.4.0",
  "org.apache.spark" % "spark-streaming-kafka-0-10_2.12" % "2.4.4",

  //twitter streaming
  "org.twitter4j" % "twitter4j-core" % "4.0.4",
  "org.twitter4j" % "twitter4j-stream" % "4.0.4",

  // Kafka
  "org.apache.kafka" %% "kafka" % "2.3.0",

  // Others
  "com.google.code.gson" % "gson" % "2.8.6"
)

