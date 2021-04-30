package exercise1

import org.scalatest.flatspec.AnyFlatSpec


class ArrayUtilsSpec extends AnyFlatSpec {

  val arr1: Array[Int] = Array(1, 2, 3, 4)
  val arr2: Array[Int] = Array(-10, -20, 10, 0)
  val arr3: Array[Int] = Array()

  "sum" should "return the sum" in {
    assert(ArrayUtils.sum(arr1) === 10)
    assert(ArrayUtils.sum(arr2) === -20)
    assert(ArrayUtils.sum(arr3) === 0)
  }

  "max" should "return the max" in {
    assert(ArrayUtils.max(arr1) === 4)
    assert(ArrayUtils.max(arr2) === 10)
    assertThrows[UnsupportedOperationException] {
      ArrayUtils.max(arr3)
    }
  }

  "min" should "return the min" in {
    assert(ArrayUtils.min(arr1) === 1)
    assert(ArrayUtils.min(arr2) === -20)
    assertThrows[UnsupportedOperationException] {
      ArrayUtils.min(arr3)
    }
  }
}
