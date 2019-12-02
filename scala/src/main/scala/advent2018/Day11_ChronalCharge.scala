package advent2018

import scala.collection.parallel.CollectionConverters._

object Day11_ChronalCharge {

  def findMaxSquare(serial: Int, size: Int = 300, minSize: Int = 1, maxSize: Int = 300): (Int, Int, Int) = {

    require(maxSize <= size)

    val grid = Array.ofDim[Int](size + 1, size + 1)

    for(x <- 1 to size; y <- 1 to size)
      grid(y)(x) = cellPower(x, y, serial)

    var maxXyz = (0, 0, 0)
    var max = Int.MinValue

    var dp_1 = grid.clone()
    var dp_2 = Array.ofDim[Int](size + 1, size + 1)

    for (z <- 2 to maxSize) {

      val next = Array.ofDim[Int](size + 1, size + 1)

      (1 to size + 1 - z).par.foreach { x =>
        for (y <- 1 to size + 1 - z) {
          val bottomLeft = dp_1(y + 1)(x)
          val topRight = dp_1(y)(x + 1)
          val center = dp_2(y + 1)(x + 1)

          val power = bottomLeft + topRight - center + grid(y)(x) + grid(y + z - 1)(x + z - 1)

          if(power > max && z >= minSize) {
            max = power
            maxXyz = (x, y, z)
          }

          next(y)(x) = power
        }
      }

      dp_2 = dp_1
      dp_1 = next
    }

    maxXyz
  }

  def cellPower(x: Int, y: Int, serial: Int): Int = {
    val rackId = x + 10
    val level = (rackId * y + serial) * rackId
    val hDigit = (level % 1000) / 100
    hDigit.toInt - 5
  }

}
