object IntHelper {

  private def filter[T: Ordering](arr: Array[T], f: (T, T) => Boolean): T =
    arr.reduceLeft { (prev, current) => if (f(prev, current)) prev else current }

  def max(arr: Array[Int]): Int = {
    // It would be better if the function could return an Either[E, A] to preserve the error as a type, instead of generating a side effect
    if (arr.isEmpty) throw new IllegalArgumentException("Array cannot be empty")
    else filter(arr, (a: Int, b: Int) => a > b)
  }

  def min(arr: Array[Int]): Int =
    if (arr.isEmpty) throw new IllegalArgumentException("Array cannot be empty")
    else filter(arr, (a: Int, b: Int) => a < b)

  def sum(arr: Array[Int]): Int = {
    if (arr.isEmpty) 0
    else arr.reduceLeft((a, b) => a + b)
  }


}
