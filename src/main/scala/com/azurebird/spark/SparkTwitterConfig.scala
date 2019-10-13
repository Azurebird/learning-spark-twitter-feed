package com.azurebird.spark

import org.apache.spark.streaming.dstream.ReceiverInputDStream
import org.apache.spark.streaming.twitter.TwitterUtils
import org.apache.spark.streaming.{Seconds, StreamingContext}
import twitter4j.Status

object SparkTwitterConfig {

  var twitterStream: ReceiverInputDStream[Status] = _

  // TODO This MUST change, to a property based file
  /** Configures Twitter service credentials using twiter.txt in the main workspace directory */
  def setupTwitter(): Unit = {
    import scala.io.Source

    val file = Source.fromFile(getClass.getResource("/twitter.txt").getPath)
    try {
      for (line <- file.getLines) {
        val fields = line.split(" ")
        if (fields.length == 2) {
          System.setProperty("twitter4j.oauth." + fields(0), fields(1))
        }
      }
    } finally {
      file.close()
    }
  }

  def instanceTwitterStream(): ReceiverInputDStream[Status] = {
    if (twitterStream != null) return twitterStream

    val ssc = new StreamingContext("local[*]", "TwitterStreaming", Seconds(1))
    twitterStream = TwitterUtils.createStream(ssc, None)
    twitterStream
  }
}
