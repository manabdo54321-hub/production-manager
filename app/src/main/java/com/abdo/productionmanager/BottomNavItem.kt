package com.abdo.productionmanager

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.ui.graphics.vector.ImageVector

data class BottomNavItem(
    val label: String,
    val route: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector
)

val bottomNavItems = listOf(
    BottomNavItem(
        label = "الرئيسية",
        route = Screen.Dashboard.route,
        selectedIcon = Icons.Filled.Home,
        unselectedIcon = Icons.Outlined.Home
    ),
    BottomNavItem(
        label = "الإنتاج",
        route = Screen.ProductionEntry.route,
        selectedIcon = Icons.Filled.Add,
        unselectedIcon = Icons.Outlined.Add
    ),
    BottomNavItem(
        label = "السجل",
        route = Screen.ProductionHistory.route,
        selectedIcon = Icons.Filled.History,
        unselectedIcon = Icons.Outlined.History
    ),
    BottomNavItem(
        label = "المهام",
        route = Screen.Tasks.route,
        selectedIcon = Icons.Filled.Task,
        unselectedIcon = Icons.Outlined.Task
    ),
    BottomNavItem(
        label = "المنتجات",
        route = Screen.ProductsManager.route,
        selectedIcon = Icons.Filled.Inventory,
        unselectedIcon = Icons.Outlined.Inventory2
    )
)
