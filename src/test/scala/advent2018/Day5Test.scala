package advent2018

import java.time.LocalDateTime

import org.scalatest.{FunSuite, Matchers}

import scala.io.Source

class Day5Test extends FunSuite with Matchers {

  import Day5._

  test("task 1") {
    val data = readFrom("day5.txt")

    react(data).length shouldBe 9900
  }

  test("task 1 (example)") {
    val data = "dabAcCaCBAcCcaDA"

    react(data) shouldBe "dabCBAcaDA"
  }

  test("task 2") {
    val data = readFrom("day5.txt")

    excludeAndReact(data).length shouldBe 4992
  }

  test("task 2 (example)") {
    val data = "dabAcCaCBAcCcaDA"

    excludeAndReact(data) shouldBe "daDA"
  }

  private def readFrom(name: String): String =
    Source.fromResource(s"advent2018/$name").mkString
}
