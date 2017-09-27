package greedyProblems.huffmanCoding

/**
  * Created by Robert-PC on 9/21/2017.
  */
object HuffmanCoding {

  def getFrequency(input: String): List[(Char, Int)] = {
    var frequency = scala.collection.mutable.Map[Char, Int]()

    input.foreach(c =>
    if(frequency.contains(c))
      frequency(c) = frequency.getOrElse(c, 0) + 1
    else
      frequency += (c -> 1)
    )

    frequency.toList
  }

  def getChars(input: Tree): List[Char] = {
    input match {
      case Fork(_, cs, _, _) => cs
      case LeafNode(c, _)    => List(c)
    }
  }

  def getWeight(input: Tree): Int ={
    input match {
      case Fork(_, _, ws, _) => ws
      case LeafNode(_, w)    => w
    }
  }

  def orderFrequencyList(input: List[(Char, Int)]): List[(Char, Int)] = {
    input.sortWith((f1, f2) => f1._2 < f2._2)
  }

  def makeTree(left: Tree, right: Tree): Tree = {
    Fork(left, getChars(left) ::: getChars(right), getWeight(left) + getWeight(right), right)
  }

  def combine(trees: List[Tree]): List[Tree] = trees match {
    case fst::snd::tail => (makeTree(fst, snd) :: tail).sortWith((t1, t2) => getWeight(t1) < getWeight(t2))
    case _                 => trees
  }
}
