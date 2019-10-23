package com.azurebird

import com.azurebird.spark.SparkTwitterBase
import com.azurebird.stream.TwitterProducer
import com.azurebird.twitter.HashTag
import org.apache.spark.streaming.Seconds
import org.apache.spark.streaming.twitter.TwitterUtils

object TwitterConsumer extends Consumer {

  private val sparkTwitter = SparkTwitterBase.instanceTwitterStream()

  private val producer: TwitterProducer = new TwitterProducer()

  override def startConsuming(): Unit = {

    val sortedTopHashtags = TwitterUtils.createStream(sparkTwitter, None)
      .map(_.getText)
      .flatMap(_.split(" "))
      .filter(_.startsWith("#"))
      .map((_, 1))
      .reduceByKeyAndWindow(_+_, _-_, Seconds(300), Seconds(1))
      .transform(_.sortBy(_._2, ascending = false))

    sortedTopHashtags.foreachRDD { rdd =>
      val tenFirsts = rdd.take(10).map(record => new HashTag(record._1, record._2))
      producer.initProducer()
      producer.publish(tenFirsts)
      producer.closeProducer()
    }

    sparkTwitter.checkpoint("tweets")
    sparkTwitter.start()
    sparkTwitter.awaitTermination()
  }
}
