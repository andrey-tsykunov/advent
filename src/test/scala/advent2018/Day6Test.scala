package advent2018

import org.scalatest.{FunSuite, Matchers}

import scala.io.Source

class Day6Test extends UnitTest {

  import Day6._

  test("task 1") {
    val data = readFrom("day6.txt")

    task1(data) shouldBe 3358
  }

  test("task 1 (example)") {
    val data = parse(example)

    task1(data) shouldBe 17
  }

  test("task 2") {
    val data = readFrom("day6.txt")

    task2(data, 10000) shouldBe 45909
  }

  test("task 2 (example)") {
    val data = parse(example)

    task2(data, 32) shouldBe 16
  }

  def parse(s: String): Seq[Coord] = {
    s.split("\\r?\n").map { x =>
      val parts = x.split(", ")

      Coord(parts(0).toInt, parts(1).toInt)
    }
  }

  val example = """1, 1
                  |1, 6
                  |8, 3
                  |3, 4
                  |5, 5
                  |8, 9""".stripMargin

  private def readFrom(name: String): Seq[Coord] =
    parse(Source.fromResource(s"advent2018/$name").mkString)
}
