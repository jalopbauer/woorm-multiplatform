package com.indigo.woorm.books.detail.status.stars

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.indigo.woorm.books.detail.viewModel.LibraryBookUiState
import com.indigo.woorm.books.detail.viewModel.action.Rate

@Composable
fun Stars(
    rating: Double,
    isLibraryBookEditing: LibraryBookUiState.Editing?,
    rate: (Double) -> Unit
) {
    val currentRating = if (isLibraryBookEditing != null && isLibraryBookEditing.bookDetailAction is Rate) isLibraryBookEditing.bookDetailAction.rating else rating
    Column(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Row {
            listOf(1, 3, 5).forEach {
                Star(
                    index = it,
                    currentRating = currentRating,
                    rate = rate
                )
            }
        }
        Row {
            listOf(2, 4).forEach {
                Star(
                    index = it,
                    currentRating = currentRating,
                    rate = rate,
                )
            }
        }
    }
}
