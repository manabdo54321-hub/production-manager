package com.abdo.productionmanager

sealed class Screen(val route: String) {
    object Dashboard : Screen("dashboard")
    object ProductionEntry : Screen("production_entry")
    object ProductionHistory : Screen("production_history")
    object ProductsManager : Screen("products_manager")
    object Tasks : Screen("tasks")
    object AddTask : Screen("add_task")
    object Finance : Screen("finance")
    object AddExpense : Screen("add_expense")
    object AddIncome : Screen("add_income")
    object Analytics : Screen("analytics")
    object Settings : Screen("settings")
}
