object VingtEtUn {

  def parse(card: String): Int = card match {
    case "J" | "Q" | "K" => 10
    case "A" => 11
    case c => c.toIntOption match {
      case Some(intVal) if intVal >= 2 && intVal < 11 => intVal
      case _ => throw new IllegalArgumentException(s"Invalid value $card")
    }
  }

  def parseAll(cards: Array[String]): Array[Int] = cards.map(parse)

  def values(card: Int): Array[Int] = card match {
    case 11 | 1 => Array(1, 11)
    case c if c >= 2 && c < 11 => Array(c)
    case _ => throw new IllegalArgumentException(s"Invalid value $card")
  }

  def determineHandValue(strategy: Array[Int] => Int, hand: Array[Int]): Int = IntHelper.sum(hand.map(values).map(strategy))

  def isBust(hand: Int): Boolean = hand > 21

  def optimisticF(hand: Array[Int]): Int = determineHandValue(IntHelper.max, hand)

  def pessimisticF(hand: Array[Int]): Int = determineHandValue(IntHelper.min, hand)

  def determineBestHandValue(hand: Array[Int]): Int = {
    val optimisticResult = optimisticF(hand)
    if (isBust(optimisticResult)) pessimisticF(hand)
    else optimisticResult
  }
}
