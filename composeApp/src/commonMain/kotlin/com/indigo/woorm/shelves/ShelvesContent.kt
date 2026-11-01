package com.indigo.woorm.shelves

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import woorm.composeapp.generated.resources.*
import com.indigo.woorm.common.bottomSheet.confirm.ConfirmationBottomSheet
import com.indigo.woorm.common.bottomSheet.rememberBottomSheetState
import com.indigo.woorm.common.button.Button
import com.indigo.woorm.common.button.fab.FloatingActionButton
import com.indigo.woorm.common.empty.Empty
import com.indigo.woorm.common.icon.AppIcon
import com.indigo.woorm.common.input.textField.TextField
import com.indigo.woorm.common.preview.ScreenPreview
import com.indigo.woorm.common.screen.Screen
import com.indigo.woorm.library.books.shelves.LibraryBookShelf
import com.indigo.woorm.shelves.composables.list.ShelfList
import com.indigo.woorm.shelves.composables.topBar.ShelvesTopBar
import com.indigo.woorm.ui.theme.AppTheme
import com.indigo.woorm.ui.theme.Rounded
import com.indigo.woorm.ui.theme.Style
import com.indigo.woorm.ui.theme.Variant

@Composable
fun ShelvesContent(
    shelves: List<LibraryBookShelf>,
    onClickBack: () -> Unit,
    onRenameShelf: (LibraryBookShelf, String) -> Unit,
    onCreateShelf: (String) -> Unit,
    onDeleteShelf: (LibraryBookShelf) -> Unit,
) {
    val renameSheet = rememberBottomSheetState()
    var editingShelf by remember { mutableStateOf<LibraryBookShelf?>(null) }
    var name by remember { mutableStateOf("") }

    val createShelfSheetState = rememberBottomSheetState()
    var newShelfName by remember { mutableStateOf("") }

    val deleteSheetState = rememberBottomSheetState()
    var deletingShelf by remember { mutableStateOf<LibraryBookShelf?>(null) }

    Screen(
        topBar = { ShelvesTopBar(onClickBack = onClickBack) },
        horizontalPadding = if (shelves.isNotEmpty()) AppTheme.spacing.horizontal.none else AppTheme.spacing.horizontal.screenMobile,
        floatingActionButton = {
            if (shelves.isNotEmpty()) {
                FloatingActionButton(
                    onFabClick = {
                        createShelfSheetState.show()
                    },
                    icon = AppIcon.Plus,
                    contentDescription = "Create Shelf"
                )
            }
        }
    ) {
        if (shelves.isEmpty()) {
            Empty(
                modifier = Modifier.fillMaxSize(),
                id = Res.drawable.empty_shelves,
                text = "Start organizing your books in shelves"
            ) {
                Button(
                    modifier = Modifier.fillMaxWidth(),
                    variant = Variant.Primary,
                    style = Style.Neutral,
                    leadingIcon = AppIcon.Plus,
                    shape = Rounded,
                    contentDescription = "Create shelf",
                    label = "Create shelf",
                    onClick = {
                        createShelfSheetState.show()
                    }
                )
            }
        } else {
            ShelfList(
                shelves = shelves,
                onEdit = {
                    editingShelf = it
                    name = it.name
                    renameSheet.show()
                },
                onDelete = {
                    deletingShelf = it
                    deleteSheetState.show()
                }
            )
        }
    }

    CreateShelfBottomSheet(
        createShelfSheetState = createShelfSheetState,
        newShelfName = newShelfName,
        onConfirm = {
            onCreateShelf(newShelfName.trim())
            newShelfName = ""
        },
        onValueChange = {
            newShelfName = it
        }
    )

    ConfirmationBottomSheet(
        state = renameSheet,
        onConfirm = {
            editingShelf?.let { onRenameShelf(it, name.trim()) }
            editingShelf = null
        },
        confirmEnabled = name.trim().isNotBlank(),
        header = {
            Text(
                text = "Rename shelf",
                style = MaterialTheme.typography.headlineSmall,
                color = AppTheme.colors.neutral.text
            )
        }
    ) {
        TextField(
            modifier = Modifier.fillMaxWidth(),
            value = name,
            onValueChange = { name = it },
            placeholder = "Shelf name",
            maxLength = 50
        )
        Spacer(modifier = Modifier.height(AppTheme.spacing.vertical.default))
    }

    ConfirmationBottomSheet(
        state = deleteSheetState,
        onConfirm = {
            deletingShelf?.let(onDeleteShelf)
        },
        confirmLabel = "Delete shelf",
        style = Style.Danger,
        header = {
            Text(
                text = "Delete shelf",
                style = MaterialTheme.typography.headlineSmall,
                color = AppTheme.colors.neutral.text
            )
        }
    ) {
        Text(
            text = "\"${deletingShelf?.name}\" will be deleted. This action cannot be undone.",
            style = MaterialTheme.typography.bodyLarge,
            color = AppTheme.colors.neutral.text
        )
    }
}



@Preview
@Composable
private fun ShelvesContent_Preview() {
    ScreenPreview {
        ShelvesContent(
            shelves = listOf(
                LibraryBookShelf(id = 1, name = "Favorites"),
                LibraryBookShelf(id = 2, name = "To Read"),
                LibraryBookShelf(id = 3, name = "Finished")
            ),
            onClickBack = {},
            onRenameShelf = { _, _ -> },
            onDeleteShelf = {},
            onCreateShelf = {},
        )
    }
}
