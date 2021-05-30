package exercise1

/**
 * ArrayUtils contains array utils
 */
object ArrayUtils {
  /**
   * Highest value of integers
   *
   * @param arr array of integers
   * @return Highest value of integers
   */
  def max(arr: Array[Int]): Int = arr.reduce(_ max _)

  /**
   * Lowest value of integers
   *
   * @param arr array of integers
   * @return Lowest value of integers
   */
  def min(arr: Array[Int]): Int = arr.reduce(_ min _)

  /**
   * Sum of integers
   *
   * @param arr array of integers
   * @return sum of integers, 0 if no integer is given
   */
  def sum(arr: Array[Int]): Int = arr.fold(0)(_ + _)
}
