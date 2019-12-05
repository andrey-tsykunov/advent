package advent2018

import java.time.{LocalDateTime, ZoneOffset}

import org.scalatest.{FunSuite, Matchers}
import utils.UnitTest

import scala.io.Source

class Day4Test extends UnitTest {

  import Day4._

  test("task 1") {
    val data = readFrom("day4.txt")

    strategy1(data) shouldBe (1097, 21)
  }

  test("task 2") {
    val data = readFrom("day4.txt")

    strategy2(data) shouldBe (727, 49)
  }

  val begin = "\\[(\\d+)-(\\d+)-(\\d+) (\\d+):(\\d+)\\] Guard #(\\d+) begins shift".r
  val falls = "\\[(\\d+)-(\\d+)-(\\d+) (\\d+):(\\d+)\\] falls asleep".r
  val wakesup = "\\[(\\d+)-(\\d+)-(\\d+) (\\d+):(\\d+)\\] wakes up".r

  private def readFrom(name: String): Stream[Log] =
    Source.fromResource(s"advent2018/$name")
      .getLines()
      .map(parse)
      .toStream

  def parse(s: String): Log = s match {
    case begin(year, month, day, hour, min, id) => ShiftStart(id.toInt, toDateTime(year, month, day, hour, min))
    case falls(year, month, day, hour, min) => FallsAsleep(toDateTime(year, month, day, hour, min))
    case wakesup(year, month, day, hour, min) => WakesUp(toDateTime(year, month, day, hour, min))
  }

  private def toDateTime(year: String, month: String, day: String, hour: String, min: String) =
    LocalDateTime.of(year.toInt, month.toInt, day.toInt, hour.toInt, min.toInt)
}
