package com.azurebird

import com.azurebird.spark.SparkTwitterBase
import com.azurebird.stream.TwitterProducer
import org.apache.spark.streaming.Seconds
import org.apache.spark.streaming.twitter.TwitterUtils
import org.apache.log4j.{Level, Logger}

object TwitterConsumer extends Consumer {

  private val sparkTwitter = SparkTwitterBase.instanceTwitterStream()

  private val producer: TwitterProducer = new TwitterProducer()

  private val logger = Logger.getLogger(getClass)

  override def startConsuming(): Unit = {

    val sortedTopHashtags = TwitterUtils.createStream(sparkTwitter, None)
      .map(_.getText)
      .flatMap(_.split(" "))
      .filter(_.startsWith("#"))
      .map((_, 1))
      .reduceByKeyAndWindow(_+_, _-_, Seconds(300), Seconds(1))
      .transform(_.sortBy(_._2, ascending = false))

    sortedTopHashtags.foreachRDD { rdd =>
      val tenFirsts = rdd.take(10)
      logger.debug(tenFirsts)
    }

    sparkTwitter.checkpoint("tweets")
    sparkTwitter.start()
    sparkTwitter.awaitTermination()
  }
}
