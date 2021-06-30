package exercise4.AutoZion

import zio._

case class OverseerRobot(name: String) extends Robot {

  override def getName: String = s"Overseer-$name"

  override def work: ZIO[ZionEnv, Any, Unit] = for {
    services <- ZIO.services[CompletedJobHub.Service, News.Service]
    (hub, news) = services
    job <- hub.subscribe.use(_.take)
    _ <- news.post(s"${job.worker.getName} completed a job")
  } yield ()

}

