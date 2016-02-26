package persistence.users

import javax.inject.Inject

import com.google.inject.ImplementedBy
import models.users.UserStatus
import models.users.db.UserStatusTableComponent
import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfigProvider}
import slick.driver.JdbcProfile

import scala.concurrent.Future

@ImplementedBy(classOf[DefaultUserStatusRepository])
trait UserStatusRepository {
  def list(): Future[Seq[UserStatus]]
}

class DefaultUserStatusRepository @Inject()(protected val dbConfigProvider: DatabaseConfigProvider) extends HasDatabaseConfigProvider[JdbcProfile] with
UserStatusRepository with UserStatusTableComponent {

  import driver.api._

  override def list(): Future[Seq[UserStatus]] = {
    val query = userStatus.result
    db.run(query)
  }
}
