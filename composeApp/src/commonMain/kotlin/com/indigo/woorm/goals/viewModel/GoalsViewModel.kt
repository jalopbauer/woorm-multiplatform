package com.indigo.woorm.goals.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.indigo.woorm.goals.GoalPreview
import com.indigo.woorm.goals.service.GoalService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.time.Clock
import kotlinx.datetime.todayIn
import kotlinx.datetime.TimeZone

class GoalsViewModel(
    private val goalService: GoalService,
): ViewModel() {

    private var _goalsState = MutableStateFlow<GoalsState>(GoalsState.Loading)
    val goalsState = _goalsState.asStateFlow()

    init {
        loadGoals()
    }

    fun loadGoals() {
        viewModelScope.launch {
            _goalsState.update { GoalsState.Loading }
            runCatching {
                goalService.goals().sortedByDescending { goal -> goal.year }
            }.onSuccess {
                _goalsState.emit(GoalsState.Success(it))
            }
        }
    }

    fun createGoal(amount: Int) {
        viewModelScope.launch {
            val currentYear = Clock.System.todayIn(TimeZone.currentSystemDefault()).year
            val currentGoal = (goalsState.value as? GoalsState.Success)
                ?.goals?.firstOrNull { it.year == currentYear }
            if (currentGoal != null) {
                goalService.set(id = currentGoal.id, amount = amount)
            } else {
                goalService.create(amount = amount)
            }
            loadGoals()
        }
    }

    fun setGoal(goal: GoalPreview, amount: Int) {
        viewModelScope.launch {
            goalService.set(id = goal.id, amount = amount)
            loadGoals()
        }
    }

    fun deleteGoal(goal: GoalPreview) {
        viewModelScope.launch {
            goalService.delete(id = goal.id)
            loadGoals()
        }
    }
}

sealed interface GoalsState {
    data object Loading: GoalsState
    data class Success(
        val goals: List<GoalPreview>
    ) : GoalsState
}
