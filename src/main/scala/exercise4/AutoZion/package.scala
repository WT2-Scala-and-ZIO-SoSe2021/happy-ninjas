package exercise4

import zio._
import zio.duration.Duration

package object AutoZion {

  // TODO: required custom ZIO environment
  type MyEnv = Has[JobBoard]

  trait Robot {
    val name: String

    def work(): ZIO[MyEnv, Any, Unit]
  }

  sealed trait Job

  case class PendingJob(duration: Duration) extends Job

  case class CompletedJob(completedBy: Robot) extends Job

  trait JobBoard {
    /**
     * Submits a job to the job board, which can later be taken up by a robot using the take method.
     */
    def submit(job: PendingJob): UIO[Unit]

    /**
     * Take a job from the job board
     */
    def take(): UIO[PendingJob]
  }

  object JobBoard {
    def submit(job: PendingJob): URIO[Has[JobBoard], Unit] = ZIO.serviceWith[JobBoard](_.submit(job))

    def take(): URIO[Has[JobBoard], PendingJob] = ZIO.serviceWith[JobBoard](_.take())
  }

  case class JobBoardLive() extends JobBoard {
    val unboundedQueue: UIO[Queue[PendingJob]] = Queue.unbounded[PendingJob]

    override def submit(job: PendingJob): UIO[Unit] = unboundedQueue.map(value => value.offer(job))

    override def take(): UIO[PendingJob] = for {
      queue <- unboundedQueue
      fib_job <- queue.poll
      job = fib_job.get
    } yield job
  }

  object JobBoardLive {
    val layer: ULayer[Has[JobBoard]] = ZLayer.succeed(JobBoardLive())
  }

}
