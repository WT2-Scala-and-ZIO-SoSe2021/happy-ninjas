package exercise4.AutoZion

import zio._
import zio.clock._

case class WorkerRobot(name: String) extends Robot {

  override def getName: String = s"Worker-$name"

  override def work: ZIO[ZionEnv, Any, Unit] = for {
    services <- ZIO.services[JobBoard.Service, CompletedJobHub.Service]
    (board, hub) = services
    job <- board.take()
    _ <- sleep(job.duration).provideLayer(Clock.live)
    _ <- hub.publish(CompletedJob(this))
  } yield ()

}
