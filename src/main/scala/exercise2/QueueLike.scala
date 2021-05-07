package exercise2

import scala.util.Try

trait QueueLike[T] {
  val inQueue: StackLike[T]
  val outQueue: StackLike[T]

  def enqueue(elem: T): QueueLike[T]

  def dequeue(): Try[QueueLike[T]]

  def front(): Option[T]

  def isEmpty: Boolean
}

case class StackedQueue[T](inQueue: StackLike[T], outQueue: StackLike[T]) extends QueueLike[T] {
  override def enqueue(elem: T): StackedQueue[T] = {
    StackedQueue(this.inQueue.push(elem), this.outQueue)
  }

  override def dequeue(): Try[QueueLike[T]] = this.outQueue match {
    case StackEmpty() => this.inQueue match {
      case StackEmpty() => Try(StackedQueue(StackEmpty[T](), StackEmpty[T]()))
      case StackCons(_, _) =>
        val out = this.inQueue.reverse().pop().get
        val in = StackEmpty[T]()
        Try(StackedQueue(in, out))
    }
    case StackCons(_, _) =>
      val out = this.outQueue.pop().get
      Try(StackedQueue(this.inQueue, out))
  }

  override def front(): Option[T] = this.outQueue match {
    case StackEmpty() => this.inQueue match {
      case StackEmpty() => None
      case _ => this.inQueue.reverse().top()
    }
    case _ => this.outQueue.top()
  }

  override def isEmpty: Boolean = inQueue.isEmpty && outQueue.isEmpty
}