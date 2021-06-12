package exercise2

import scala.annotation.tailrec
import scala.util.Try


sealed trait StackLike[T] {
  def push(elem: T): StackLike[T]

  def pop(): Try[StackLike[T]]

  def top(): Option[T]

  def isEmpty: Boolean

  def reverse(): StackLike[T]
}


case class StackEmpty[T]() extends StackLike[T] {
  override def push(elem: T): StackLike[T] = Stack(elem, this)

  override def pop(): Try[StackLike[T]]= Try(StackEmpty())

  override def top(): Option[T] = None

  override def isEmpty: Boolean = true

  override def reverse(): StackLike[T] = this
}


case class Stack[T](head: T, tail: StackLike[T]) extends StackLike[T] {
  override def push(elem: T): StackLike[T] = Stack(elem, this)

  override def pop(): Try[StackLike[T]]= Try(tail)

  override def top(): Option[T] = Option(head)

  override def isEmpty: Boolean = false

  override def reverse(): StackLike[T] = {
    val beginStack = Stack(head, StackEmpty())
    this.loopReverse(tail, beginStack)
  }

  @tailrec
  private def loopReverse(tail: StackLike[T], all: Stack[T]): StackLike[T] = tail match {
    case Stack(smallHead, smallTail) => {
      val newAll = Stack(smallHead, all)
      this.loopReverse(smallTail, newAll)
    }
    case StackEmpty() => all
  }
}