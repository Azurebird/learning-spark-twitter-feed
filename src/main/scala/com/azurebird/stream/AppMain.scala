package com.azurebird.stream

import com.azurebird.TwitterConsumer

object AppMain {

  /** Makes sure only ERROR messages get logged to avoid log spam. */
  private def setupLogging(): Unit = {
    import org.apache.log4j.{Level, Logger}
    Logger.getLogger("org").setLevel(Level.ERROR)
  }

  def main(args: Array[String]): Unit = {
    setupLogging()
    TwitterConsumer.startConsuming()
  }
}
