package exercise3

import exercise3.temperature._

object TemperatureRunner extends App {
  implicit val locale: Locale.Locale = Locale.SCI
  val value: Temperature = 0.0
  val other = 0.0.kelvin()

  // HINT: 0.0 °K
  print(display(value))

  // HINT: -273.15 °K
  print(display(other))
}
