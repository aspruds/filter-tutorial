package controllers.ui

import models.users.forms.Filter
import play.api.data.Form
import play.api.data.Forms._

trait FilterForm {

  val filterForm = Form {
    mapping(
      "name" -> optional(text()),
      "surname" -> optional(text()),
      "statusIds" -> optional(list(longNumber())),
      "dateCreatedFrom" -> optional(jodaDate("dd-MM-yyyy")),
      "dateCreatedUntil" -> optional(jodaDate("dd-MM-yyyy"))
    )(Filter.apply)(Filter.unapply)
  }

}
