package com.indigo.woorm.shelves

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.indigo.woorm.common.loading.Loading
import com.indigo.woorm.shelves.viewModel.ShelvesState
import com.indigo.woorm.shelves.viewModel.ShelvesViewModel
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun ShelvesScreen(
    onClickBack: () -> Unit,
    viewModel: ShelvesViewModel = koinViewModel()
) {
    val shelvesState by viewModel.shelvesState.collectAsState()

    when (val state = shelvesState) {
        is ShelvesState.Loading -> Loading(modifier = Modifier.fillMaxSize())
        is ShelvesState.Success -> ShelvesContent(
            shelves = state.shelves,
            onClickBack = onClickBack,
            onRenameShelf = viewModel::renameShelf,
            onDeleteShelf = viewModel::deleteShelf,
            onCreateShelf = viewModel::createShelf
        )
    }
}
