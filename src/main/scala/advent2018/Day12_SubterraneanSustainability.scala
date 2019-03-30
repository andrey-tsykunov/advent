package advent2018

import java.lang.Math.max

import scala.annotation.tailrec

object Day12_SubterraneanSustainability {

  def simulate(initial: String, times: Long, masks: Set[String]): (String, Long) = {

    @tailrec
    def go(state: String, pos: Long, t: Long): (String, Long) = {

      if(t == 0L) return (state, pos)

      val (nextState, nextPos) = next(state, pos, masks)

      //println(state2, pos2)

      if(state == nextState) return (nextState, (t - 1) * (nextPos - pos) + nextPos)

      go(nextState, nextPos, t - 1)
    }

    go(initial, 0L, times)
  }

  def next(state: String, statePos: Long, masks: Set[String]): (String, Long) = {
    val r = s"....$state....".sliding(5)
      .map { s => if(masks.contains(s)) '#' else '.' }
      .mkString

    val start = r.indexOf('#')

    (
      r.substring(start).replaceAll("([.]+$)", ""),
      statePos - 2 + start
    )
  }

  def plantCount(state: String, statePos: Long): Long =
    state.zipWithIndex.collect { case ('#', i) => statePos + i }
      .sum
}
