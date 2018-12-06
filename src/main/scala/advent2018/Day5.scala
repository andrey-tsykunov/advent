package advent2018

import scala.annotation.tailrec

object Day5 {

  def excludeAndReact(s: String): String = {
    ('a' to 'z')
      .toParArray
      .map { c => react(s.filterNot(_.toLower == c)) }
      .minBy(_.length)
  }

  def react(s: String, num: Int = 0): String = {

    @tailrec
    def go(a: Array[Char]): Array[Char] = {
      var i = 0
      val acc = Vector.newBuilder[Char]
      while(i < a.length) {
        val c1 = a(i)
        val c2 = if(i == a.length - 1) '_' else a(i + 1)

        if(c1.toUpper == c2.toUpper && c1 != c2)
          i += 2
        else {
          acc += c1
          i += 1
        }
      }

      val r = acc.result().toArray

      if(r.length == a.length) r
      else go(r)
    }

    go(s.toCharArray).mkString
  }

  def sim___(s: String): String = {

    println(s)

    val r = s.toStream
      .zipAll(s.toStream.drop(1), '_', '_')
      .zip('_' #:: s.toStream)
      .collect { case ((c, c1), c2) if (c.toUpper != c1.toUpper && c != c1) && (c.toUpper != c2.toUpper && c != c2) =>
        println(s"$c, $c1, $c2")
        //val r = (c.toUpper == c1.toUpper && c != c1) || (c.toUpper == c2.toUpper && c != c2)
        //println(r)
        c
      }
      .mkString

    println(">> "  + r)

    if(r.length == s.length) r
    else react(r)
  }
}
