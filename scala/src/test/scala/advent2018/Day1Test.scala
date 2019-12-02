package advent2018.y2018

import advent2018.UnitTest
import org.scalatest.{FunSuite, Matchers}

import scala.io.Source

class Day1Test extends UnitTest {

  import advent2018.Day1_ChronalCalibration._

  test("result freq") {

    val r = resultFrequency(readFrom("day1.txt"))

    r shouldBe 599
  }

  test("first dup") {

    val r = firstDuplicate(readFrom("day1.txt"))

    r shouldBe 81204
  }

  test("first dup 2") {

    val r = firstDuplicate_zip(readFrom("day1.txt"))

    r shouldBe 81204
  }

  test("first dup 3") {

    val r = firstDuplicate_mutable_1(readFrom("day1.txt"))

    r shouldBe 81204
  }

  private def readFrom(name: String): Stream[Int] =
    Source.fromResource(s"advent2018/$name").getLines().map(_.toInt).toStream
}
