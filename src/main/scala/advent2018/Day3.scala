package advent2018

case class Claim(id: Int, left: Int, top: Int, width: Int, height: Int) {
  def right = left + width
  def bottom = top + height
}

object Day3 {
  def task1(claims: Seq[Claim]): Int = {

    claims.flatMap { c =>
      for {
        i <- c.left until c.right
        j <- c.top until c.bottom
      }
        yield (i, j)
    }
      .groupBy(identity)
      .count { case (_, xs) => xs.size > 1 }
  }

  def task1_matrix(claims: Seq[Claim]): Int = {

    val data = Array.ofDim[Int](1000, 1000)

    claims.foreach { c =>
      for {
        i <- c.left until c.right
        j <- c.top until c.bottom
      }
        data(i)(j) = data(i)(j) + 1
    }

    data.map(a => a.count( _ > 1)).sum
  }

  def overlap(c1: Claim, c2: Claim): Set[(Int, Int)] = {
    val isOverlap = !(c1.left > c2.right || c2.left > c1.right || c2.top > c1.bottom || c1.top > c2.bottom)

    if(isOverlap) {
      val xs = List(c1.left, c1.right, c2.left, c2.right).sorted
      val ys = List(c1.top, c1.bottom, c2.top, c2.bottom).sorted

      val overlap = for {
        x <- xs(1) until xs(2)
        y <- ys(1) until ys(2)
      }
        yield (x, y)

      overlap.toSet
    }
    else Set.empty
  }

  def task1_overlap(claims: Seq[Claim]): Int = {

    val overlaps = for {
      c1 <- claims
      c2 <- claims if c1.id != c2.id
    }
      yield overlap(c1, c2)

    overlaps.foldLeft(Set.empty[(Int, Int)])(_ ++ _).size
  }

  def task2(claims: Seq[Claim]): Int = {

    claims.flatMap { c =>
      for {
        i <- c.left + 1 to c.left + c.width
        j <- c.top + 1 to c.top + c.height
      }
        yield ((i, j), c.id)
    }
      .groupBy(_._1)
      .collect { case (_, xs) if xs.size > 1 => xs.map(_._2).toSet }
      .foldLeft(claims.map(_.id).toSet)(_ -- _)
      .head
  }
}
