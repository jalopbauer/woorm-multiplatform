package com.indigo.woorm.bookClubs

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.indigo.woorm.bookClubs.detail.BookClubScreen
import com.indigo.woorm.bookClubs.list.BookClubsScreen
import com.indigo.woorm.navigation.Route

fun NavGraphBuilder.bookClubs(navController: NavHostController) {
    composable<Route.Base.Clubs> {
        BookClubsScreen(
            onBookClubClick = {
                navController.navigate(Route.BookClub(it.id))
            },
        )
    }
    composable<Route.BookClub> {
        BookClubScreen(
            onClickBack = {
                navController.popBackStack()
            },
            onClickToBook = { bookPreview ->
                navController.navigate(Route.BookDetail(bookPreview.id))
            }
        )
    }
}

