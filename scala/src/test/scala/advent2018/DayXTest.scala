package advent2018

import org.scalatest.{FunSuite, Matchers}

import scala.io.Source

class DayXTest extends UnitTest {

  test("task 1") {
    val data = readFrom("day_.txt")

  }

  test("task 1 (example)") {
    val data = "abc"

  }

  test("task 2") {
    val data = readFrom("day_.txt")

  }

  private def readFrom(name: String): Seq[String] =
    Source.fromResource(s"advent2018/$name")
      .getLines()
      .toStream
}
