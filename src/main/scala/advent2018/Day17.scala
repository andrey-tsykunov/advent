package advent2018

import scala.collection.mutable

object Day17 {

  case class ClayLine(xs: List[Int], ys: List[Int])

  case class Position(x: Int, y: Int) {
    def below = Position(x, y + 1)
    def left = Position(x - 1, y)
    def right = Position(x + 1, y)
  }

  def countReachableTiles(clayLines: Seq[ClayLine]): (Int, Int) = {

    val clays = clayPositions(clayLines)
    val maxY = clays.map(_.y).max
    val minY = clays.map(_.y).min

    val water = mutable.Set.empty[Position]
    val restWater = mutable.Set.empty[Position]

    def dfsMarkRestWater(from: Position): Unit = {

      if(clays.contains(from)) return
      if(restWater.contains(from)) return

      restWater += from

      dfsMarkRestWater(from.below)
      dfsMarkRestWater(from.left)
      dfsMarkRestWater(from.right)
    }

    def dfs(from: Position): Boolean = {

      if(clays.contains(from)) return true
      if(water.contains(from)) return true
      if(from.y > maxY) return false

      val supportBelow = if(restWater.contains(from.below)) true
        else if(water.contains(from.below)) false
        else dfs(from.below)

      if(supportBelow) dfsMarkRestWater(from.below)

      water += from

      val filledLeft = supportBelow && dfs(from.left)
      val filledRight = supportBelow && dfs(from.right)
      supportBelow && filledLeft && filledRight
    }

    dfs(Position(500, 0).below)

    //printMap(clays, water, restWater)

    (water.count(_.y >= minY), restWater.count(_.y >= minY))
  }

  def printMap(clays: Set[Position], water: scala.collection.Set[Position], restWater: scala.collection.Set[Position]): Unit = {

    val xs = clays.map(_.x) ++ water.map(_.x)
    val minX = xs.min
    val maxX = xs.max

    val ys = clays.map(_.y) ++ water.map(_.y)
    val maxY = ys.max
    val minY = ys.min

    val data = Array.fill[Char](maxY + 1, maxX - minX + 1)(' ')

    for(x <- minX to maxX; y <- minY to maxY) {

      if(clays.contains(Position(x, y)))
        data(y)(x - minX) = '#'
      else if(restWater.contains(Position(x, y)))
        data(y)(x - minX) = '~'
      else if(water.contains(Position(x, y)))
        data(y)(x - minX) = '|'
    }

    data.map(_.mkString).foreach(println)
  }

  private def clayPositions(clays: Seq[ClayLine]): Set[Position] = {
    clays.collect {
      case ClayLine(x :: Nil, ys) => ys.map(y => Position(x, y))
      case ClayLine(xs, y :: Nil) => xs.map(x => Position(x, y))
    }.flatten.toSet
  }
}
