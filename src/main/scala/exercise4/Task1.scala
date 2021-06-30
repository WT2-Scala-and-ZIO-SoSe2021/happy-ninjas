package exercise4

import zio._


object Task1 extends zio.App {

  trait Config
  trait Logging
  trait Parsing
  trait Database
  trait Serialization
  trait UserService

  val configLive: ULayer[Has[Config]] = ???
  val userServiceLive: URLayer[Has[Database] with Has[Logging] with Has[Serialization], Has[UserService]] = ???
  val parsingLive: ULayer[Has[Parsing]] = ???
  val serializationLive: URLayer[Has[Parsing], Has[Serialization]] = ???
  val databaseLive: URLayer[Has[Config], Has[Database]] = ???
  val loggingLive: ULayer[Has[Logging]] = ???

  type MyEnv = Has[Database] with Has[Logging] with Has[Serialization] with Has[UserService] with Has[Parsing] with Has[Config]

  def f(): URIO[MyEnv, Unit] = ???

  /**
   * Inject the whole environment as a ZLayer
   * a >+> b Passing through dependencies
   * a ++ b Horizontal Composition
   */
  override def run(args: List[String]): URIO[zio.ZEnv, ExitCode] = f().provideCustomLayer(
      configLive >+> databaseLive ++ loggingLive ++ parsingLive >+> serializationLive >+> userServiceLive
    ).exitCode
}
