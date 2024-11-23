package com.ynemreuslu.instantqr.navigaiton

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.ynemreuslu.instantqr.R

@Composable
fun BottomNavigationBarScreen(navController: NavHostController) {
    val scannerTitle = stringResource(R.string.scanner)
    val historyTitle = stringResource(R.string.history)
    val creatorTitle = stringResource(R.string.creator)

    val bottomNavigationItems = remember {
        listOf(
            BottomNavigationItem(
                route = "scanner",
                title = scannerTitle,
                iconResId = R.drawable.ic_scanner
            ),
            BottomNavigationItem(
                route = "history",
                title = historyTitle,
                iconResId = R.drawable.ic_history
            ),
            BottomNavigationItem(
                route = "creator",
                title = creatorTitle,
                iconResId = R.drawable.ic_creator
            ),
        )
    }

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    NavigationBar(
        containerColor = MaterialTheme.colorScheme.onPrimary,
        contentColor = MaterialTheme.colorScheme.primary,
        tonalElevation = 3.dp
    ) {
        bottomNavigationItems.forEach { item ->
            AddNavigationItem(
                item = item,
                currentDestination = currentDestination,
                navController = navController
            )
        }
    }
}

@Composable
fun RowScope.AddNavigationItem(
    item: BottomNavigationItem,
    currentDestination: NavDestination?,
    navController: NavHostController
) {

    NavigationBarItem(
        label = {
            Text(
                text = item.title,
                style = MaterialTheme.typography.labelSmall
            )
        },
        icon = {
            Icon(
                painter = painterResource(id = item.iconResId),
                contentDescription = item.title
            )
        },
        selected = currentDestination?.hierarchy?.any { it.route == item.route } == true,
        colors = NavigationBarItemDefaults.colors(
            selectedIconColor = MaterialTheme.colorScheme.primary,
            unselectedIconColor = MaterialTheme.colorScheme.primary,
            selectedTextColor = MaterialTheme.colorScheme.primary,
            unselectedTextColor = MaterialTheme.colorScheme.onSurfaceVariant
        ),
        onClick = {
            if (currentDestination?.route != item.route) {
                navController.navigate(item.route) {
                    popUpTo(navController.graph.findStartDestination().id) {
                        inclusive = true
                    }
                    launchSingleTop = true
                }
            }
        }
    )
}



