package exercise4.AutoZion

import zio._

class AutoZionHub(hub: Hub[CompletedJob]) extends CompletedJobHub.Service {

  override def subscribe: ZManaged[Any, Nothing, Dequeue[CompletedJob]] = for { sub <- hub.subscribe } yield sub

  override def publish(job: CompletedJob): UIO[Unit] = for { _ <- hub.publish(job)} yield ()

}
