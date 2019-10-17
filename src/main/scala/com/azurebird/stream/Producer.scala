package com.azurebird.stream

trait Producer {

  def produce(item: Producible)
}
