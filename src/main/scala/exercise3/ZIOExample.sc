import zio.blocking.effectBlocking

import scala.util.Try
import scala.concurrent.Future
import zio._

import java.io.IOException
import scala.io.StdIn

/*
ZIO[R, E, A]
R - Required Environment Type
E - Failure Type
A - Success Type
*/

/*
Task[A] - fail[Throwable] or succeed[A], no requirements
 */


/*
CREATE EFFECTS
 */


// from success values
val s1 = ZIO.succeed(42)
val s2: Task[Int] = Task.succeed(43)
val now = ZIO.effectTotal(System.currentTimeMillis()) // value has side effects

// from failure values
val f1 = ZIO.fail("Uh oh!") // ZIO no restriction
val f2 = Task.fail(new Exception("Uh oh!"))

// from scala values

// option
val zoption:IO[Option[Nothing], Int] = ZIO.fromOption(Some(2))
val zoption2: IO[String, Int] = zoption.mapError(_ => "It wasn't there!")
// either
val zeither = ZIO.fromEither(Right("Success!"))
// try
val ztry = ZIO.fromTry(Try(42 / 0))
// function
val zfun: URIO[Int, Int] = ZIO.fromFunction((i: Int) => i * i)
// future
lazy val future = Future.successful("Hello!")
val zfuture: Task[String] = ZIO.fromFuture { implicit ec => future.map(_ => "Goodbye!")}

// from side effects

// synchronous side-effects

// The error type of the resulting effect will always be Throwable
val getStrLn: Task[String] = ZIO.effect(StdIn.readLine())
// side-effect is known to not throw any exceptions, then the side-effect can be converted into a ZIO effect using ZIO.effectTotal
def putStrLn(line: String): UIO[Unit] = ZIO.effectTotal(println(line))
// refine the error type of an effect
val getStrLn2: IO[IOException, String] = ZIO.effect(StdIn.readLine()).refineToOrDie[IOException]

// asynchronous side-effects

// asynchronous side-effect with a callback-based API can be converted into a ZIO effect
/*
IO.effectAsync[AuthError, User] { callback =>
    legacy.login(
      user => callback(IO.succeed(user)),
      err  => callback(IO.fail(err))
    )
  }
 */

// blocking synchronous side-effects
val sleeping = effectBlocking(Thread.sleep(Long.MaxValue))


/*
BASIC OPERATIONS
 */

// mapping

// map over the success channel of an effect
val succeeded: UIO[Int] = IO.succeed(21).map(_ * 2)
// map over the error channel
val failed: IO[Exception, Unit] = IO.fail("No no!").mapError(msg => new Exception(msg))

// chaining

// first effect fails, the callback passed to flatMap will never be invoked
val sequenced = getStrLn.flatMap(input => putStrLn(s"You entered: $input"))

// for comprehensions

// use Scala's for comprehensions to build sequential effects:
val program =
  for {
    _    <- putStrLn("Hello! What is your name?")
    name <- getStrLn
    _    <- putStrLn(s"Hello, ${name}, welcome to ZIO!")
  } yield ()

// zipping

// combine two effects into a single effect, result tuple of both
// left is executed before right
// if any zip operation fails the composed effect fails
val zipped: UIO[(String, Int)] = ZIO.succeed("4").zip(ZIO.succeed(2))



