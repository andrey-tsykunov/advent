package advent2018

import org.scalatest.{FunSuite, Matchers}

import scala.io.Source

class Day11Test extends UnitTest {

  import Day11_ChronalCharge._

  test("task 1") {
    findMaxSquare(1133, maxSize = 3) shouldBe (235,14,3)
  }

  test("task 2") {
    findMaxSquare(1133) shouldBe (237,227,14)
  }

  test("task 3") {
    findMaxSquare(1133, size = 1000, maxSize = 1000) shouldBe (477,295,19)
  }

  test("cell power") {

    cellPower(3, 5, 8) shouldBe 4
    cellPower(122, 79, 57) shouldBe -5
    cellPower(217, 196, 39) shouldBe 0
    cellPower(101, 153, 71) shouldBe 4
  }

  test("findMaxSquare 3x3") {

    findMaxSquare(18, minSize = 3, maxSize = 3) shouldBe (33, 45, 3)
    findMaxSquare(42, minSize = 3, maxSize = 3) shouldBe (21, 61, 3)
  }

  test("findMaxSquare any size") {

    findMaxSquare(18) shouldBe (90,269,16)
    findMaxSquare(42) shouldBe (232,251,12)
  }
}
