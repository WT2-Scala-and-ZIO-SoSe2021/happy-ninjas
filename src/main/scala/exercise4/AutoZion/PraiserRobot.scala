package exercise4.AutoZion

import zio._

case class PraiserRobot(name: String) extends Robot {

  override def getName: String = s"Praiser-$name"

  override def work: ZIO[ZionEnv, Any, Unit] = for {
    services <- ZIO.services[CompletedJobHub.Service, News.Service]
    (hub, news) = services
    job <- hub.subscribe.use(_.take)
    _ <- news.post(s"Well done, ${job.worker.getName}!")
  } yield ()
}