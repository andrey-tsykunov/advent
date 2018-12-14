package advent2018

import org.scalatest.{FunSuite, Matchers}

import scala.io.Source

class Day10Test extends UnitTest {

  import Day10._

  test("task 1") {
    val data = readFrom("day10.txt")

    val (r, sec) = printMinArea(data)

    sec shouldBe 10418
    println
    r.foreach(println)
  }

  test("task 1 (example)") {
    val data = readFrom("day10_1.txt")

    val (r, sec) = printMinArea(data)

    sec shouldBe 3
    println
    r.foreach(println)
  }

  private def readFrom(name: String): Seq[Point] =
    Source.fromResource(s"advent2018/$name")
      .getLines()
      .map("""[-\d]+""".r.findAllIn(_).map(_.toInt).toList)
      .map { case l :: t :: vl :: vt :: Nil => Point(l, t, vl, vt) }
      .toStream
}
