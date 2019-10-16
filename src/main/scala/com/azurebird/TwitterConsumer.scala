package com.azurebird

import com.azurebird.spark.SparkTwitterBase
import org.apache.spark.streaming.Seconds

class TwitterConsumer extends Consumer {

  val sparkTwitter = SparkTwitterBase.instanceTwitterStream()

  override def consume(): Unit = {

    val colombianTweets = sparkTwitter.filter(tweet => tweet.getPlace.getCountry.equals("Colombia"))

    val statuses = sparkTwitter.map(status => status.getText())

    // Blow out each word into a new DStream
    val tweetwords = statuses.flatMap(tweetText => tweetText.split(" "))

    // Now eliminate anything that's not a hashtag
    val hashtags = tweetwords.filter(word => word.startsWith("#"))

    // Map each hashtag to a key/value pair of (hashtag, 1) so we can count them up by adding up the values
    val hashtagKeyValues = hashtags.map(hashtag => (hashtag, 1))

    val hashtagCounts = hashtagKeyValues.reduceByKeyAndWindow( (x,y) => x + y, (x,y) => x - y, Seconds(300), Seconds(1))
  }
}
