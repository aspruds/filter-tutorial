package models.users.db

import models.users.UserStatus
import play.api.db.slick.HasDatabaseConfig
import slick.driver.JdbcProfile

trait UserStatusTableComponent {
  this: HasDatabaseConfig[JdbcProfile] =>

  import driver.api._

  val userStatus = TableQuery[UserStatusTable]

  val usersAutoInc = {
    userStatus returning userStatus.map(_.id) into ((status, id) => status.copy(id = id))
  }

  class UserStatusTable(tag: Tag) extends Table[UserStatus](tag, "user_status") {

    def id = column[Long]("id", O.AutoInc, O.PrimaryKey)

    def code = column[String]("code")

    def name = column[String]("name")

    def * = (
      id,
      code,
      name) <> (UserStatus.tupled, UserStatus.unapply)
  }
}

