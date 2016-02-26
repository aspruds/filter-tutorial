package utils.db

import play.api.db.slick.HasDatabaseConfig
import slick.driver.JdbcProfile
import slick.lifted.CanBeQueryCondition

trait MaybeFilterSupport {
  this: HasDatabaseConfig[JdbcProfile] =>

  import driver.api._

  /*
  http://stackoverflow.com/questions/22036534/better-slick-dynamic-query-coding-style
 */
  case class MaybeFilter[T, U](query: Query[T, U, Seq]) {
    def filter[C, R: CanBeQueryCondition](data: Option[C])(condition: C => T => R) = {
      data.map {
        value => MaybeFilter(query.withFilter(condition(value)))
      }.getOrElse(this)
    }
  }

}