package models.users

import org.joda.time.DateTime

case class User(id: Long, name: String, surname: String, statusId: Long, created: DateTime, deleted: Option[DateTime])