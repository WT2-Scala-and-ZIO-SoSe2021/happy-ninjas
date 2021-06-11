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

case class Queue[T](inQueue: StackLike[T], outQueue: StackLike[T]) extends QueueLike[T] {
  override def enqueue(elem: T): Queue[T] = {
    Queue(this.inQueue.push(elem), this.outQueue)
  }

  override def dequeue(): Try[QueueLike[T]] = this.outQueue match {
    case Stack(_, _) => {
      val out = this.outQueue.pop().get
      Try(Queue(this.inQueue, out))
    }
    case StackEmpty() => this.inQueue match {
      case Stack(_, _) => {
        val out = this.inQueue.reverse().pop().get
        val in = StackEmpty[T]()
        Try(Queue(in, out))
      }
      case StackEmpty() => Try(Queue(StackEmpty(), StackEmpty()))
    }
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
