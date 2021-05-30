package exercise2.lib

import org.scalatest.flatspec.AnyFlatSpec


class KarpusStrongSpec extends AnyFlatSpec {

  val exception = new RuntimeException("message")

  "whiteNoise" should " returns a queue containing a total of frequency elements" in {
    // Arrange & Act
    val queue = KarpusStrong.whiteNoise(440, 1)

    // Assert
    assert(queue.length === 440)
    assert(queue.min >= -.5)
    assert(queue.max >= .5)
  }
}
