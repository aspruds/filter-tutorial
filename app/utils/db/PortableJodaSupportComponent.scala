package utils.db

import com.github.tototoshi.slick.GenericJodaSupport
import play.api.db.slick.HasDatabaseConfig
import slick.driver.{JdbcDriver, JdbcProfile}

trait PortableJodaSupportComponent {
  this: HasDatabaseConfig[JdbcProfile] =>

  val jdbcDriver = driver.asInstanceOf[JdbcDriver]

  object PortableJodaSupport extends GenericJodaSupport(jdbcDriver)
}