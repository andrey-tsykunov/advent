package advent2018

import scala.collection.mutable

object Day7  {

  type Task = Char
  type Edge = (Task, Task)

  def runTasks(edges: Seq[Edge], workersCount: Int, taskBase: Int): Int = {

    val tasks = new TasksTracker(edges)
    val workers = new WorkersTracker(workersCount, taskBase)

    var time = 0
    while(tasks.hasTasks || workers.inProgress) {

      while(workers.available(time) && tasks.hasTasks)
        workers.addTask(tasks.nextTask, time)

      if(workers.inProgress) {
        val (task, nextTime) = workers.completeNext

        time = nextTime
        tasks.completeTask(task)
      }
    }

    time
  }

  def sort(edges: Seq[Edge]): Seq[Task] = {

    val tracker = new TasksTracker(edges)

    val r = Vector.newBuilder[Task]

    while(tracker.hasTasks) {
      val next = tracker.nextTask

      r += next
      tracker.completeTask(next)
    }

    r.result()
  }

  class WorkersTracker(workersCount: Int, taskBase: Int) {
    val completionQueue =  new mutable.PriorityQueue[(Task, Int)]()(Ordering.by[(Task, Int), Int](-_._2))
    val workers = Array.ofDim[Int](workersCount)

    def inProgress = completionQueue.nonEmpty

    def addTask(task: Task, time: Int) = {

      val size = taskBase + 1 + task.toInt - 'A'.toInt

      val Some(w) = nextWorker(time)

      workers(w) = time + size
      completionQueue.enqueue((task, time + size))
    }

    def completeNext = completionQueue.dequeue()

    def nextWorker(time: Int) = workers.zipWithIndex.find(_._1 <= time).map(_._2)

    def available(time: Int) = nextWorker(time).isDefined
  }

  class TasksTracker(edges: Seq[Edge]) {
    val graph = fromEdges(edges)
    val invertedGraph = fromEdges(edges.map(_.swap))
    val roots = graph.keySet -- invertedGraph.keySet

    val tasksQueue = new mutable.PriorityQueue[Task]()(implicitly[Ordering[Task]].reverse)
    tasksQueue.enqueue(roots.toSeq: _*)

    val completed = mutable.Set.empty[Task]

    private def fromEdges(edges: Seq[Edge]): Map[Task, Set[Task]] = {

      val m = mutable.Map.empty[Task, mutable.Builder[Task, Set[Task]]]

      for ((from, to) <- edges)
        m.getOrElseUpdate(from, Set.newBuilder) += to

      m.map { case (n, xs) => n -> xs.result() }
        .toMap
        .withDefaultValue(Set.empty)
    }

    def hasTasks: Boolean = tasksQueue.nonEmpty

    def nextTask: Task = {
      val t = tasksQueue.dequeue()

      if(completed.contains(t)) nextTask
      else t
    }

    def completeTask(task: Task): Unit = {
      completed += task

      graph(task)
        .diff(completed)
        .filter(n => (invertedGraph(n) -- completed).isEmpty)
        .foreach(n => tasksQueue.enqueue(n))
    }
  }
}
