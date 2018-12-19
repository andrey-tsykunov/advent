package advent2018

import java.util

import scala.annotation.tailrec
import scala.collection._

object Day15 {

  type GameMap = Vector[String]
  type Path = List[Position]

  private val wall = '#'
  private val goblin = 'G'
  private val elf = 'E'
  private val empty = '.'

  case class Position(row: Int, column: Int) {

    override def toString: String = s"($row, $column)"
    def nearby: Stream[Position] = Stream(Position(row, column + 1), Position(row, column - 1), Position(row + 1, column), Position(row - 1, column))
  }

  object Position {
    import scala.math.Ordered.orderingToOrdered
    implicit val ordering = new Ordering[Position] {
      override def compare(x: Position, y: Position): Int = (x.row, x.column).compare((y.row, y.column))
    }
  }

  case class Creature (id: Int, code: Char, points: Int, position: Position) {
    require(code == elf || code == goblin)

    override def toString: String = s"$code$id @ $position ($points)"

    def enemyCode: Char = if(code == elf) goblin else elf
    def isAlive = points > 0

    def takeHit(damage: Int): Creature = copy(points = points - damage)
    def move(position: Position): Creature = copy(position = position)
  }

  case class GameSettings(attackPowers: Map[Char, Int])

  case class GameState(map: GameMap, creatures: Seq[Creature], settings: GameSettings) {

    require(creatures.map(_.position).distinct.size == creatures.size, "creatures stepped on each other")

    def drawWithCreatures: GameMap = {
      val chars = map.map(_.toCharArray).toArray
      creatures.foreach(c => chars(c.position.row)(c.position.column) = c.code)
      chars.map(_.mkString).toVector
    }

    def formatCreatures: String =
      creatures.sortBy(_.position).map(c => s"${c.code}${c.points}").mkString(", ")

  }

  private def bfs(map: GameMap, from: Position, destinations: Set[Position], blocked: Set[Position]): Seq[Path] = {

    val visited = new mutable.HashSet[Position]
    visited += from

    val queue = new util.LinkedList[Seq[Path]]()
    queue.add(Seq(List(from)))

    while(queue.size > 0) {
      val xx = queue.poll()
      val nextPaths = xx.flatMap { path =>
        path.head.nearby
          .filterNot(p => map(p.row)(p.column) == wall)
          .filterNot(blocked.contains)
          .filterNot(visited.contains)
          .map { p =>

            if(!destinations.contains(p))
              visited += p
            p :: path
          }
      }

      val pathsToDestinations = nextPaths.collect { case last :: rest if destinations.contains(last) => rest }

      if(pathsToDestinations.nonEmpty)
        return pathsToDestinations

      if(nextPaths.nonEmpty)
        queue.add(nextPaths)
    }

    Nil
  }

  def makeTurn(input: GameState): (Boolean, GameState) = {

    val positionToCreature = mutable.Map(input.creatures.map(c => c.position -> c):_*)
    val idToCreature = mutable.Map(input.creatures.map(c => c.id -> c):_*)

    def nextMove(creature: Creature): Option[Position] = {

      val (peers, enemies) = positionToCreature.partition { case (_, c) => c.code == creature.code }

      val attackPath: Option[Path] = bfs(input.map, creature.position, enemies.keySet, peers.keySet)
        .sortBy(_.head)
        .headOption

      attackPath.filter(_.length > 1).map { path =>
        bfs(input.map, path.head, Set(path.last), positionToCreature.keySet - path.last)
          .map(_.head)
          .min
      }
    }

    def findAttackTarget(target: Char, from: Position): Option[Creature] =
      from.nearby.flatMap(p => positionToCreature.get(p).filter(_.code == target) )
        .sortBy(c => (c.points, c.position))
        .headOption

    def move(before: Creature, position: Position): Creature = {
      val after = before.move(position)
      positionToCreature -= before.position
      positionToCreature += after.position -> after
      idToCreature += after.id -> after
      after
    }

    def attack(source: Creature, target: Creature): Unit = {
      val after = target.takeHit(input.settings.attackPowers(source.code))

      if(after.points > 0) {
        positionToCreature += target.position -> after
        idToCreature += target.id -> after
      }
      else {
        positionToCreature -= after.position
        idToCreature -= after.id
      }
    }

    input.creatures
      .toStream
      .sortBy(_.position)
      .map(_.id)
      .flatMap(id => idToCreature.get(id).filter(_.isAlive))
      .foreach { creature =>

        if(idToCreature.values.map(_.code).toStream.distinct.size == 1)
          return (true, input.copy(creatures = idToCreature.values.toVector))

        val moved = nextMove(creature)
          .map(p => move(creature, p))
          .getOrElse(creature)

        findAttackTarget(creature.enemyCode, moved.position).foreach(t => attack(moved, t))
      }

    (false, input.copy(creatures = idToCreature.values.toVector))
  }

  def createGame(maze: Seq[String], elfPower: Int = 3, goblinPower: Int = 3): GameState = {
    val parsed = maze.map(_.toCharArray).toArray

    val creatures = new mutable.ArrayBuffer[Creature]

    for(row <- parsed.indices; column <- parsed(row).indices) {
      val cell = parsed(row)(column)
      if(cell == elf || cell == goblin) {
        creatures += Creature(creatures.size, cell, 200, Position(row, column))
        parsed(row)(column) = empty
      }
    }

    val settings = GameSettings(Map(goblin -> goblinPower, elf -> elfPower))
    GameState(parsed.map(_.mkString).toVector, creatures.toVector, settings)
  }

  case class GameResult(turn: Int, game: GameState) {
    def score: Int = turn * game.creatures.map(_.points).sum
  }

  def runUntilWin(game: GameState): GameResult = {
    @tailrec
    def run(game: GameState, turn: Int): GameResult = {

      val (completed, r) = makeTurn(game)

      if(completed) GameResult(turn - 1, r)
      else run(r, turn + 1)
    }

    run(game, 1)
  }

  def runNTimes(game: GameState, times: Int): GameState =
    (1 to times).foldLeft(game) { case (g, _) => makeTurn(g)._2 }

  def findMinElfPower(maze: Seq[String]): (Int, GameResult) = {
    Stream.from(4).map { power =>
      val game = createGame(maze, power)
      val elvesBefore = game.creatures.count(_.code == elf)
      val r = runUntilWin(game)
      val elvesAfter = r.game.creatures.count(_.code == elf)
      (power, elvesBefore == elvesAfter, r)
    }
      .collect { case (power, true, game) => (power, game) }
      .head
  }

}
