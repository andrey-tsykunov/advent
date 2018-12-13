package advent2018

trait Memoization {

  def memoize[I,O](f: I => O): I => O = new scala.collection.mutable.HashMap[I, O]() {
    override def apply(x: I): O = getOrElseUpdate(x, f(x))
  }
}
