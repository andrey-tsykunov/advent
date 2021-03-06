package advent2018

import org.scalatest.{FunSuite, Matchers}
import utils.UnitTest

import scala.io.Source

class Day2Test extends UnitTest {

  import advent2018.Day2_InventoryManagementSystem._

  test("task 1") {
    val data = readFrom("day2.txt")

    count(data) shouldBe 5904
  }

  test("task 2") {

    val data = readFrom("day2.txt")

    common(data) shouldBe "jiwamotgsfrudclzbyzkhlrvp"
  }

  test("task 2, sort") {

    val data = readFrom("day2.txt")

    common2(data) shouldBe "jiwamotgsfrudclzbyzkhlrvp"
  }

  private def readFrom(name: String): Stream[String] =
    Source.fromResource(s"advent2018/$name").getLines().toStream
}
