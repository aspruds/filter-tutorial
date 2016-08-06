package models.ui

import enumeratum._

sealed trait SortField extends EnumEntry

object SortField extends PlayEnum[SortField] {

  val values = findValues

  case object Id extends SortField
  case object Name extends SortField
  case object Surname extends SortField
  case object DateCreated extends SortField

}