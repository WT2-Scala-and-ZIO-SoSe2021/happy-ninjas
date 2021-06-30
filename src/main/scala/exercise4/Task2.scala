package exercise4

import zio._


object Task2 extends zio.App {

  /**
   * zipPair - executes two ZIOs in parallel. If one fails, the other is interrupted.
   * fork - executes the effect in a new fiber and attach it to the parent fibers scope
   * join - wait for the fiber to computs its value and return the value
   */
  val program: ZIO[Any, String, Unit] = {
    for {
      fib <- (ZIO.interrupt zipPar ZIO.fail("foo")).fork
      _ <- fib.join
    } yield ()
  }

  override def run(args: List[String]): URIO[zio.ZEnv, ExitCode] = program.exitCode
}