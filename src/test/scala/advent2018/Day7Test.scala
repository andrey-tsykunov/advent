package advent2018

import org.scalatest.{FunSuite, Matchers}

import scala.io.Source

class Day7Test extends FunSuite with Matchers {

  import Day7._

  test("task 1") {
    val data = readFrom("day7.txt")

    sort(data).mkString shouldBe "CFMNLOAHRKPTWBJSYZVGUQXIDE"
  }

  test("task 1 (example 1)") {
    val data = readFrom("day7_1.txt")

    sort(data).mkString shouldBe "CABDFE"
  }

  test("task 1 (example 2)") {
    val data = readFrom("day7_2.txt")

    sort(data).mkString shouldBe "CAZBDE"
  }

  test("task 2") {
    val data = readFrom("day7.txt")

    runTasks(data, 5, 60) shouldBe 971
  }

  test("task 2 (example 1)") {
    val data = readFrom("day7_1.txt")
    runTasks(data, 2, 0) shouldBe 15
  }

  test("task 2 (example 2)") {
    runTasks(Seq('A' -> 'B'), 1, 0) shouldBe 3
  }

  val format = "Step ([A-Z]) must be finished before step ([A-Z]) can begin.".r

  private def readFrom(name: String): Seq[Edge] =
    Source.fromResource(s"advent2018/$name")
      .getLines()
      .map { case format(c1, c2) => (c1(0), c2(0)) }
      .toStream
}
