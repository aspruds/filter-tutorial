package models.users.db

import models.users.User
import org.joda.time.DateTime
import play.api.db.slick.HasDatabaseConfig
import slick.driver.JdbcProfile
import utils.db.PortableJodaSupportComponent

trait UsersTableComponent extends PortableJodaSupportComponent {
  this: HasDatabaseConfig[JdbcProfile] =>

  import driver.api._
  import PortableJodaSupport._

  val users = TableQuery[UsersTable]

  val usersAutoInc = {
    users returning users.map(_.id) into ((user, id) => user.copy(id = id))
  }

  class UsersTable(tag: Tag) extends Table[User](tag, "users") {

    def id = column[Long]("id", O.AutoInc, O.PrimaryKey)

    def name = column[String]("name")

    def surname = column[String]("surname")

    def statusId = column[Long]("status_id")

    def created = column[DateTime]("date_created")

    def deleted = column[Option[DateTime]]("date_deleted")

    def * = (
      id,
      name,
      surname,
      statusId,
      created,
      deleted) <> (User.tupled, User.unapply)
  }
}

