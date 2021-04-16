import org.scalatest.flatspec.AnyFlatSpec

class IntHelperSpec extends AnyFlatSpec {

  "IntHelper" should "compute the maximum value of an array of integers " in {
    assert(IntHelper.max(Array(1, 2, 3, 4, 5, 6)) == 6)
  }

  "IntHelper" should "raise an invalid args error if the array is empty in the max func" in {
    assertThrows[IllegalArgumentException](IntHelper.max(Array.emptyIntArray))
  }

  "IntHelper" should "raise an invalid args error if the array is empty in the min func" in {
    assertThrows[IllegalArgumentException](IntHelper.min(Array.emptyIntArray))
  }

  "IntHelper" should "compute the minimum value of an array of integers " in {
    assert(IntHelper.min(Array(1, 2, 3, 4, 0, 6)) == 0)
  }

  "IntHelper" should "compute the sum of an array of integers" in {
    assert(IntHelper.sum(Array(1, 2, 3, 4, 5, 6)) == 21)
  }

  "IntHelper" should "return zero if the array is empty" in {
    assert(IntHelper.sum(Array.emptyIntArray) == 0)
  }
}
