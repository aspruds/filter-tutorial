package models.users.forms

import org.joda.time.DateTime

case class Filter(
                   name: Option[String],
                   surname: Option[String],
                   statusIds: Option[List[Long]],
                   dateCreatedFrom: Option[DateTime],
                   dateCreatedUntil: Option[DateTime]
                 ) {


  def normalizeDates(): Filter = {
    this
      .copy(dateCreatedFrom = dateCreatedFrom.map(_.plusDays(1).withTimeAtStartOfDay))
      .copy(dateCreatedUntil = dateCreatedUntil.map(_.plusDays(1).withTimeAtStartOfDay))
  }
}