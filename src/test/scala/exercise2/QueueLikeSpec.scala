package exercise2

import org.scalatest.flatspec.AnyFlatSpec


class QueueLikeSpec extends AnyFlatSpec {

  val exception = new RuntimeException("message")

  "enqueue" should "enqueue elements" in {
    // Arrange
    val queue = StackedQueue(StackEmpty[Int](), StackEmpty[Int]())
    // Act && Assert
    assert(queue.enqueue(1) == StackedQueue(StackCons(1, StackEmpty()), StackEmpty()))
  }

  "dequeue" should "dequeue elements" in {
    // Arrange
    val queue = StackedQueue(StackEmpty(), StackCons(1, StackEmpty()))
    // Act && Assert
    assert(queue.dequeue().get == StackedQueue(StackEmpty(), StackEmpty()))
  }

  "front" should "returns front element" in {
    assert(StackedQueue(StackCons(1, StackCons(2, StackEmpty())), StackEmpty()).front().get == 2)
    assert(StackedQueue(StackEmpty(), StackCons(2, StackCons(1, StackEmpty()))).front().get == 2)
  }

  "empty" should "return if empty" in {
    assert(StackedQueue(StackEmpty(), StackEmpty()).isEmpty)
    assert(!StackedQueue(StackCons(1, StackCons(2, StackEmpty())), StackEmpty()).isEmpty)
  }
}
