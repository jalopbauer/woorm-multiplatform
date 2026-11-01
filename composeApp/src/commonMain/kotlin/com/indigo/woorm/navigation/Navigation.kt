package com.indigo.woorm.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navDeepLink
import com.indigo.woorm.bookClubs.bookClubs
import com.indigo.woorm.bookClubs.invite.InviteScreen
import com.indigo.woorm.books.detail.BookDetailScreen
import com.indigo.woorm.books.search.SearchScreen
import com.indigo.woorm.goals.GoalsScreen
import com.indigo.woorm.home.HomeScreen
import com.indigo.woorm.library.LibraryScreen
import com.indigo.woorm.profile.ProfileScreen
import com.indigo.woorm.shelves.ShelvesScreen
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun Navigation(
    navController: NavHostController,
) {
    NavHost(
        navController = navController,
        startDestination = Route.Base.Home
    ) {
        bookClubs(navController)
        composable<Route.Base.Home> {
            HomeScreen(
                viewModel = koinViewModel(),
                onClickBookDetailCard = { bookPreview ->
                    navController.navigate(Route.BookDetail(bookPreview.id))
                },
                onClickBookClubCard = { bookClubPreview ->
                    navController.navigate(Route.BookClub(bookClubPreview.id))
                },
            )
        }
        composable<Route.Base.Library> {
            LibraryScreen(
                viewModel = koinViewModel(),
                onCoverClick = { bookPreview ->
                    navController.navigate(Route.BookDetail(bookPreview.id))
                },
                navigateToSearch = {
                    navController.navigate(Route.Base.Search())
                },
                navigateToShelves = {
                    navController.navigate(Route.Shelves)
                },
            )
        }
        composable<Route.BookDetail> {
            BookDetailScreen(
                viewModel = koinViewModel(),
                onClickBackIconButton = { navController.popBackStack() }
            )
        }
        composable<Route.Base.Search> {
            SearchScreen(
                viewModel = koinViewModel(),
                onCoverClick = { bookPreview ->
                    navController.navigate(Route.BookDetail(bookPreview.id))
                }
            )

        }
        composable<Route.BookClub.Invite>(
            deepLinks = listOf(
                navDeepLink<Route.BookClub.Invite>(
                    basePath = "https://woorm.com/book-clubs/invite"
                )
            )
        ) {
            InviteScreen(
                viewModel = koinViewModel(),
                navigateToBookClubs = {
                    navController.navigate(Route.Base.Clubs)
                }
            )
        }
        composable<Route.Base.Woorm> {
            ProfileScreen(
                navigateToShelves = {
                    navController.navigate(Route.Shelves)
                },
                navigateToGoals = {
                    navController.navigate(Route.Goals)
                }
            )
        }

        composable<Route.Shelves> {
            ShelvesScreen(
                onClickBack = { navController.popBackStack() }
            )
        }

        composable<Route.Goals> {
            GoalsScreen(
                onClickBack = { navController.popBackStack() }
            )
        }
    }
}
