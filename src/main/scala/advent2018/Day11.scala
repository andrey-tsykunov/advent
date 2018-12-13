package advent2018

object Day11 {

  def findMaxSquare(serial: Int, maxSize: Int = 300): (Int, Int, Int) = {

    val grid = Array.ofDim[Int](301, 301)

    for(x <- 1 to 300; y <- 1 to 300)
      grid(y)(x) = cellPower(x, y, serial)

    var maxXyz = (0, 0, 0)
    var max = Int.MinValue

    var dp_1 = grid.clone()
    var dp_2 = Array.ofDim[Int](301, 301)

    for (z <- 2 to maxSize) {

      val next = Array.ofDim[Int](301, 301)

      for (x <- 1 to 301 - z; y <- 1 to 301 - z) {
        val bottomLeft = dp_1(y + 1)(x)
        val topRight = dp_1(y)(x + 1)
        val center = dp_2(y + 1)(x + 1)

        val power = bottomLeft + topRight - center + grid(y)(x) + grid(y + z - 1)(x + z - 1)

        if(power > max) {
          max = power
          maxXyz = (x, y, z)
        }

        next(y)(x) = power
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
