package advent2018

import advent2018.Day13_MineCartMadness.{Cart, Grid}
import org.scalatest.{FunSuite, Matchers}
import utils.UnitTest

import scala.io.Source

class Day13Test extends UnitTest {

  import Day13_MineCartMadness._

  test("task 1") {
    val (grid, carts) = readFrom("day13.txt")

    runUntilCrash(grid, carts) shouldBe Coord(117,62)
  }

  test("task 2") {

    val (grid, carts) = readFrom("day13.txt")

    runUntilOneRemain(grid, carts) shouldBe Coord(69, 67)
  }

  test("task 1 (example)") {
    val gridStr = """/->-\
                    ||   |  /----\
                    || /-+--+-\  |
                    || | |  | v  |
                    |\-+-/  \-+--/
                    |  \------/""".stripMargin

    val(grid, carts) = parseString(gridStr)

    runUntilCrash(grid, carts) shouldBe Coord(7,3)
  }

  test("moveCarts") {
    val gridStr = """/->-\
                    ||   |  /----\
                    || /-+--+-\  |
                    || | |  | v  |
                    |\-+-/  \-+--/
                    |  \------/""".stripMargin

    val(grid, carts) = parseString(gridStr)

    val (carts2, _) = moveCarts(grid, carts)

    draw(grid, carts2) shouldBe """/-->\
                                  ||   |  /----\
                                  || /-+--+-\  |
                                  || | |  | |  |
                                  |\-+-/  \->--/
                                  |  \------/""".stripMargin
  }

  test("parse") {
    val gridStr = """/->-\
                    ||   |  /----\
                    || /-+--+-\  |
                    || | |  | v  |
                    |\-+-/  \-+--/
                    |  \------/""".stripMargin

    val(grid, carts) = parseString(gridStr)

    draw(grid, carts) shouldBe gridStr
  }

  def draw(grid: Grid, carts: Seq[Cart]): String = {
    val chars = grid.map(_.toCharArray).toArray
    carts.foreach(c => chars(c.coord.y)(c.coord.x) = c.moveDirection.char)
    draw(chars.map(_.mkString).toVector)
  }

  def draw(grid: Grid): String = grid.map(_.replaceAll("([ ]+$)", "")).mkString(lineSeparator)

  def parseString(s: String): (Grid, Seq[Cart]) = (align andThen parse)(s.split(System.getProperty("line.separator")))

  val align: IndexedSeq[String] => IndexedSeq[String] = xs => {
    val width = xs.map(_.length).max
    xs.map(_.padTo(width, ' '))
  }

  private def readFrom(name: String): (Grid, Seq[Cart]) =
    (align andThen parse)(
      Source.fromResource(s"advent2018/$name").getLines().toVector
    )
}
