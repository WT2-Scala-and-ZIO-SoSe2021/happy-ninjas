package exercise4.AutoZion

import zio.{ZIO}
import zio.console.Console

import java.io.IOException

case class ReporterRobot(name: String) extends Robot {
  override def getName: String = s"Reporter-$name"

  private def writeMessage(message: String): ZIO[Console, IOException, Unit] = for {
    console <- ZIO.environment[Console]
    _ <- console.get.putStrLn(message)
  } yield ()

  override def work: ZIO[ZionEnv, Any, Unit] = for {
    service <- ZIO.environment[News]
    message <- service.get.proclaim()
    _ <- writeMessage(message).provideLayer(Console.live)
  } yield ()

}
