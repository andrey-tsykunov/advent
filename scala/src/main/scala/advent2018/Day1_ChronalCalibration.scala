package advent2018

import scala.collection.mutable

object Day1_ChronalCalibration {
  def resultFrequency(changes: Stream[Int]): Int = changes.sum

  def firstDuplicate(changes: Stream[Int]): Int = {

    val freqs = Stream.continually(changes).flatten.scanLeft(0)(_ + _)
    val seed = (Option.empty[Int], Set.empty[Int])

    freqs
      .scanLeft(seed) { case ((_, seen), x) => (Some(x).filter(seen.contains), seen + x) }
      .collectFirst { case (Some(dup), _) => dup }
      .get
  }

  def firstDuplicate_zip(changes: Stream[Int]): Int = {

    val freqs = Stream.continually(changes).flatten.scanLeft(0)(_ + _)
    val seenFreqs = freqs.scanLeft(Set.empty[Int])(_ + _)

    freqs.zip(seenFreqs)
      .collect { case (f, seen) if seen.contains(f) => f }
      .head
  }

  def firstDuplicate_mutable_1(changes: Stream[Int]): Int = {
    val freqs = Stream.continually(changes).flatten.scanLeft(0)(_ + _)
    val seen = mutable.Set.empty[Int]

    freqs.find { f =>
        val r = seen.contains(f)
        seen += f
        r
    }.get
  }
}
