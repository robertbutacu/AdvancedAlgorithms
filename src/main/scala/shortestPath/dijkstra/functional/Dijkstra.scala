package shortestPath.dijkstra.functional

import scala.annotation.tailrec

object Dijkstra {
  type CityName      = String
  type Distance      = Int
  type Graph         = Map[Edge, Distance]
  type Path          = (List[Edge], Distance)
  type VisitedNodes  = List[Node]
  type PriorityQueue = List[Node]
  case class Node(name: CityName, tentativeDistance : Distance = Int.MaxValue, previous: Option[Node] = None)
  case class Edge(from: Node, to: Node)

  def shortest(from: Node, to: Node, graph: Graph): Path = {
    //@tailrec
    def go(curr: Node, pq: PriorityQueue, vn: VisitedNodes): Path = {
      (List(), 0)
      /**
        * add curr to visited list
        *
        * updated current node's neighbors
        *
        * update priority queue
        *
        * recursive call to go until curr is equal to to
        */
      val updatedPq = pq :+ curr
      (List(), 0)
    }

    go(from, List(), List())
  }

  def transformNeighbors(graph: Graph): Graph = {
    Map[Edge, Distance]().empty
  }

  def transformEdge(edge: Edge, distance: Distance): Edge = {
    Edge(edge.from, Node(edge.to.name, Math.min(edge.from.tentativeDistance + distance, edge.to.tentativeDistance)))
  }

  def transformPriorityQueue(priorityQueue: PriorityQueue): PriorityQueue = {
    priorityQueue sortWith (_.tentativeDistance < _.tentativeDistance)
  }

}
