package advent2018

import scala.collection.breakOut

object Day2 {
  def count(items: Stream[String]): Int = {

    var d2 = 0
    var d3 = 0
    items.foreach { s =>
      val dups = s.groupBy(x => x).mapValues(_.length).values.toSet

      if(dups.contains(2)) d2 += 1
      if(dups.contains(3)) d3 += 1
    }

    d2 * d3
  }

  def common(items: Seq[String]): String = {

    def replace(s: String)(i: Int) = s.indices.map(j => if(i == j) '*' else s(j))

    items.flatMap(s => s.indices.map(replace(s)))
      .groupBy(identity)
      .collect { case (s, seq) if seq.size == 2 => s }
      .head.filterNot(_ == '*').mkString
  }

  def common2(items: Stream[String]): String = {
    val r = for {
      x <- items
      y <- items
      xy = x.zip(y).collect { case (cx, cy) if cx == cy => cx }
      if xy.length == x.length - 1
    }
      yield xy.mkString

    r.head
  }
}
