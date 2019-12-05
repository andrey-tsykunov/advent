package advent2019

import utils.UnitTest

import scala.io.Source

class Day3Test extends UnitTest {

  import Day3_CrossedWires._

  test("task 1 test 1") {
    closestIntersectionDistance(readFrom("day3_1.txt")) shouldBe 159
    closestIntersectionDistance(readFrom("day3_2.txt")) shouldBe 135
  }

  test("task 1") {
    closestIntersectionDistance(readFrom("day3.txt")) shouldBe 375
  }

  test("task 2 tests") {
    fewestStepsToIntersection( readFrom("day3_1.txt")) shouldBe 610
    fewestStepsToIntersection( readFrom("day3_2.txt")) shouldBe 410
  }

  test("task 2") {
    fewestStepsToIntersection(readFrom("day3.txt")) shouldBe 14746
  }

  private def readFrom(name: String): Seq[Wire] =
    Source.fromResource(s"advent2019/$name")
      .getLines()
      .map { x => Wire(x.split(",").map(parse)) }
      .toSeq
}
