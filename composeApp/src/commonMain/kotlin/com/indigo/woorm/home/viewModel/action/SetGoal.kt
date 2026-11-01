package com.indigo.woorm.home.viewModel.action

data class SetGoal(val amount: Int) : HomeAction {
    override val description: String = "Amount set successfully"
}
