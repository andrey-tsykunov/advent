package advent2018

import org.scalatest.{FunSuite, Matchers}
import utils.UnitTest

import scala.io.Source

class Day8Test extends UnitTest {

  import Day8_MemoryManeuver._

  test("task 1") {
    val data = readFrom("day8.txt")

    parse(data).sum shouldBe 47464
  }

  test("task 1 (example)") {
    val data = "2 3 0 3 10 11 12 1 1 0 1 99 2 1 1 2"

    parse(data).sum shouldBe 138
  }

  test("task 2") {
    val data = readFrom("day8.txt")

    parse(data).value shouldBe 23054
  }

  test("task 2 (example)") {
    val data = "2 3 0 3 10 11 12 1 1 0 1 99 2 1 1 2"

    parse(data).value shouldBe 66
  }

  private def readFrom(name: String): String =
    Source.fromResource(s"advent2018/$name").mkString
}
