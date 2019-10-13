name := "spark_twitter_feed"

version := "0.1"

scalaVersion := "2.11.0"

libraryDependencies ++= Seq(
  "org.apache.spark" % "spark-core_2.11" % "2.4.3",
  "org.apache.spark" % "spark-core_2.11" % "2.4.3",
  "org.apache.spark" % "spark-sql_2.11" % "2.4.3",
  "org.apache.spark" %% "spark-mllib" % "2.4.3",
  "org.apache.bahir" %% "spark-streaming-twitter" % "2.4.0",
  "org.twitter4j" % "twitter4j-core" % "4.0.4",
  "org.twitter4j" % "twitter4j-stream" % "4.0.4"
)

