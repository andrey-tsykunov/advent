package advent2018

import utils.UnitTest

import scala.io.Source

class Day17Test extends UnitTest {

  import Day17_ReservoirResearch._

  test("task 1 and 2") {
    val data = readFrom("day17.txt")

    countReachableTiles(data) shouldBe (42389, 34497)
  }

  test("task 1 (example )") {
    val data = Seq(
      "x=495, y=2..7",
      "y=7, x=495..501",
      "x=501, y=3..7",
      "x=498, y=2..4",
      "x=506, y=1..2",
      "x=498, y=10..13",
      "x=504, y=10..13",
      "y=13, x=498..504").map(parse)

    countReachableTiles(data) shouldBe (57,29)
  }

  test("task 1, consider min Y") {
    val data = Seq(
      "y=3, x=499..501",
      "x=510, y=2..3").map(parse)

    countReachableTiles(data) shouldBe (7, 0)
  }

  test("task 1, flow from two sides") {
    val data = Seq(
      "y=2, x=499..501",
      "y=5, x=498..502").map(parse)

    countReachableTiles(data) shouldBe (13, 0)
  }

  val formatX = "x=(\\d+), y=(\\d+)..(\\d+)".r
  val formatY = "y=(\\d+), x=(\\d+)..(\\d+)".r

  private def parse(s: String): ClayLine = s match {
    case formatX(x, y1, y2) => ClayLine(List(x.toInt), (y1.toInt to y2.toInt).toList)
    case formatY(y, x1, x2) => ClayLine((x1.toInt to x2.toInt).toList, List(y.toInt))
  }

  private def readFrom(name: String): Seq[ClayLine] =
    Source.fromResource(s"advent2018/$name")
      .getLines()
      .map(parse)
      .toStream
}
