package exercise1

import org.scalatest.flatspec.AnyFlatSpec


class TwentyOneSpec extends AnyFlatSpec {

  "parse" should "parses a string to its internal game value" in {
    assert(TwentyOne.parse("2") === 2)
    assert(TwentyOne.parse("3") === 3)
    assert(TwentyOne.parse("4") === 4)
    assert(TwentyOne.parse("5") === 5)
    assert(TwentyOne.parse("6") === 6)
    assert(TwentyOne.parse("7") === 7)
    assert(TwentyOne.parse("8") === 8)
    assert(TwentyOne.parse("9") === 9)
    assert(TwentyOne.parse("10") === 10)
    assert(TwentyOne.parse(TwentyOne.Jack) === 10)
    assert(TwentyOne.parse(TwentyOne.Queen) === 10)
    assert(TwentyOne.parse(TwentyOne.King) === 10)
    assert(TwentyOne.parse(TwentyOne.Ace) === 11)
  }

  "parse" should "parses a invalid string to zero" in {
    assert(TwentyOne.parse("ABC") === 0)
    assert(TwentyOne.parse("") === 0)
    assert(TwentyOne.parse("0") === 0)
    assert(TwentyOne.parse("-1") === 0)
    assert(TwentyOne.parse("Jack") === 0)
    assert(TwentyOne.parse("true") === 0)
    assert(TwentyOne.parse("11") === 0)
  }

  "parseAll" should "parses multiple strings to their internal game values" in {
    // Arrange
    val cards: Array[String] = Array("2", "10", TwentyOne.Jack, TwentyOne.Ace)
    val containInvalidCards: Array[String] = Array("2", "", "ABC", TwentyOne.Ace)
    val onlyInvalidCards: Array[String] = Array("-1", "", "ABC", "0")
    val noCards: Array[String] = Array()

    // Act & Assert
    assert(TwentyOne.parseAll(cards) === Array(2, 10, 10, 11))
    assert(TwentyOne.parseAll(containInvalidCards) === Array(2, 0, 0, 11))
    assert(TwentyOne.parseAll(onlyInvalidCards) === Array(0, 0, 0, 0))
    assert(TwentyOne.parseAll(noCards) === Array())
  }

  "parseAll" should "returns possible values" in {
    // Arrange
    val aceCardValue = 11
    val numberCardValue = 2
    val jackCardValue = 10
    val outOfRangeCardValue = 1

    // Act & Assert
    assert(TwentyOne.values(aceCardValue) === Array(1, 11))
    assert(TwentyOne.values(numberCardValue) === Array(2))
    assert(TwentyOne.values(jackCardValue) === Array(10))
    assert(TwentyOne.values(outOfRangeCardValue) === Array())
  }

  "isBust" should "busts the correct value" in {
    // Arrange
    val inRangeValue = 10
    val hitValue = 21
    val outOfRangeValue = 22

    // Act & Assert
    assert(TwentyOne.isBust(inRangeValue) === false)
    assert(TwentyOne.isBust(hitValue) === false)
    assert(TwentyOne.isBust(outOfRangeValue) === true)
  }

  "optimisticF" should "finds the max handle value" in {
    // Arrange
    val hand: Array[Int] = Array(2, 5, 10, 11)
    assert(TwentyOne.optimisticF(hand) === 28)
  }

  "pessimisticF" should "finds the min handle value" in {
    // Arrange
    val hand: Array[Int] = Array(2, 5, 10, 11)
    assert(TwentyOne.pessimisticF(hand) === 18)
  }
}
