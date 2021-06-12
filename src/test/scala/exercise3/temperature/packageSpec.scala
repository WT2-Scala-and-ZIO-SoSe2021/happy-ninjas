package exercise3.temperature

import org.scalatest.flatspec.AnyFlatSpec


class packageSpec extends AnyFlatSpec {

  private def checkTemperature(actual: Temperature, expected: Temperature): Unit = {
    assert("%.2f".format(actual) === "%.2f".format(expected))
  }

  "Temperature" should "convert a given degree in the specified system" in {
    // Arrange
    val temperature: Temperature = 273.15

    // Act && Assert
    checkTemperature(temperature.celsius(), 273.15)
    checkTemperature(temperature.kelvin(), 0)
    checkTemperature(temperature.fahrenheit(), 133.97)
  }

  "Temperature" should "return the average of two temperatures" in {
    // Arrange
    val temperature: Temperature = 1
    val other: Temperature = 10

    // Act && Assert
    checkTemperature(temperature.celsius().avg(other.fahrenheit()), -5.61)
  }

  "Temperature" should "display itself" in {
    // Arrange
    val temperature: Temperature = 273.15

    // Act && Assert
    assert(display(temperature) === "273.15 °C")
  }
}
