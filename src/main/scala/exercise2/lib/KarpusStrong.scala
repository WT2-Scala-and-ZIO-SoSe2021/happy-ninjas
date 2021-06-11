package exercise2.lib

import scala.collection.immutable.Queue
import scala.util.Random

object KarpusStrong {

  val EnergyDecayFactor = 0.996
  val random = new Random()

  def main(args: Array[String]): Unit = {
    loop(StdAudio.play)(whiteNoise())
  }

  /**
   * Returns a queue containing a total of frequency elements of random values
   * between .5 and -.5 multiplied by volume.
   */
  def whiteNoise(frequency: Int = 440, volume: Double = 1.0): Queue[Double] = {
    validateValues(frequency, volume)
    val frequencies = (1 to frequency)
    frequencies.map(_ => random.between(-.5, .5) * volume).to(Queue)
  }

  def validateValues(frequency: Int = 440, volume: Double = 1.0) {
    if(frequency <= 0) {
      throw new Exception("Frequency must be greater then zero")
    } else if (volume < 0 || volume > 1) {
      throw new Exception("Volume must be between 0 and 1")
    }
  }

  /**
   * Remove the front double in the queue and average it with the next double
   * multiplied by an energy decay factor of 0.996.
   * Then, add newDouble to the queue.
   */
  def update(queue: Queue[Double]): Queue[Double] = {
    val (first, dequeuedQueue) = queue.dequeue
    val second = dequeuedQueue.front
    val newDouble = processNewDouble(first, second)
    dequeuedQueue.appended(newDouble)
  }

  private def processNewDouble(first: Double, second: Double): Double = {
    ((first + second) / 2) * EnergyDecayFactor
  }

  /**
   * Takes a queue and a function, which can be used to play audio.
   * It takes the queue, passes it to update, plays the front element of the queue and calls itself indefinitely.
   */
  def loop(playFunc: Double => Unit)(queue: Queue[Double]): Unit = {
    import scala.annotation.tailrec
    @tailrec
    def tailedLoop(queue: Queue[Double]): Unit = {
      val updatedQueue = update(queue)
      val playDouble = updatedQueue.head
      playFunc(playDouble)
      tailedLoop(updatedQueue)
    }
    tailedLoop(queue)
  }
}
