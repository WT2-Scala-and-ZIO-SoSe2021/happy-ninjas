package exercise4.AutoZion

import java.util.concurrent.TimeUnit
import zio._
import zio.clock.Clock
import zio.random._
import zio.duration._

case class ElderRobot(name: String) extends Robot {

  override def getName: String = s"Elder-$name"

  private def makeJob: URIO[Random, PendingJob] = for {
    time <- nextIntBetween(1, 4)
    duration = Duration(time, TimeUnit.SECONDS)
  } yield PendingJob(duration)

  override def work: ZIO[ZionEnv, Any, Unit] = for {
    env <- ZIO.environment[JobBoard]
    job <- makeJob.provideLayer(Random.live)
    _ <- env.get.submit(job)
      .repeat(Schedule.spaced(Duration.fromMillis(2000)) && Schedule.forever)
      .provideLayer(Clock.live)
  } yield ()

}
