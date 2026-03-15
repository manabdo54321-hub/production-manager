package com.abdo.productionmanager

import androidx.compose.runtime.Composable
import androidx.compose.material3.Text
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.abdo.feature.production.ProductionEntryScreen
import com.abdo.feature.production.ProductionHistoryScreen
import com.abdo.feature.production.ProductsManagerScreen
import com.abdo.feature.tasks.TasksScreen
import com.abdo.feature.tasks.AddTaskScreen

@Composable
fun AppNavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Screen.Dashboard.route
    ) {
        composable(Screen.Dashboard.route) {
            DashboardScreen(
                onNavigateToProductionEntry = {
                    navController.navigate(Screen.ProductionEntry.route)
                },
                onNavigateToHistory = {
                    navController.navigate(Screen.ProductionHistory.route)
                },
                onNavigateToProducts = {
                    navController.navigate(Screen.ProductsManager.route)
                },
                onNavigateToTasks = {
                    navController.navigate(Screen.Tasks.route)
                },
                onNavigateToFinance = {
                    navController.navigate(Screen.Finance.route)
                }
            )
        }
        composable(Screen.ProductionEntry.route) {
            ProductionEntryScreen(
                onNavigateBack = { navController.popBackStack() }
            )
        }
        composable(Screen.ProductionHistory.route) {
            ProductionHistoryScreen(
                onNavigateBack = { navController.popBackStack() }
            )
        }
        composable(Screen.ProductsManager.route) {
            ProductsManagerScreen(
                onNavigateBack = { navController.popBackStack() }
            )
        }
        composable(Screen.Tasks.route) {
            TasksScreen(
                onNavigateBack = { navController.popBackStack() },
                onNavigateToAddTask = { navController.navigate(Screen.AddTask.route) }
            )
        }
        composable(Screen.AddTask.route) {
            AddTaskScreen(
                onNavigateBack = { navController.popBackStack() }
            )
        }
        composable(Screen.Finance.route) {
            Text("المالية — قريباً")
        }
        composable(Screen.AddExpense.route) {
            Text("إضافة مصروف — قريباً")
        }
        composable(Screen.AddIncome.route) {
            Text("إضافة دخل — قريباً")
        }
        composable(Screen.Analytics.route) {
            Text("التحليلات — قريباً")
        }
        composable(Screen.Settings.route) {
            Text("الإعدادات — قريباً")
        }
    }
}
