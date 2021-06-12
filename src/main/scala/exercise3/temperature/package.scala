package exercise3

package object temperature {
  type Temperature = Double

  implicit class TemperatureClass(value: Temperature) {
    val freezingPoint: Temperature = 0.0
    val absoluteZero: Temperature = -273.15
    def celsius(): Temperature = value
    def kelvin(): Temperature = value - 273.15
    def fahrenheit(): Temperature = (value - 32) * 5/9
    def avg(other: Temperature): Temperature = {
      val values = List[Temperature](value, other)
      values.sum / values.length
    }
  }

  object Locale extends Enumeration {
    type Locale = String
    val US = "US"
    val SCI = "SCI"
    val Other = "Other"
  }

  implicit val locale: Locale.Locale = Locale.Other

  def display(value: Temperature)(implicit locale: Locale.Locale): String = locale match {
    case Locale.US => s"${value} °F"
    case Locale.SCI => s"${value} °K"
    case Locale.Other => s"${value} °C"
    case _ => throw new NotImplementedError(s"${value} not implemented.")
  }
}
