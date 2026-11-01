package com.indigo.woorm.books.detail.status.list

import androidx.compose.runtime.Composable
import com.indigo.woorm.library.books.LibraryBook
import com.indigo.woorm.library.books.status.LibraryBookStatus

@Composable
fun LibraryBookStatusList(
    libraryBook: LibraryBook?,
    onClickDeleteStatus: (LibraryBookStatus) -> Unit,
) {
    libraryBook?.statuses?.sortedByDescending { it.createdAt }?.forEach { prevStatus ->
        LibraryBookStatusItem(
            status = prevStatus,
            onClickDelete = { onClickDeleteStatus(prevStatus) }
        )
    }
}