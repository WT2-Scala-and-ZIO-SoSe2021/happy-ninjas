package exercise4

import zio._
import zio.duration.Duration

package object AutoZion {

  // TODO: required custom ZIO environment
  type MyEnv = ???

  trait Robot {
    val name: String

    def work(): ZIO[MyEnv, Any, Unit]
  }

  sealed trait Job

  case class PendingJob(duration: Duration) extends Job

  case class CompletedJob(completedBy: Robot) extends Job

}
