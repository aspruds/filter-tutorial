package persistence.users

import javax.inject.Inject

import com.google.inject.ImplementedBy
import models.users.forms.Filter
import models.ui.{SortOrder,Pager}
import models.users.User
import models.users.db.UsersTableComponent
import play.api.db.slick.{HasDatabaseConfigProvider, DatabaseConfigProvider}
import slick.driver.JdbcProfile
import slick.lifted.ColumnOrdered
import utils.db.{PortableJodaSupport, MaybeFilterSupport}

import scala.concurrent.Future

@ImplementedBy(classOf[DefaultUserRepository])
trait UserRepository {
  def count(filter: Filter): Future[Int]

  def list(pager: Pager, filter: Filter, sortOrder: SortOrder): Future[Seq[User]]
}

class DefaultUserRepository @Inject()(protected val dbConfigProvider: DatabaseConfigProvider)
  extends HasDatabaseConfigProvider[JdbcProfile] with UserRepository with UsersTableComponent with MaybeFilterSupport with PortableJodaSupport {

  import driver.api._
  import JodaSupport._

  def list(): Future[Seq[User]] = {
    val query = users.result
    db.run(query)
  }

  def filteredUsers(f: Filter) = {
    val filter = f.normalizeDates()

    MaybeFilter(users)
      .filter(filter.name)(v => d => d.name === v)
      .filter(filter.surname)(v => d => d.surname === v)
      .filter(filter.statusIds)(v => d => d.statusId inSetBind v)
      .filter(filter.dateCreatedFrom)(v => d => d.created >= v)
      .filter(filter.dateCreatedUntil)(v => d => d.created <= v)
      .query
  }

  private def ordering(order: SortOrder): UsersTable => ColumnOrdered[_] = { r =>
    implicit class OrderUtils[T](rep: Rep[T]) {
      def ordered(implicit ev: Rep[T] => ColumnOrdered[T]) = {
        if(order.desc) rep.desc else rep.asc
      }
    }

    order.code match {
      case "id" => r.id.ordered
      case "name" => r.name.ordered
      case "surname" => r.surname.ordered
      case "dateCreated" => r.created.ordered
      case _ => throw new Exception(s"unknown sortOrder: $order")
    }
  }

  override def list(pager: Pager, filter: Filter, sortOrder: SortOrder): Future[Seq[User]] = {
    val offset = (pager.currentPage - 1) * pager.pageSize

    val query = filteredUsers(filter)
      .sortBy(ordering(sortOrder))
      .drop(offset)
      .take(pager.pageSize)
      .result

    db.run(query)
  }

  override def count(filter: Filter): Future[Int] = {
    val query = filteredUsers(filter).size.result
    db.run(query)
  }
}
