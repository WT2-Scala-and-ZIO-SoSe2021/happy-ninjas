package exercise3

import scala.util.Success
import scala.util.Failure
import zio.console.Console
import zio.{ExitCode, Task, URIO, ZIO}
import zio.random._
import exercise2.{Queue, QueueLike, StackEmpty}
import exercise2.lib.StdAudio

object ZIOSynthesizer extends zio.App {

  val ENERGY_DECAY_FACTOR = 0.996

  def run(args: List[String]): URIO[Random with Console, ExitCode] =
    app.exitCode

  val app: ZIO[Random, Throwable, Unit] =
    for {
      sound <- whiteNoise()
      _ <- loop(sound)
    } yield ()

  // use effect instead of effectTotal, because play as given some side effects
  def play(sound: Double): Task[Unit] = ZIO.effect(StdAudio.play(sound))


  def whiteNoise(frequency: Int = 440, volume: Double = 1.0): ZIO[Random, Throwable, Queue[Double]] = {
    if (frequency <= 0) {
      ZIO.fail("Frequency must be greater then zero")
    } else if (volume < 0 || volume > 1) {
      ZIO.fail("Volume must be between 0 and 1")
    }

    val frequencies = (1 to frequency)
    ZIO.foldLeft(frequencies)(new Queue[Double](StackEmpty(), StackEmpty())) {
      (acc, _) =>
        for {
          nextRandomValue <- nextDoubleBetween(-0.5, 0.5)
          // we dont need effectTotal because value does have side effects
          queue <- ZIO.effect(acc.enqueue(nextRandomValue * volume))
        } yield queue
    }
  }


  def update(queue: QueueLike[Double]): Option[QueueLike[Double]] = {
    val updatedQueue = queue.dequeue()
    updatedQueue match {
      case Success(updatedQueue) => Some(
        updatedQueue.enqueue((queue.front().get + updatedQueue.front().get) / 2.0 * ENERGY_DECAY_FACTOR)
      )
      case Failure(_) => None
    }
  }


  def loop(queue: QueueLike[Double]): ZIO[Random, Throwable, Unit] = {
    val newQueue = update(queue).get
    for {
      _ <- play(newQueue.front().get)
      _ <- loop(newQueue)
    } yield ()
  }
}