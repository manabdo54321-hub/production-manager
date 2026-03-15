package com.abdo.productionmanager

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.compose.material3.Text

@Composable
fun AppNavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Screen.Dashboard.route
    ) {
        composable(Screen.Dashboard.route) {
            Text("Dashboard — قريباً")
        }
        composable(Screen.ProductionEntry.route) {
            Text("تسجيل الإنتاج — قريباً")
        }
        composable(Screen.ProductionHistory.route) {
            Text("سجل الإنتاج — قريباً")
        }
        composable(Screen.ProductsManager.route) {
            Text("إدارة المنتجات — قريباً")
        }
        composable(Screen.Tasks.route) {
            Text("المهام — قريباً")
        }
        composable(Screen.AddTask.route) {
            Text("إضافة مهمة — قريباً")
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
