import org.scalatest.flatspec.AnyFlatSpec

class VingtEtUnSpec extends AnyFlatSpec {

  "VingtEtUn" should "throw an invalid args error if a card is invalid" in {
    assertThrows[IllegalArgumentException](VingtEtUn.parse("0"))
  }

  "VingtEtUn" should "parse an ace" in {
    assertResult(11)(VingtEtUn.parse("A"))
  }

  "VingtEtUn" should "parse a jack" in {
    assertResult(10)(VingtEtUn.parse("J"))
  }

  "VingtEtUn" should "parse a king" in {
    assertResult(10)(VingtEtUn.parse("K"))
  }

  "VingtEtUn" should "parse a queen" in {
    assertResult(10)(VingtEtUn.parse("Q"))
  }

  "VingtEtUn" should "parse a number in range 2-10" in {
    assertResult(10)(VingtEtUn.parse("10"))
  }

  "VingtEtUn" should "correctly parse a set of cards" in {
    val cards = Array("2", "A", "J", "Q", "K", "6")
    assertResult(Array(2, 11, 10, 10, 10, 6))(VingtEtUn.parseAll(cards))
  }

  "VingtEtUn" should "throw an error if a card in a set is invalid" in {
    val cards = Array("2", "A", "J", "Q", "K", "0")
    assertThrows[IllegalArgumentException](VingtEtUn.parseAll(cards))
  }

  "VingtEtUn" should "produce a correct set of possible values for an Ace" in {
    assertResult(Array(1, 11))(VingtEtUn.values(11))
  }

  "VingtEtUn" should "produce a correct set of possible values for value in range 2-10" in {
    assertResult(Array(5))(VingtEtUn.values(5))
  }

  "VingtEtUn" should "throw an error if the card is invalid" in {
    assertThrows[IllegalArgumentException](VingtEtUn.values(0))
  }

  "VingtEtUn" should "optimistically determine a hand" in {
    // The 1 and 11 are mapped both to 11, therefore we get 2+4+7+2*11 = 35
    assertResult(35)(VingtEtUn.optimisticF(Array(2, 4, 7, 11, 1)))
  }

  "VingtEtUn" should "pessimistically determine a hand" in {
    // Both the 1 and 11 are mapped to 1, so we get 2+4+7+2 = 15
    assertResult(15)(VingtEtUn.pessimisticF(Array(2, 4, 7, 11, 1)))
  }

  "VingtEtUn" should "determine the best hand as the optimistic one if it doesnt bust" in {
    assertResult(21)(VingtEtUn.determineBestHandValue(Array(4, 7, 10)))
  }

  "VingtEtUn" should "determine the best hand as the pessimistic one if it busts" in {
    // 35 is a bust, so it determines 15 as the best hand
    assertResult(15)(VingtEtUn.determineBestHandValue(Array(2, 4, 7, 11, 1)))
  }

}
