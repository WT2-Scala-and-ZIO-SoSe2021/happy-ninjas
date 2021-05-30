package exercise1

/**
 * Twenty-One, the aim is to come as close to 21 as possible.
 */
object TwentyOne {

  val Jack = "J"
  val Queen = "Q"
  val King = "K"
  val Ace = "A"

  /**
   * Takes a card and returns its associated integer.
   *
   * @param card card
   * @return associated integer
   */
  def parse(card: String): Int = {
    val valueAsInt = toInt(card)
    if (valueAsInt.isDefined && valueAsInt.get >= 2 && valueAsInt.get <= 10) {
      valueAsInt.get
    } else {
      card match {
        case Jack | Queen | King => 10
        case Ace => 11
        case _ => 0
      }
    }
  }

  /**
   * Takes multiple cards and returns their associated integers.
   *
   * @param cards cards
   * @return associated integers
   */
  def parseAll(cards: Array[String]): Array[Int] = {
    cards map parse
  }

  /**
   * Takes a card and returns its possible values.
   * Ace returns 2 values.
   *
   * @param card card
   * @return values
   */
  def values(card: Int): Array[Int] = {
    card match {
      case 11 => Array(1, 11)
      case value if value >= 2 && value <= 10 => Array(value)
      case _ => Array[Int]()
    }
  }

  /**
   * Determines the hand value by given strategy function and hand.
   *
   * @param strategy strategy
   * @param hand     hand
   * @return hand value
   */
  def determineHandValue(strategy: Array[Int] => Int)(hand: Array[Int]): Int = ArrayUtils.sum(hand.map(values).map(strategy))

  /**
   * Returns true if value is greater than 21
   */
  def isBust(hand: Int): Boolean = hand > 21

  /**
   * Applies max as the strategy
   */
  def optimisticF(hand: Array[Int]): Int = determineHandValue(ArrayUtils.max)(hand)

  /**
   * Applies min as the strategy
   */
  def pessimisticF(hand: Array[Int]): Int = determineHandValue(ArrayUtils.min)(hand)

  /**
   * Computes its hand values in a order optimisticF and then pessimisticF.
   * Skips optimisticF if its value is in a bust.
   */
  def determineBestHandValue(hand: Array[Int]): Int = {
    val optimisticValue = optimisticF(hand)
    if (isBust(optimisticValue)) {
      pessimisticF(hand)
    } else {
      optimisticValue
    }
  }

  /**
   * Convert a string to its integer.
   *
   * @param s string
   * @return integer, none if exception is raised
   */
  def toInt(s: String): Option[Int] = {
    try {
      Some(s.toInt)
    } catch {
      case e: Exception => None
    }
  }
}
