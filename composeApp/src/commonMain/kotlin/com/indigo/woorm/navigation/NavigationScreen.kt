package com.indigo.woorm.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.NavigationRailItemDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteDefaults
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScaffold
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScaffoldDefaults
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteType
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.indigo.woorm.common.icon.AppIcon
import com.indigo.woorm.common.icon.Icon
import com.indigo.woorm.ui.theme.AppTheme


@Composable
fun NavigationScreen(
    navController: NavHostController,
    content: @Composable ColumnScope.() -> Unit,
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    val routes = listOf(
        Route.Base.Library(),
        Route.Base.Clubs,
        Route.Base.Home,
        Route.Base.Search(),
        Route.Base.Woorm,
    )
    val showNavigation = routes.any { route -> currentRouteContainsRoute(currentRoute, route) }

    val adaptiveInfo = currentWindowAdaptiveInfo()
    val defaultLayoutType = NavigationSuiteScaffoldDefaults.calculateFromAdaptiveInfo(adaptiveInfo)

    val currentLayoutType = if (showNavigation) {
        defaultLayoutType
    } else {
        NavigationSuiteType.None
    }

    val myNavigationSuiteItemColors = NavigationSuiteDefaults.itemColors(
        navigationBarItemColors = NavigationBarItemDefaults.colors(
            indicatorColor = AppTheme.colors.neutral.stroke,
            selectedIconColor = AppTheme.colors.neutral.stroke
        ),
        navigationRailItemColors = NavigationRailItemDefaults.colors(
            indicatorColor = AppTheme.colors.neutral.stroke,
            selectedIconColor = AppTheme.colors.neutral.stroke
        ),
        navigationDrawerItemColors = NavigationDrawerItemDefaults.colors(
            selectedContainerColor = AppTheme.colors.neutral.stroke,
            selectedIconColor = AppTheme.colors.neutral.stroke
        )
    )

    val navigationSuiteColors = NavigationSuiteDefaults.colors(
        navigationBarContainerColor = AppTheme.colors.neutral.background,
        navigationBarContentColor = AppTheme.colors.neutral.text,
        navigationRailContainerColor = AppTheme.colors.neutral.background,
        navigationRailContentColor = AppTheme.colors.neutral.text,
        navigationDrawerContainerColor = AppTheme.colors.neutral.background,
        navigationDrawerContentColor = AppTheme.colors.neutral.text,
        shortNavigationBarContainerColor = AppTheme.colors.neutral.background,
        shortNavigationBarContentColor = AppTheme.colors.neutral.text

    )
    NavigationSuiteScaffold(
        navigationSuiteColors = navigationSuiteColors,
        layoutType = currentLayoutType,
        navigationSuiteItems = {
            if (showNavigation) {
                routes.forEach { route ->
                    val (label, icon) = when (route) {
                        Route.Base.Clubs -> "Clubs" to AppIcon.UsersRound
                        Route.Base.Home -> "Home" to AppIcon.House
                        is Route.Base.Library -> "Library" to AppIcon.LibraryBig
                        Route.Base.Woorm -> "Woorm" to AppIcon.Worm
                        is Route.Base.Search -> "Search" to AppIcon.Search
                    }
                    item(
                        colors = myNavigationSuiteItemColors,
                        icon = {
                            Icon(
                                icon = icon,
                                contentDescription = label,
                                tint = AppTheme.colors.neutral.text
                            )
                        },
                        label = {
                            Text(
                                text = label,
                                color = AppTheme.colors.neutral.text
                            )
                        },
                        selected = currentRouteContainsRoute(currentRoute, route),
                        onClick = { navController.navigate(route = route) }
                    )
                }
            }

        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(AppTheme.colors.neutral.background)
        ) {
            content()
        }
    }
}

private fun currentRouteContainsRoute(
    currentRoute: String?,
    route: Route.Base
): Boolean {
    val routeClassName = route::class.simpleName ?: return false
    return currentRoute?.contains(routeClassName) ?: false
}