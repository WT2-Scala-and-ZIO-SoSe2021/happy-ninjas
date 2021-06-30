package exercise4

import zio._
import zio.duration.Duration


package object AutoZion {
  type JobBoard = Has[JobBoard.Service]
  type JobHub = Has[CompletedJobHub.Service]
  type News = Has[News.Service]
  type ZionEnv = JobBoard with JobHub with News

  trait Robot {
    def getName: String

    def work: ZIO[ZionEnv, Any, Unit]
  }

  sealed trait Job

  case class PendingJob(duration: Duration) extends Job

  case class CompletedJob(worker: Robot) extends Job

  object JobBoard {
    trait Service {

      def submit(job: PendingJob): UIO[Unit]

      def take(): UIO[PendingJob]

    }

    val live: ULayer[JobBoard] = ZLayer.fromEffect {
      for {
        queue <- Queue.unbounded[PendingJob]
      } yield new AutoZionBoard(queue)
    }

  }

  object CompletedJobHub {
    trait Service {

      def subscribe: ZManaged[Any, Nothing, Dequeue[CompletedJob]]

      def publish(job: CompletedJob): UIO[Unit]

    }

    val live: ULayer[JobHub] = ZLayer.fromEffect {
      for {
        hub <- Hub.unbounded[CompletedJob]
      } yield new AutoZionHub(hub)
    }
  }

  object News {
    trait Service {
      def post(news: String): UIO[Unit]

      def proclaim(): UIO[String]
    }

    val live: ULayer[News] = ZLayer.fromEffect {
      for {
        queue <- Queue.unbounded[String]
      } yield new AutoZionNews(queue)
    }
  }

}
