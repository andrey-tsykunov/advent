package advent2018

object Day11 {

  def findMaxSquare(serial: Int, maxSize: Int = 300): (Int, Int, Int) = {
    val dp = Array.ofDim[Int](301, 301, 301)

    for(x <- 1 to 300; y <- 1 to 300)
      dp(y)(x)(1) = cellPower(x, y, serial)

    var maxXyz = (0, 0, 0)
    var max = Int.MinValue

    for (z <- 2 to maxSize) {
      for (x <- 1 to 301 - z; y <- 1 to 301 - z) {
        val bottomLeft = dp(y + 1)(x)(z - 1)
        val topRight = dp(y)(x + 1)(z - 1)
        val center = dp(y + 1)(x + 1)(z - 2)

        val power = bottomLeft + topRight - center + dp(y)(x)(1) + dp(y + z - 1)(x + z - 1)(1)

        if(power > max) {
          max = power
          maxXyz = (x, y, z)
        }

        dp(y)(x)(z) = power
      }
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
