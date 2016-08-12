package utils.db

import play.api.db.slick.HasDatabaseConfig
import slick.driver.JdbcProfile

trait PortableJodaSupport {
  this: HasDatabaseConfig[JdbcProfile] =>

  object JodaSupport extends com.github.tototoshi.slick.GenericJodaSupport(driver)
}