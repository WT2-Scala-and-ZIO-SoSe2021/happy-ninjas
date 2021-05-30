package exercise2

/**
 * Represents a the inclusive or relationship that is either an `E`, or a `A`, or both an `E` and a `A`.
 */
sealed trait Ior[A] {

  /**
   * Maps the function argument through `Right`.
   * @param f The function to bind across `Right`.
   */
  def map[B](f: A => B): Ior[B] = flatMap(value => Ior.unit(f(value)))

  /**
   * Binds the given function across `Right`.
   * @param f The function to bind across `Right`.
   */
  def flatMap[B](f: A => Ior[B]): Ior[B] = this match {
    case Left(exception) => Ior.left(exception)
    case Right(element) => f(element)
    // nested pattern matching required
    case Both(exception, element) =>
      f(element) match {
        // @ deals with the object itself
        case obj@Left(_) => obj
        case Right(element) => Both(exception, element)
        case obj@Both(_, _) => obj
      }
  }
}


case class Left[A](throwable: Throwable) extends Ior[A]

case class Right[A](element: A) extends Ior[A]

case class Both[A](throwable: Throwable, element: A) extends Ior[A]


/**
 * Companion object for Ior
 */
object Ior {
  def left[A](throwable: Throwable): Left[A] = Left(throwable)

  def right[A](element: A): Right[A] = Right(element)

  def both[A](throwable: Throwable, element: A): Both[A] = Both(throwable, element)

  def unit[A](element: A): Ior[A] = Right(element)
}