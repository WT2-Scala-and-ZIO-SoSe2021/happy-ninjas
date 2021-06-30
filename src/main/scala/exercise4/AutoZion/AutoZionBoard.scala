package exercise4.AutoZion

import zio._

class AutoZionBoard(queue: Queue[PendingJob]) extends JobBoard.Service {

  override def submit(job: PendingJob): UIO[Unit] = for { _ <- queue.offer(job) } yield ()

  override def take(): UIO[PendingJob] = for { job <- queue.take } yield job

}
