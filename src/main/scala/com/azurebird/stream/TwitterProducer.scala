package com.azurebird.stream

import java.util.Properties

import org.apache.kafka.clients.producer.{KafkaProducer, ProducerRecord}

class TwitterProducer extends Producer {

  val kafkaProps: Properties = setupKafkaConf()

  private var producer: KafkaProducer[String, String] = _

  /** Makes sure only ERROR messages get logged to avoid log spam. */
  private def setupLogging(): Unit = {
    import org.apache.log4j.{Level, Logger}
    Logger.getLogger("org").setLevel(Level.INFO)
  }

  private def setupKafkaConf(): Properties = {
    setupLogging()
    val props = new Properties()
    props.put("bootstrap.servers", "localhost:9092")
    props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer")
    props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer")
    props
  }

  def initProducer(): Unit = {
    producer = new KafkaProducer[String, String](kafkaProps)
  }

  def publish(tuple: (String, Int)): Unit = {
    val record = new ProducerRecord[String, String]("my-replicated-topic", tuple._1 , tuple._1)
    producer.send(record)
  }

  def closeProducer(): Unit = {
    producer.close()
  }

  override def produce(item: Producible): Unit = ???
}
