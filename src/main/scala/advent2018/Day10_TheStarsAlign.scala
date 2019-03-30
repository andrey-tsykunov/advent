package advent2018

import scala.annotation.tailrec

object Day10_TheStarsAlign {

  case class Point(left: Int, top: Int, leftVelocity: Int, topVelocity: Int) {
    def next = copy(left = left + leftVelocity, top = top + topVelocity)
  }

  case class Boundary(minLeft: Int, minTop: Int, maxLeft: Int, maxTop: Int) {
    def leftSize: Int = maxLeft - minLeft + 1
    def topSize: Int = maxTop - minTop + 1
  }

  case class PointsArea(points: Seq[Point]) {

    val boundary = points.foldLeft(Boundary(Int.MaxValue, Int.MaxValue, Int.MinValue, Int.MinValue)) {
      case (Boundary(minLeft, minTop, maxLeft, maxTop), Point(left, top, _, _)) =>
        Boundary(Math.min(minLeft, left), Math.min(minTop, top), Math.max(maxLeft, left), Math.max(maxTop, top))
    }

    val size = boundary.leftSize.toLong * boundary.topSize

    def print(): Seq[String] = {

      val array = Array.fill[Char](boundary.topSize, boundary.leftSize)(' ')
      points.foreach { p =>
        array(p.top - boundary.minTop)(p.left - boundary.minLeft) = '#'
      }

      array.map(_.mkString)
    }

    def next() = PointsArea(points.map(_.next))
  }

  def printMinArea(points: Seq[Point]): (Seq[String], Int) = {

    @tailrec
    def go(current: PointsArea, i: Int): (Seq[String], Int) = {
      val next = current.next()

      if(next.size > current.size) (current.print(), i)
      else go(next, i + 1)
    }

    go(PointsArea(points), 0)
  }

}
