package advent2018

object Day8_MemoryManeuver {

  def parse(s: String): Node = {

    val data = s.split(' ').map(_.toInt)

    def go(start: Int): (Node, Int) = {
      val childrenSize = data(start)
      val metaSize = data(start + 1)

      var nextStart = start + 2
      val children = Vector.newBuilder[Node]
      (0 until childrenSize).foreach { _ =>
        val (n, size) = go(nextStart)

        children += n
        nextStart += size
      }

      val node = Node(children.result(), data.slice(nextStart, nextStart + metaSize))

      (node, nextStart - start + metaSize)
    }

    go(0)._1
  }

  case class Node(children: IndexedSeq[Node], metadata: Seq[Int]) {
    def sum: Int = metadata.sum + children.map(_.sum).sum

    def value: Int = {
      if(children.isEmpty) metadata.sum
      else {
        val c = children.lift
        metadata.map { i => c(i - 1).map(_.value).getOrElse(0) }.sum
      }
    }
  }
}
