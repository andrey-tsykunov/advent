package advent2018

import scala.annotation.tailrec

object Day13 {

  type Grid = Vector[String]

  sealed trait Direction {
    val x: Int
    val y: Int
    val char: Char

    def turn(turn: Direction): Direction = (this, turn) match {
      case (Top, Left) => Left
      case (Top, Right) => Right
      case (Bottom, Left) => Right
      case (Bottom, Right) => Left
      case (Left, Left) => Bottom
      case (Left, Right) => Top
      case (Right, Left) => Top
      case (Right, Right) => Bottom
      case (d, _) => d
    }

    def nextTurnDirection = this match {
      case Left => Top
      case Top => Right
      case Right => Left
      case _ => throw new IllegalArgumentException(s"Unsupported turn direction: $this")
    }
  }

  case object Left extends Direction {
    val x = -1
    val y = 0
    val char = '<'
  }

  case object Right extends Direction {
    val x = 1
    val y = 0
    val char = '>'
  }

  case object Top extends Direction {
    val x = 0
    val y = -1
    val char = '^'
  }

  case object Bottom extends Direction {
    val x = 0
    val y = 1
    val char = 'v'
  }

  object Direction {
    val all = List(Left, Top, Right, Bottom)
    def parse(c: Char): Option[Direction] = all.find(_.char == c)
  }

  case class Coord(x: Int, y: Int)

  case class Cart(coord: Coord, moveDirection: Direction, turnDirection: Direction = Left) {
    def move(grid: Grid): Cart = {
      val moved = Coord(coord.x + moveDirection.x, coord.y + moveDirection.y)

      (grid(moved.y)(moved.x), moveDirection) match {
        case ('+', _) => Cart(moved, moveDirection.turn(turnDirection), turnDirection.nextTurnDirection)

        case ('\\', Top) => Cart(moved, Left, turnDirection)
        case ('\\', Left) => Cart(moved, Top, turnDirection)
        case ('\\', Bottom) => Cart(moved, Right, turnDirection)
        case ('\\', Right) => Cart(moved, Bottom, turnDirection)

        case ('/', Top) => Cart(moved, Right, turnDirection)
        case ('/', Right) => Cart(moved, Top, turnDirection)
        case ('/', Left) => Cart(moved, Bottom, turnDirection)
        case ('/', Bottom) => Cart(moved, Left, turnDirection)

        case _ => Cart(moved, moveDirection, turnDirection)
      }
    }
  }

  @tailrec
  def runUntilCrash(grid: Grid, carts: Seq[Cart], i: Int = 0): Coord =
    moveCarts(grid, carts) match {
      case (_, crashes) if crashes.nonEmpty => crashes.head
      case (remained, _) => runUntilCrash(grid, remained, i + 1)
    }

  @tailrec
  def runUntilOneRemain(grid: Grid, carts: Seq[Cart], i: Int = 0): Coord =
    moveCarts(grid, carts) match {
      case (remained :: Nil, _) => remained.coord
      case (remained, _) => runUntilOneRemain(grid, remained, i + 1)
    }

  def moveCarts(grid: Grid, carts: Seq[Cart]): (List[Cart], Set[Coord]) = {

    @tailrec
    def go(notMoved: Seq[Cart], cartsByCoord: Map[Coord, Cart], crashSites: Set[Coord] = Set.empty): (List[Cart], Set[Coord]) =
      notMoved match {
        case Nil => (cartsByCoord.values.toList, crashSites)
        case cart :: remained if crashSites.contains(cart.coord) => go(remained, cartsByCoord - cart.coord, crashSites)
        case cart :: remained =>
          val justMoved = cart.move(grid)

          cartsByCoord.get(justMoved.coord) match {
            case Some(_) =>
              go(remained, cartsByCoord - justMoved.coord - cart.coord, crashSites + justMoved.coord)
            case None =>
              go(remained, cartsByCoord - cart.coord + (justMoved.coord -> justMoved), crashSites)
          }
      }

    go(carts.sortBy(x => (x.coord.y, x.coord.x)), carts.map(c => c.coord -> c).toMap)
  }

  val parse: IndexedSeq[String] => (Grid, Seq[Cart]) = xs => {
    val grid = Array.fill[Char](xs.length, xs.head.length)(' ')

    val cartsB = List.newBuilder[Cart]

    xs.indices.foreach { y =>
      val row = xs(y)
      row.indices.foreach { x =>
        Direction.parse(row(x)) match {
          case Some(d) => cartsB += Cart(Coord(x, y), d)
          case _ => grid(y)(x) = row(x)
        }
      }
    }

    val carts = cartsB.result()

    carts.foreach { cart =>
      cart.moveDirection match {
        case Left | Right => grid(cart.coord.y)(cart.coord.x) = '-'
        case Top | Bottom => grid(cart.coord.y)(cart.coord.x) = '|'
      }
    }

    (grid.map(_.mkString).toVector, carts)
  }
}
