package exercise2

import scala.util.{Try}

sealed trait StackLike[T] {
  def push(elem: T): StackLike[T]

  def pop(): Try[StackLike[T]] = this match {
    case StackCons(_, tail) => Try(tail)
    case StackEmpty() => Try(StackEmpty())
  }

  def top(): Option[T]

  def isEmpty: Boolean

  def reverse(reverseStack: StackLike[T] = StackEmpty()): StackLike[T]
}

case class StackCons[T](head: T, tail: StackLike[T]) extends StackLike[T] {
  override def push(elem: T): StackLike[T] = StackCons(elem, this)

  override def top(): Option[T] = Option(head)

  override def isEmpty: Boolean = false

  override def reverse(reverseStack: StackLike[T] = StackEmpty()): StackLike[T] = reverseStack match {
    case StackCons(_, _) =>
      val tempStack = reverseStack.push(this.head)
      this.reverseInternal(this.tail, tempStack)
    case StackEmpty() =>
      val tempStack = StackCons(this.head, StackEmpty())
      this.reverseInternal(this.tail, tempStack)
  }

  private def reverseInternal(tail: StackLike[T], tempStack: StackLike[T]): StackLike[T] = {
    tail match {
      case StackCons(_, _) => this.tail.reverse(tempStack)
      case StackEmpty() => tempStack
    }
  }
}

case class StackEmpty[T]() extends StackLike[T] {
  override def push(elem: T): StackLike[T] = StackCons(elem, this)

  override def top(): Option[T] = None

  override def isEmpty: Boolean = true

  override def reverse(newStack: StackLike[T] = StackEmpty()): StackLike[T] = this
}