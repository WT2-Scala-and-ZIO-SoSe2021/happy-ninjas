package exercise2

import org.scalatest.flatspec.AnyFlatSpec


class IorSpec extends AnyFlatSpec {

  val exception = new RuntimeException("message")

  "Ior" should "create Left, Right and Both as Ior" in {
    // Assert
    assert(Ior.right(2) === Right(2))
    assert(Ior.right("a string") === Right("a string"))
    assert(Ior.left[String](exception) === Left(exception))
    assert(Ior.both(exception, 2) === Both(exception, 2))
    assert(Ior.both(exception, "a string") === Both(exception, "a string"))
  }

  "map" should "map the function argument" in {
    // Assert
    assert(Ior.right(2).map(value => value * 4) === Right(8))
    assert(Ior.left[String](exception).map(value => value * 4) === Ior.left[String](exception))
    assert(Ior.both(exception, 2).map(value => value * 4) === Ior.both(exception, 8))
  }

  "flatMap" should "bind the given function" in {
    // Assert
    assert(Ior.right(2).flatMap(_ => Ior.right("a string")) === Ior.right("a string"))
    assert(Ior.right("a string").flatMap(_ => Ior.right(2)) === Ior.right(2))
    assert(Ior.right("a string").flatMap(_ => Ior.left[String](exception)) === Ior.left(exception))
  }
}
