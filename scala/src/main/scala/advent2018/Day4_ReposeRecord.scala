package advent2018

import java.time.{Duration, LocalDateTime, ZoneOffset}

trait Log {
  def time: LocalDateTime
}

case class ShiftStart(id: Int, time: LocalDateTime) extends Log
case class FallsAsleep(time: LocalDateTime) extends Log
case class WakesUp(time: LocalDateTime) extends Log

case class Shift(id: Int, logs: Seq[Log]) {

  def sleepIntervals: Seq[Range] = {

    val (_, intervals) = logs.foldRight((Option.empty[LocalDateTime], List.empty[Range])) {
      case (WakesUp(time), (_, acc)) => (Some(time), acc)
      case (FallsAsleep(t0), (Some(t1), acc)) => (None, (t0.getMinute until t1.getMinute) :: acc)
      case (_, (pending, acc)) => (pending, acc)
    }

    intervals
  }
}

object Day4 {

  /*
    Strategy 1: Find the guard that has the most minutes asleep. What minute does that guard spend asleep the most?
   */
  def strategy1(logs: Seq[Log]): (Int, Int) = {

    val (id, (_, index)) = buildStats(logs)
      .mapValues { stats =>
        stats.zipWithIndex.maxBy { case (count, i) => count}
      }
      .toSeq
      .maxBy { case (_, (count, i)) => count }

    (id, index)
  }

  /*
    Strategy 2: Of all guards, which guard is most frequently asleep on the same minute?
   */
  def strategy2(logs: Seq[Log]): (Int, Int) = {

    val idToStats = buildStats(logs)

    val (id, _) = idToStats
      .mapValues(_.sum)
      .toSeq
      .maxBy { case (_, totalMinAsleep) => totalMinAsleep }

    val (_, index) = idToStats(id)
      .zipWithIndex
      .maxBy { case (count, _) => count}

    (id, index)
  }

  private def buildStats(logs: Seq[Log]) = {

    val sorted = logs.sortBy(_.time.toEpochSecond(ZoneOffset.UTC))

    val (_, shifts) = sorted.foldRight((List.empty[Log], List.empty[Shift])) {
      case (log: ShiftStart, (pending, acc)) => (Nil, Shift(log.id, log :: pending) :: acc)
      case (log, (pending, acc)) => (log :: pending, acc)
    }

    shifts.groupBy(_.id).mapValues { guardShifts =>
        val stats = Array.ofDim[Int](60)
        guardShifts.flatMap(_.sleepIntervals).flatten.foreach { i => stats(i) += 1 }
        stats
      }.view
  }
}
