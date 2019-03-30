package advent2018

import scala.io.Source

class Day18Test extends UnitTest {

  import Day18_SettlersofTheNorthPole._

  test("task 1") {
    val data = readFrom("day18.txt")

    resourceValueAfterNMinutes(data, 10) shouldBe 745008
  }

  test("task 1 (example)") {
    val data = Seq(".#.#...|#.",
      ".....#|##|",
      ".|..|...#.",
      "..|#.....#",
      "#.#|||#|#|",
      "...#.||...",
      ".|....|...",
      "||...#|.#|",
      "|.||||..|.",
      "...#.|..|.")

    resourceValueAfterNMinutes(data, 10) shouldBe 1147
//    Seq(
//      ".......##.",
//      "......|###",
//      ".|..|...#.",
//      "..|#||...#",
//      "..##||.|#|",
//      "...#||||..",
//      "||...|||..",
//      "|||||.||.|",
//      "||||||||||",
//      "....||..|."
//    )
  }

  test("task 2") {
    val data = readFrom("day18.txt")

    // 1000000000L
    resourceValueAfterNMinutes(data, 1000000000L) shouldBe 219425
  }

  private def readFrom(name: String): Seq[String] =
    Source.fromResource(s"advent2018/$name")
      .getLines()
      .toStream
}
