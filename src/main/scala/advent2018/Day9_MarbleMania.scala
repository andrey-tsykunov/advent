package advent2018

object Day9_MarbleMania {

  class RingItem private(val value: Int, var previous: RingItem, var next: RingItem) {
    def move(count: Int): RingItem = count match {
      case x if x > 0 => next.move(count - 1)
      case x if x < 0 => previous.move(count + 1)
      case 0 => this
    }

    def remove(): Int = {
      previous.next = next
      next.previous = previous
      value
    }

    def insert(value: Int): RingItem = {
      val m = new RingItem(value, this, next)

      this.next.previous = m
      this.next = m
      m
    }
  }

  object RingItem {
    def starter() = {
      val r = new RingItem(0, null, null)
      r.previous = r
      r.next = r
      r
    }
  }

  def runGame(playersCount: Int, marblesCount: Int): Long = {

    var current = RingItem.starter()
    val playersScore = Array.ofDim[Long](playersCount)

    (1 to marblesCount).foreach {
      case m if m % 23 == 0 =>
        current = current.move(-7)

        playersScore(m % playersCount) += m + current.remove()
        current = current.next

      case m =>
        current = current.move(1).insert(m)
    }

    playersScore.max
  }
}
