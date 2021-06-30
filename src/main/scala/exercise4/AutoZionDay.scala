package exercise4

import zio._
import AutoZion._
import zio.duration._
import zio.clock._

object AutoZionDay extends zio.App {

  val env: ULayer[ZionEnv] = JobBoard.live ++ CompletedJobHub.live ++ News.live

  val daySchedule: Schedule[Any, Any, Duration] = (Schedule.forever >>> Schedule.elapsed).whileOutput(_ < 24.hours)

  def zionDay: ZIO[ZionEnv with Clock, Any, Unit] = for {
    a <- ElderRobot("1").work.fork.delay(500.milliseconds)
    b <- ElderRobot("2").work.fork.delay(500.milliseconds)
    e <- PraiserRobot("1").work.forever.fork
    f <- OverseerRobot("1").work.forever.fork
    c <- WorkerRobot("1").work.forever.fork
    d <- WorkerRobot("2").work.forever.fork
    _ <- ReporterRobot("1").work.repeat(daySchedule)
  } yield ()

  override def run(args: List[String]): URIO[zio.ZEnv, ExitCode] = zionDay.provideLayer(env ++ Clock.live).exitCode

}
