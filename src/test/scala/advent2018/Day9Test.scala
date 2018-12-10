package advent2018

import org.scalatest.{FunSuite, Matchers}

import scala.io.Source

class Day9Test extends FunSuite with Matchers {

  import Day9._

  test("task 1") {
    runGame(419, 72164) shouldBe 423717
  }

  test("task 1 (example 1)") {

    runGame(9, 25) shouldBe 32
  }

  test("task 1 (example 2)") {

    runGame(10, 1618) shouldBe 8317
  }

  test("task 1 (example 3)") {

    runGame(13, 7999) shouldBe 146373
  }

  test("task 1 (example 4)") {

    runGame(17, 1104) shouldBe 2764
  }

  test("task 2") {
    runGame(419, 72164 * 100) shouldBe 3553108197L
  }

  private def readFrom(name: String): Seq[String] =
    Source.fromResource(s"advent2018/$name")
      .getLines()
      .toStream
}
