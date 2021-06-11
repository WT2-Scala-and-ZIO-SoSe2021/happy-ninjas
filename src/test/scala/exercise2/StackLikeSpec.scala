package exercise2

import org.scalatest.flatspec.AnyFlatSpec
import scala.util.Success


class StackLikeSpec extends AnyFlatSpec {

  val exception = new RuntimeException("message")

  /*

  "push" should "push elements" in {
    assert(StackEmpty().push(1) == StackCons(1, StackEmpty()))
    assert(StackCons(1, StackEmpty()).push(2) == StackCons(2, StackCons(1, StackEmpty())))
  }

  "pop" should "pop elements" in {
    // Arrange
    val emptyStack = StackEmpty()
    val nonEmptyStack = StackCons(2, StackCons(1, StackEmpty())).pop()
    // Act && Assert
    assert(emptyStack.pop() == Success(StackEmpty()))
    assert(nonEmptyStack.get == StackCons(1, StackEmpty()))
  }

  "top" should "return first element" in {
    assert(StackEmpty().top().isEmpty)
    assert(StackCons(1, StackEmpty()).top().get == 1)
  }

  "isEmpty" should "return first element" in {
    assert(StackEmpty().isEmpty)
    assert(!StackCons(1, StackEmpty()).isEmpty)
  }

  "reverse" should "return first element" in {
    val stack = StackCons(1, StackCons(2, StackCons(3, StackEmpty())))
    assert(stack.reverse() == StackCons(3, StackCons(2, StackCons(1, StackEmpty()))))
  }

  */
}
