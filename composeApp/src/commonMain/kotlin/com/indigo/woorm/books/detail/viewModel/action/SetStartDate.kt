package com.indigo.woorm.books.detail.viewModel.action

import kotlinx.datetime.LocalDate

class SetStartDate(val date: LocalDate?): BookDetailAction {
    override val description: String
        get() = "Set start date: $date"
}
