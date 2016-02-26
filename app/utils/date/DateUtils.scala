package utils.date

import org.joda.time.format.DateTimeFormat
import org.joda.time.{DateTime, DateTimeZone}

object DateUtils {
  implicit class DateTimeFormatterUtils(dateTime: DateTime) {
    def format(format: String) = {
      val formatter = DateTimeFormat.forPattern(format)
      formatter.print(dateTime).toUpperCase
    }

    def toUtc = dateTime.withZone(DateTimeZone.forID("UTC"))
  }
}
