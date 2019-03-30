package advent2018

import scala.annotation.tailrec


object Day18_SettlersofTheNorthPole {

  def calcResourceValue(map: Seq[String]): (Int, Int) =
    (map.map(row => row.count(_ == '#')).sum, map.map(row => row.count(_ == '|')).sum)

  def resourceValueAfterNMinutes(initial: Seq[String], n: Long): Int = {

    @tailrec
    def go(map: Seq[String], rvToTurn: Map[(Int, Int), Long], turnToRv: Map[Long, (Int, Int)], n: Long, cycleSize: Option[Int]): (Int, Int) = {
      if(n == 0L || cycleSize.exists(n % _ == 0) ) return calcResourceValue(map)

      def withinMap(row: Int, column: Int): Boolean = row >= 0 && row < map.size && column >= 0 && column < map(row).length

      def nearby(row: Int, column: Int): Seq[Char] = {
        for {
          r <- row - 1 to row + 1
          c <- column - 1 to column + 1 if withinMap(r, c) && (r != row || c != column)
        }
          yield map(r)(c)
      }

      def transform() = map.indices.map { r =>
        map(r).indices.map { c =>
          val grouped = nearby(r, c).groupBy(identity)

          map(r)(c) match {
            case '.' =>
              if(grouped.getOrElse('|', Nil).size >= 3) '|'
              else '.'
            case '|' =>
              if(grouped.getOrElse('#', Nil).size >= 3) '#'
              else '|'
            case '#' =>
              if(grouped.getOrElse('#', Nil).nonEmpty && grouped.getOrElse('|', Nil).nonEmpty) '#'
              else '.'
          }
        }.mkString
      }

      val r = transform()
      val rv = calcResourceValue(r)

      val size = rvToTurn.get(rv).flatMap { prev =>
        val size = (prev - n).toInt

        turnToRv.get(prev + size)
          .filter(_ == rv)
          .map(_ => size)
      }

      go(r, rvToTurn + (rv -> n), turnToRv + (n -> rv), n - 1, size)
    }

    val (lamber, wood) = go(initial, Map.empty, Map.empty, n, None)

    lamber * wood
  }
}
