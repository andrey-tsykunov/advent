package advent2018

import scala.collection.mutable

object Day1 {
  def resultFrequency(changes: Stream[Int]): Int = changes.sum

  def firstDuplicate(changes: Stream[Int]): Int = {
    val seed = (Option.empty[Int], Set.empty[Int])

    Stream.continually(changes).flatten
      .scanLeft(0)(_ + _)
      .scanLeft(seed) { case ((_, seen), x) => (Some(x).filter(seen.contains), seen + x) }
      .collect { case (Some(dup), _) => dup }
      .head
  }

  def firstDuplicate2(changes: Stream[Int]): Int = {

    val freqs = Stream.continually(changes).flatten.scanLeft(0)(_ + _)
    val seenFreqs = freqs.scanLeft(Set.empty[Int])(_ + _)

    freqs.zip(seenFreqs)
      .collect { case (f, seen) if seen.contains(f) => f }
      .head
  }

  def firstDuplicate3(changes: Stream[Int]): Int = {
    val seen = mutable.Set.empty[Int]

    Stream.continually(changes).flatten
      .scanLeft(0)(_ + _)
      .filter { f =>
        val r = seen.contains(f)
        seen += f
        r
      }
      .head
  }
}
