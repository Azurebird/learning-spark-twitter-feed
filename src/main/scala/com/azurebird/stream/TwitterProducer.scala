package com.azurebird.stream

import java.util.Properties

import com.azurebird.twitter.Tweet
import org.apache.kafka.clients.producer.{KafkaProducer, ProducerRecord}

class TwitterProducer {

  /** Makes sure only ERROR messages get logged to avoid log spam. */
  def setupLogging(): Unit = {
    import org.apache.log4j.{Level, Logger}
    Logger.getLogger("org").setLevel(Level.INFO)
  }

  def main(args: Array[String]): Unit = {
    val twitterProducer = new TwitterProducer()
    setupLogging()
    publish("This is a message from scala")
  }

  def publish(string: String): Unit = {
    val props = new Properties()
    props.put("bootstrap.servers", "localhost:9092")
    props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer")
    props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer")
    val producer = new KafkaProducer[String, String](props)
    val record = new ProducerRecord[String, String]("my-replicated-topic", "key", string)
    producer.send(record)
    producer.close()
  }
}
