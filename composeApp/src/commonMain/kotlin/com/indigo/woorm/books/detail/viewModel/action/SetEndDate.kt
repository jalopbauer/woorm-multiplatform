package com.indigo.woorm.books.detail.viewModel.action

import kotlinx.datetime.LocalDate

class SetEndDate(val date: LocalDate?): BookDetailAction {
    override val description: String
        get() = "Set end date: $date"
} 
