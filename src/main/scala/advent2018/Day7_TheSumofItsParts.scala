package advent2018

import scala.collection.mutable

object Day7_TheSumofItsParts  {

  type Task = Char
  type Edge = (Task, Task)

  def runTasks(edges: Seq[Edge], workersCount: Int, taskBase: Int): Int = {

    val tasks = new TasksTracker(edges)
    val workers = new WorkersTracker(workersCount, taskBase)

    var time = 0
    while(tasks.available || workers.inProgress) {

      while(workers.available(time) && tasks.available)
        workers.addTask(tasks.next, time)

      if(workers.inProgress) {
        val (task, nextTime) = workers.complete

        time = nextTime
        tasks.complete(task)
      }
    }

    time
  }

  def sort(edges: Seq[Edge]): Seq[Task] = {

    val tasks = new TasksTracker(edges)

    val r = Vector.newBuilder[Task]

    while(tasks.available) {
      val next = tasks.next

      r += next
      tasks.complete(next)
    }

    r.result()
  }

  class WorkersTracker(workersCount: Int, taskBase: Int) {
    private val completionQueue =  new mutable.PriorityQueue[(Task, Int)]()(Ordering.by[(Task, Int), Int](-_._2))
    private val workers = Array.ofDim[Int](workersCount)

    def inProgress: Boolean = completionQueue.nonEmpty

    def addTask(task: Task, time: Int): Unit = {

      val size = taskBase + 1 + task.toInt - 'A'.toInt

      val Some(w) = nextWorker(time)

      workers(w) = time + size
      completionQueue.enqueue((task, time + size))
    }

    def complete = completionQueue.dequeue()

    def nextWorker(time: Int) = workers.zipWithIndex.find(_._1 <= time).map(_._2)

    def available(time: Int) = nextWorker(time).isDefined
  }

  class TasksTracker(edges: Seq[Edge]) {
    private val graph = fromEdges(edges)
    private val invertedGraph = fromEdges(edges.map(_.swap))
    private val roots = graph.keySet -- invertedGraph.keySet

    private val tasksQueue = new mutable.PriorityQueue[Task]()(implicitly[Ordering[Task]].reverse)
    tasksQueue.enqueue(roots.toSeq: _*)

    private val completed = mutable.Set.empty[Task]

    private def fromEdges(edges: Seq[Edge]): Map[Task, Set[Task]] = {

      val m = mutable.Map.empty[Task, mutable.Builder[Task, Set[Task]]]

      for ((from, to) <- edges)
        m.getOrElseUpdate(from, Set.newBuilder) += to

      m.map { case (n, xs) => n -> xs.result() }
        .toMap
        .withDefaultValue(Set.empty)
    }

    def available: Boolean = tasksQueue.nonEmpty

    def next: Task = {
      val t = tasksQueue.dequeue()

      if(completed.contains(t)) next
      else t
    }

    def complete(task: Task): Unit = {
      completed += task

      graph(task)
        .diff(completed)
        .filter(n => (invertedGraph(n) -- completed).isEmpty)
        .foreach(n => tasksQueue.enqueue(n))
    }
  }
}
