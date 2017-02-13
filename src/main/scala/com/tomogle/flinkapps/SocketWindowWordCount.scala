package com.tomogle.flinkapps

import org.apache.flink.streaming.api.scala._
import org.apache.flink.streaming.api.windowing.time.Time
import org.apache.flink.streaming.api.windowing.windows.TimeWindow

object SocketWindowWordCount {

  /**
    * To run, first run nc -l 35555
    */
  def main(args: Array[String]): Unit = {

    val port = 35555
    val env = StreamExecutionEnvironment.getExecutionEnvironment
    val text = env.socketTextStream("localhost", port, '\n')

    val words = text.flatMap(_.split("\\s"))
    val counts = words.map(w => WordCount(w, 1))
    val by: KeyedStream[WordCount, String] = counts.keyBy(_.word)
    val window: WindowedStream[WordCount, String, TimeWindow] = by.timeWindow(Time.seconds(5), Time.seconds(1))
    val windowedCounts: DataStream[WordCount] = window.sum("count")
    windowedCounts.print().setParallelism(1)

    env.execute()
  }

  case class WordCount(word: String, count: Long)
}
