package com.abdo.productionmanager

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.abdo.core.ui.theme.ProductionManagerTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val viewModel: MainViewModel = hiltViewModel()
            val themeId by viewModel.themeId.collectAsState(initial = 0)
            val darkMode by viewModel.darkMode.collectAsState(initial = false)

            ProductionManagerTheme(
                themeId = themeId,
                darkMode = darkMode
            ) {
                var showSplash by remember { mutableStateOf(true) }
                if (showSplash) {
                    SplashScreen(onFinished = { showSplash = false })
                } else {
                    MainScreen(viewModel = viewModel)
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(viewModel: MainViewModel) {
    val navController = rememberNavController()
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    val topLevelRoutes = bottomNavItems.map { it.route }
    val showBottomNav = currentRoute in topLevelRoutes

    val userName by viewModel.userName.collectAsState(initial = "عبده")
    val userJob by viewModel.userJob.collectAsState(initial = "صانع سنجر")
    val themeId by viewModel.themeId.collectAsState(initial = 0)
    val darkMode by viewModel.darkMode.collectAsState(initial = false)
    val todayProduction by viewModel.todayProduction.collectAsState(initial = 0)
    val pendingTasksCount by viewModel.pendingTasksCount.collectAsState(initial = 0)

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            AppDrawerContent(
                userName = userName,
                userJob = userJob,
                currentRoute = currentRoute,
                todayProduction = todayProduction,
                pendingTasksCount = pendingTasksCount,
                currentThemeId = themeId,
                darkMode = darkMode,
                onThemeChange = { viewModel.setTheme(it) },
                onDarkModeChange = { viewModel.setDarkMode(it) },
                onNavigate = { route ->
                    navController.navigate(route) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                onClose = { scope.launch { drawerState.close() } }
            )
        }
    ) {
        Scaffold(
            topBar = {
                if (showBottomNav) {
                    TopAppBar(
                        title = {
                            Text(
                                text = when (currentRoute) {
                                    Screen.Dashboard.route -> "الرئيسية"
                                    Screen.ProductionEntry.route -> "تسجيل الإنتاج"
                                    Screen.ProductionHistory.route -> "سجل الإنتاج"
                                    Screen.ProductsManager.route -> "إدارة المنتجات"
                                    Screen.Tasks.route -> "المهام"
                                    else -> "مدير الإنتاج"
                                }
                            )
                        },
                        navigationIcon = {
                            IconButton(onClick = {
                                scope.launch { drawerState.open() }
                            }) {
                                Icon(Icons.Default.Menu, contentDescription = "القائمة")
                            }
                        },
                        colors = TopAppBarDefaults.topAppBarColors(
                            containerColor = MaterialTheme.colorScheme.primary,
                            titleContentColor = MaterialTheme.colorScheme.onPrimary,
                            navigationIconContentColor = MaterialTheme.colorScheme.onPrimary
                        )
                    )
                }
            },
            bottomBar = {
                if (showBottomNav) {
                    NavigationBar {
                        bottomNavItems.forEach { item ->
                            val selected = navBackStackEntry?.destination
                                ?.hierarchy?.any { it.route == item.route } == true
                            NavigationBarItem(
                                selected = selected,
                                onClick = {
                                    navController.navigate(item.route) {
                                        popUpTo(navController.graph.findStartDestination().id) {
                                            saveState = true
                                        }
                                        launchSingleTop = true
                                        restoreState = true
                                    }
                                },
                                icon = {
                                    Icon(
                                        if (selected) item.selectedIcon else item.unselectedIcon,
                                        contentDescription = item.label
                                    )
                                },
                                label = { Text(item.label) }
                            )
                        }
                    }
                }
            }
        ) { paddingValues ->
            AppNavGraph(
                navController = navController,
                paddingValues = paddingValues
            )
        }
    }
}
