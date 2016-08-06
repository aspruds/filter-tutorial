package controllers.ui

import models.ui.SortOrder
import models.ui.SortField
import play.api.data.Form
import play.api.data.Forms._

trait SortOrderForm {

  val sortOrderForm = Form {
    mapping(
      "sortBy" -> SortField.formField,
      "desc" -> boolean
    )(SortOrder.apply)(SortOrder.unapply)
  }

}
