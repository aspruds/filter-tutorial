package models.ui

/**
 * UI helper from http://kev009.com/wp/2012/12/reusable-pagination-in-play-2/
 * @param totalResults
 * @param currentPage
 * @param pageSize
 */
case class Pager(totalResults: Int, currentPage: Int=1, pageSize: Int=14)
