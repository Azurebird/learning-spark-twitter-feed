package com.azurebird.stream

import java.util.Properties

import com.azurebird.twitter.HashTag
import com.google.gson.Gson
import org.apache.kafka.clients.producer.{KafkaProducer, ProducerRecord}

class TwitterProducer extends Producer {

  val kafkaProps: Properties = setupKafkaConf()
  val gson: Gson = new Gson()

  private var producer: KafkaProducer[String, String] = _

  private def setupKafkaConf(): Properties = {
    val props = new Properties()
    props.put("bootstrap.servers", "localhost:9092")
    props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer")
    props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer")
    props
  }

  def initProducer(): Unit = {
    producer = new KafkaProducer[String, String](kafkaProps)
  }

  def publish(hashTag: Array[HashTag]): Unit = {
    val record = new ProducerRecord[String, String]("my-replicated-topic", "hashTags" , gson.toJson(hashTag))
    producer.send(record)
  }

  def closeProducer(): Unit = {
    producer.close()
  }

  override def produce(item: Producible): Unit = ???
}
