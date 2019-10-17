package com.azurebird

import com.azurebird.spark.SparkTwitterBase
import com.azurebird.stream.{Producer, TwitterProducer}
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
      rdd.foreachPartition { partitionOfRecords =>
        producer.initProducer()
        partitionOfRecords.foreach(record => producer.publish(record))
        producer.closeProducer()
      }
    }

    sparkTwitter.checkpoint("/Users/azurebird/git/spark-scala/twitter/")
    sparkTwitter.start()
    sparkTwitter.awaitTermination()
  }
}
