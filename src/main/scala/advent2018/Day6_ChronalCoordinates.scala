package advent2018

import scala.collection.mutable

object Day6_ChronalCoordinates {

  case class Coord(left: Int, top: Int) {
    def neighbours: Stream[Coord] = Stream(
      Coord(left - 1, top),
      Coord(left, top - 1),
      Coord(left + 1, top),
      Coord(left, top + 1)
    )
  }

  case class CoordProps(owner: Int, dist: Int)

  def task1(coords: Seq[Coord]): Int = {
    var visited = coords.zipWithIndex.map { case (x, i: Int) => x -> CoordProps(i, 0) }.toMap

    val (leftMin, topMin, leftMax, topMax) = findBoundary(coords)

    def withinBoundary(c: Coord) = c.left >= leftMin && c.left <= leftMax && c.top >= topMin && c.top <= topMax

    def onTheBorder(c: Coord) = c.left == leftMin || c.left == leftMax || c.top == topMin || c.top == topMax

    val queue = mutable.Queue.empty[(Coord, CoordProps)]
    queue.enqueue(visited.toSeq: _*)

    while(queue.nonEmpty) {
      val (coord, CoordProps(owner, dist)) = queue.dequeue()

      val neighboursDist = dist + 1
      coord.neighbours
        .filter(withinBoundary)
        .foreach { n =>
          visited.get(n) match {
            case Some(CoordProps(previousOwner, d)) if owner != previousOwner && d == neighboursDist =>
              visited += (n -> CoordProps(-1, neighboursDist))
            case None =>
              visited += (n -> CoordProps(owner, neighboursDist))
              queue.enqueue((n, CoordProps(owner, neighboursDist)))
            case _ =>
          }
        }
    }

    visited.toSeq
      .groupBy { case (_, CoordProps(owner, _)) => owner  }
      .map {
        case (-1, _) => 0
        case (_, xs) =>
          if (xs.exists { case (c, _) => onTheBorder(c) }) 0
          else xs.size
      }.max
  }

  def findBoundary(coords: Seq[Coord]) = (
    coords.minBy(_.left).left,
    coords.minBy(_.top).top,
    coords.maxBy(_.left).left,
    coords.maxBy(_.top).top
  )

  /*
    Won't work for large limits...
   */
  def task2(coords: Seq[Coord], limit: Int): Int = {

    val (leftMin, topMin, leftMax, topMax) = findBoundary(coords)

    def totalDist(c: Coord): Int = coords.map(x => Math.abs(x.left - c.left) + Math.abs(x.top - c.top)).sum

    val cWithDist = for {
      l <- leftMin to leftMax
      t <- topMin to topMax
      c = Coord(l, t)
    }
      yield c -> totalDist(c)

    cWithDist.count { case (_, dist) => dist < limit }
  }
}
