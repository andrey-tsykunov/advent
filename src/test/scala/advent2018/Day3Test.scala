package advent2018

import com.typesafe.scalalogging.StrictLogging
import org.scalatest.{FunSuite, Matchers}

import scala.io.Source

class Day3Test extends FunSuite with Matchers with StrictLogging {

  import Day3._

  test("task 1") {

    val data = readFrom("day3.txt")

    task1(data) shouldBe 107663
  }

  test("overlap 1") {

    val c1 = Claim(1, 0, 0, 2, 2)
    val c2 = Claim(2, 1, 1, 2, 2)

    overlap(c1, c2) shouldBe Set((1, 1))
  }

  test("task 1 (overlap)") {

    val data = readFrom("day3.txt")

    task1_overlap(data) shouldBe 107663
  }

  test("task 1 (matrix)") {

    val data = readFrom("day3.txt")

    task1_matrix(data) shouldBe 107663
  }

  test("task 1, test") {
    val data = readFrom("day3_.txt")

    task1(data) shouldBe 4
  }

  test("task 2") {

    val data = readFrom("day3.txt")

    task2(data) shouldBe 1166
  }

  test("task 2, test") {

    val data = readFrom("day3_.txt")

    task2(data) shouldBe 3
  }

  val format = "#(\\d+) @ (\\d+),(\\d+): (\\d+)x(\\d+)".r

  private def readFrom(name: String): Stream[Claim] =
    Source.fromResource(s"advent2018/$name")
      .getLines()
      .map { case format(id, left, top, width, height) => Claim(id.toInt, left.toInt, top.toInt, width.toInt, height.toInt) }
      .toStream
}
