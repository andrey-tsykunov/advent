package advent2018

import scala.io.Source

class Day15Test extends UnitTest {

  import Day15_BeverageBandits._

  test("task 1") {
    val map = createGame(readFrom("day15.txt"))

    val r = runUntilWin(map)

    r.score shouldBe 207059
  }

  test("task 2") {
    val map = readFrom("day15.txt")

    val (_, game) = findMinElfPower(map)

    game.score shouldBe 49120
  }

  test("task 1 (example 1)") {
    val map = createGame(Vector(
      "#######",
      "#.G...#",
      "#...EG#",
      "#.#.#G#",
      "#..G#E#",
      "#.....#",
      "#######"))

    val r = runUntilWin(map)

    r.game.drawWithCreatures shouldBe Seq(
      "#######",
      "#G....#",
      "#.G...#",
      "#.#.#G#",
      "#...#.#",
      "#....G#",
      "#######"
    )

    r.turn shouldBe 47
    r.score shouldBe 27730
    r.game.formatCreatures shouldBe "G200, G131, G59, G200"
  }

  test("task 2 (example 1)") {
    val map = Vector(
      "#######",
      "#.G...#",
      "#...EG#",
      "#.#.#G#",
      "#..G#E#",
      "#.....#",
      "#######")

    val (power, game) = findMinElfPower(map)
    power shouldBe 15
    game.score shouldBe 4988
  }

  test("task 1 (example 1, run few times)") {
    val map = createGame(Vector(
      "#######",
      "#.G...#",
      "#...EG#",
      "#.#.#G#",
      "#..G#E#",
      "#.....#",
      "#######"))

    runNTimes(map, 2).drawWithCreatures shouldBe Seq(
      "#######",
      "#...G.#",
      "#..GEG#",
      "#.#.#G#",
      "#...#E#",
      "#.....#",
      "#######"
    )

    val game47 = runNTimes(map, 47)

    game47.drawWithCreatures shouldBe Seq(
      "#######",
      "#G....#",
      "#.G...#",
      "#.#.#G#",
      "#...#.#",
      "#....G#",
      "#######"
    )
  }

  test("task 1 (example 2)") {
    val map = createGame(Vector(
      "#######",
      "#G..#E#",
      "#E#E.E#",
      "#G.##.#",
      "#...#E#",
      "#...E.#",
      "#######"))

    val r = runUntilWin(map)

    r.game.drawWithCreatures shouldBe Seq(
      "#######",
      "#...#E#",
      "#E#...#",
      "#.E##.#",
      "#E..#E#",
      "#.....#",
      "#######"
    )

    r.turn shouldBe 37
    r.game.formatCreatures shouldBe "E200, E197, E185, E200, E200"
    r.score shouldBe 36334
  }

  test("task 1 (example 3)") {
    val map = createGame(Vector(
      "#######",
      "#E..EG#",
      "#.#G.E#",
      "#E.##E#",
      "#G..#.#",
      "#..E#.#",
      "#######"))

    val r = runUntilWin(map)

    r.game.drawWithCreatures shouldBe Seq(
      "#######",
      "#.E.E.#",
      "#.#E..#",
      "#E.##.#",
      "#.E.#.#",
      "#...#.#",
      "#######"
    )

    r.game.formatCreatures shouldBe "E164, E197, E200, E98, E200"
    r.turn shouldBe 46
    r.score shouldBe 39514
  }

  test("task 2 (example 2)") {
    val map = Vector(
      "#######",
      "#E..EG#",
      "#.#G.E#",
      "#E.##E#",
      "#G..#.#",
      "#..E#.#",
      "#######")

    val (power, r) = findMinElfPower(map)

    power shouldBe 4
    r.score shouldBe 31284
  }

  test("task 1 (example 4)") {
    val map = createGame(Vector(
      "#######",
      "#E.G#.#",
      "#.#G..#",
      "#G.#.G#",
      "#G..#.#",
      "#...E.#",
      "#######"))

    val r = runUntilWin(map)

    r.game.drawWithCreatures shouldBe Seq(
      "#######",
      "#G.G#.#",
      "#.#G..#",
      "#..#..#",
      "#...#G#",
      "#...G.#",
      "#######"
    )

    r.game.formatCreatures shouldBe "G200, G98, G200, G95, G200"
    r.turn shouldBe 35
    r.score shouldBe 27755
  }

  test("task 1 (example 5)") {
    val map = createGame(Vector(
      "#######",
      "#.E...#",
      "#.#..G#",
      "#.###.#",
      "#E#G#G#",
      "#...#G#",
      "#######"))

    val r = runUntilWin(map)

    r.game.drawWithCreatures shouldBe Seq(
      "#######",
      "#.....#",
      "#.#G..#",
      "#.###.#",
      "#.#.#.#",
      "#G.G#G#",
      "#######"
    )

    r.game.formatCreatures shouldBe "G200, G98, G38, G200"
    r.turn shouldBe 54
    r.score shouldBe 28944
  }

  test("task 1 (example 6)") {
    val map = createGame(Vector(
      "#########",
      "#G......#",
      "#.E.#...#",
      "#..##..G#",
      "#...##..#",
      "#...#...#",
      "#.G...G.#",
      "#.....G.#",
      "#########"))

    val r = runUntilWin(map)

    r.game.drawWithCreatures shouldBe Seq(
      "#########",
      "#.G.....#",
      "#G.G#...#",
      "#.G##...#",
      "#...##..#",
      "#.G.#...#",
      "#.......#",
      "#.......#",
      "#########"
    )

    r.game.formatCreatures shouldBe "G137, G200, G200, G200, G200"
    r.turn shouldBe 20
    r.score shouldBe 18740
  }

  test("task 2 (example 6)") {
    val map = Vector(
      "#########",
      "#G......#",
      "#.E.#...#",
      "#..##..G#",
      "#...##..#",
      "#...#...#",
      "#.G...G.#",
      "#.....G.#",
      "#########")

    val (power, r) = findMinElfPower(map)

    power shouldBe 34
    r.score shouldBe 1140
  }

  test("parse") {
    val game = createGame(Vector(
      "#########",
      "#G..G..G#",
      "#.......#",
      "#.......#",
      "#G..E..G#",
      "#.......#",
      "#.......#",
      "#G..G..G#",
      "#########"))

    game.drawWithCreatures shouldBe Seq(
      "#########",
      "#G..G..G#",
      "#.......#",
      "#.......#",
      "#G..E..G#",
      "#.......#",
      "#.......#",
      "#G..G..G#",
      "#########")

  }

  test("make few turns") {
    val game = createGame(Vector(
      "#########",
      "#G..G..G#",
      "#.......#",
      "#.......#",
      "#G..E..G#",
      "#.......#",
      "#.......#",
      "#G..G..G#",
      "#########"))

    val (_, game1) = makeTurn(game)

    game1.drawWithCreatures shouldBe Vector(
      "#########",
      "#.G...G.#",
      "#...G...#",
      "#...E..G#",
      "#.G.....#",
      "#.......#",
      "#G..G..G#",
      "#.......#",
      "#########")

    val (_, game2) = makeTurn(game1)

    game2.drawWithCreatures shouldBe Vector(
      "#########",
      "#..G.G..#",
      "#...G...#",
      "#.G.E.G.#",
      "#.......#",
      "#G..G..G#",
      "#.......#",
      "#.......#",
      "#########")

    val (_, game3) = makeTurn(game2)

    game3.drawWithCreatures shouldBe Vector(
      "#########",
      "#.......#",
      "#..GGG..#",
      "#..GEG..#",
      "#G..G...#",
      "#......G#",
      "#.......#",
      "#.......#",
      "#########")
  }

  private def readFrom(name: String): Seq[String] =
    Source.fromResource(s"advent2018/$name")
      .getLines()
      .toStream
}
