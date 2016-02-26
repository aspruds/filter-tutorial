package controllers

import javax.inject.Inject

import controllers.ui.{FilterForm, SortOrderForm}
import models.ui.{Pager, SortOrder}
import models.users.forms.Filter
import org.joda.time.DateTime
import persistence.users.{UserRepository, UserStatusRepository}
import play.api.i18n.{MessagesApi, I18nSupport}
import play.api.mvc._
import views.html.screens.users.{list => listView}

import scala.concurrent.ExecutionContext.Implicits.global

class Application @Inject()(val messagesApi: MessagesApi,
                            userRepository: UserRepository,
                            userStatusRepository: UserStatusRepository) extends Controller
  with FilterForm with SortOrderForm with I18nSupport {

  val DefaultSortOrder = SortOrder("surname", false)

  /**
    * Initialize default filter (users created since yesterday)
    */
  def index = Action.async {

    val yesterday = DateTime.now.minusDays(1).withTimeAtStartOfDay

    val currentPage = 1

    val filter = Filter(
      name = None,
      surname = None,
      statusIds = None,
      dateCreatedFrom = Some(yesterday),
      dateCreatedUntil = None)

    val form = filterForm.fill(filter)

    filterReports(currentPage, filter, DefaultSortOrder)
  }

  def list(currentPage: Int = 1) = Action.async { implicit request =>
    val sortOrder = sortOrderForm.bindFromRequest().value.getOrElse(DefaultSortOrder)

    filterForm.bindFromRequest.fold(
      formWithErrors => index(request),
      filter => filterReports(currentPage, filter, sortOrder)
    )
  }

  private def filterReports(
                             currentPage: Int,
                             filter: Filter,
                             sortOrder: SortOrder) = {
    for {
      userStatusTypes <- userStatusRepository.list()
      count <- userRepository.count(filter)
      pager = Pager(count, currentPage)
      users <- userRepository.list(pager, filter, sortOrder)
    } yield {
      val filledFilterForm = filterForm.fill(filter)
      val filledSortOrder = sortOrderForm.fill(sortOrder)
      Ok(listView(users, userStatusTypes, pager, filledFilterForm, filledSortOrder))
    }
  }

}
