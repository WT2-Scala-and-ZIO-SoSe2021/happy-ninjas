package exercise4.AutoZion

import zio._

class AutoZionNews(queue: Queue[String]) extends News.Service {

  override def post(news: String): UIO[Unit] = for { _ <- queue.offer(news) } yield ()

  override def proclaim(): UIO[String] = for { news <- queue.take } yield news
}
