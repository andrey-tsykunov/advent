package advent2018

import org.scalatest.{FunSuite, Matchers}

import scala.io.Source

class Day11Test extends FunSuite with Matchers {

  import Day11._

  test("task 1") {
    findMaxSquare(1133, 3) shouldBe (235,14,3)
  }

  test("task 2") {
    findMaxSquare(1133) shouldBe (237,227,14)
  }

  test("cell power") {

    cellPower(3, 5, 8) shouldBe 4
    cellPower(122, 79, 57) shouldBe -5
    cellPower(217, 196, 39) shouldBe 0
    cellPower(101, 153, 71) shouldBe 4
  }

  test("findMaxSquare 3x3") {

    findMaxSquare(18, 3) shouldBe (33, 45, 3)
    findMaxSquare(42, 3) shouldBe (21, 61, 3)
  }

  test("findMaxSquare any size") {

    findMaxSquare(18) shouldBe (90,269,16)
    findMaxSquare(42) shouldBe (232,251,12)
  }
}
