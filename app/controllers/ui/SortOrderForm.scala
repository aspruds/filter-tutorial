package controllers.ui

import models.ui.SortOrder
import play.api.data.Form
import play.api.data.Forms._

trait SortOrderForm {

  val sortOrderForm = Form {
    mapping(
      "sortBy" -> text,
      "desc" -> boolean
    )(SortOrder.apply)(SortOrder.unapply)
  }

}
