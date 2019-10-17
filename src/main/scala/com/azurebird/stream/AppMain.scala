package com.azurebird.stream

import com.azurebird.TwitterConsumer

object AppMain {

  def main(args: Array[String]): Unit = {
    TwitterConsumer.startConsuming()
  }
}
