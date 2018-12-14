package advent2018

import org.scalatest.{FunSuite, Matchers}

class Day12Test extends UnitTest {

  import Day12._

  test("task 1") {

    val masks = loadMasks(notes).toSet

    val (last, lastPos) = simulate(initial, 20, masks)

    plantCount(last, lastPos) shouldBe 3241
  }

  test("task 1 (example)") {
    val initial = "#..#.#..##......###...###"

    val notes = """...## => #
                  |..#.. => #
                  |.#... => #
                  |.#.#. => #
                  |.#.## => #
                  |.##.. => #
                  |.#### => #
                  |#.#.# => #
                  |#.### => #
                  |##.#. => #
                  |##.## => #
                  |###.. => #
                  |###.# => #
                  |####. => #""".stripMargin

    val masks = loadMasks(notes).toSet

    val (last, lastPos) = simulate(initial, 1000, masks)

    last shouldBe "#....##....#####...#######....#.#..##"
    plantCount(last, lastPos) shouldBe 325
  }

  test("next") {
    val masks = loadMasks(notes).toSet

    next("#..#.#..##......###...###", 0, masks) shouldBe ("#...#....#.....#..#..#..#", 0)
    next("##..#..#.#....#....#..#.#...#...#", -1, masks) shouldBe ("#.#..#...#.#...##...#...#.#..##..##", -2)
  }

  test("task 2") {
    val masks = loadMasks(notes).toSet

    val (last, lastPos) = simulate(initial, 50000000000L, masks)

    plantCount(last, lastPos) shouldBe 2749999999911L
  }

  private def loadMasks(masks: String): Seq[String] = masks.split(System.getProperty("line.separator"))
    .collect { case s if s.endsWith("#") => s.split(" => ")(0) }

  val initial = "##.#....#..#......#..######..#.####.....#......##.##.##...#..#....#.#.##..##.##.#.#..#.#....#.#..#.#"

  val notes = """#.#.. => .
                |..##. => .
                |...#. => .
                |..#.. => .
                |##### => #
                |.#.#. => .
                |####. => .
                |###.. => .
                |.#..# => #
                |#..#. => #
                |#.#.# => .
                |#...# => #
                |..### => .
                |...## => #
                |##..# => #
                |#.... => .
                |.#.## => #
                |#.### => #
                |.##.# => #
                |#..## => .
                |.#... => #
                |.###. => .
                |##... => #
                |##.## => #
                |##.#. => #
                |#.##. => #
                |.##.. => .
                |..#.# => .
                |....# => .
                |###.# => .
                |..... => .
                |.#### => .""".stripMargin
}
