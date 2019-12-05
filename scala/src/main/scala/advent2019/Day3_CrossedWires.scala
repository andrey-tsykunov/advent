package advent2019

object Day3_CrossedWires {

  case class Segment(dx: Int, dy: Int, distance: Int)
  case class Wire(segments: Seq[Segment])

  case class Position(x: Int, y: Int) {
    def move(dx: Int, dy: Int): Position = Position(x + dx, y + dy)
  }

  def parse(seg: String): Segment = {
    val distance = seg.substring(1).toInt
    seg(0) match {
      case 'U' => Segment(0, 1, distance)
      case 'D' => Segment(0, -1, distance)
      case 'L' => Segment(-1, 0, distance)
      case 'R' => Segment(1, 0, distance)
    }
  }

  def wireMap(wire: Wire): Map[Position, Int] = {

    val r = Seq.newBuilder[(Position, Int)]

    var position = Position(0, 0)
    var i = 0

    for(segment <- wire.segments)
      for (_ <- 0 until segment.distance) {
        position = position.move(segment.dx, segment.dy)
        i += 1

        r.addOne(position, i)
      }

    r.result().toMap
  }

  def closestIntersectionDistance(wires: Seq[Wire]): Int =
    wires.map(wireMap).map(_.keySet).iterator
      .reduce(_ intersect _)
      .map(p => Math.abs(p.x) + Math.abs(p.y))
      .min

  def fewestStepsToIntersection(wires: Seq[Wire]): Int = {
    val wireMaps = wires.map(wireMap)

    wireMaps.map(_.keySet).iterator
      .reduce(_ intersect _)
      .map { i =>  wireMaps.map(m => m(i)).sum }
      .min
  }


}
