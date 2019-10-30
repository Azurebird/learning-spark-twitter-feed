package com.azurebird.spark

import org.apache.spark.SparkConf
import org.apache.spark.streaming.dstream.ReceiverInputDStream
import org.apache.spark.streaming.twitter.TwitterUtils
import org.apache.spark.streaming.{Seconds, StreamingContext}
import twitter4j.Status

/**
  * This class its in charge of build an retrieve a valid twitter stream
  */
object SparkTwitterBase {

  var twitterStream: StreamingContext = _

  def instanceTwitterStream(): StreamingContext = {
    if (twitterStream != null) return twitterStream

    setupTwitter()
    val conf = new SparkConf()
    conf.setAppName("TwitterStreaming")
    twitterStream = new StreamingContext(conf, Seconds(1))
    twitterStream
  }

  // TODO This should change in the future by using system properties during app launch
  private def setupTwitter(): Unit = {
    import scala.io.Source

    val file = Source.fromInputStream(getClass.getResourceAsStream("/twitter.txt"))

    try {
      file.getLines()
        .map(_.split(" "))
        .filter(_.length == 2)
        .foreach(property => System.setProperty(s"twitter4j.oauth.${property(0)}", property(1)))
    } finally {
      file.close()
    }
  }
}
