package com.example.tenmatch.ui.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.PlayArrow
import androidx.navigation.compose.navigation
import com.example.tenmatch.ui.screens.StartScreen
import com.example.tenmatch.ui.screens.GameScreen
import com.example.tenmatch.ui.screens.ResultsScreen
import com.example.tenmatch.ui.screens.HomeScreen
import com.example.tenmatch.ui.screens.SearchScreen
import com.example.tenmatch.ui.screens.ProfileScreen
import com.example.tenmatch.domain.Report
import com.example.tenmatch.domain.Location
import com.example.tenmatch.domain.ReportStatus
import androidx.navigation.navArgument
import androidx.navigation.NavType

@Composable
fun MainNavigation(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = "auth"
    ) {
        // Authentication Nested Flow
        navigation(startDestination = MainRoutes.Login.route, route = "auth") {
            composable(MainRoutes.Login.route) {
                // Placeholder for Login
                Text("Login Screen")
            }
            composable(MainRoutes.Register.route) {
                // Placeholder for Register
                Text("Register Screen")
            }
        }

        // Main App Flow (with Scaffold)
        navigation(startDestination = MainRoutes.Home.route, route = "main") {
            composable(MainRoutes.Home.route) {
                val dummyReports = listOf(
                    Report("1", "Bache en vía principal", Location(0.0, 0.0, "Calle 100"), ReportStatus.PENDING, "user1"),
                    Report("2", "Luminaria fundida", Location(0.0, 0.0, "Carrera 7"), ReportStatus.RESOLVED, "user2")
                )
                MainScaffold(navController) {
                    HomeScreen(dummyReports)
                }
            }
            composable(MainRoutes.Search.route) {
                MainScaffold(navController) {
                    SearchScreen()
                }
            }
            composable(MainRoutes.Profile.route) {
                MainScaffold(navController) {
                    ProfileScreen("Juan Pérez", "juan@example.com")
                }
            }
        }

        // Game Flow
        navigation(startDestination = MainRoutes.Start.route, route = "game_flow") {
            composable(MainRoutes.Start.route) {
                StartScreen(navController)
            }
            composable(
                route = MainRoutes.Game.route,
                arguments = listOf(navArgument("playerName") { type = NavType.StringType })
            ) { backStackEntry ->
                val playerName = backStackEntry.arguments?.getString("playerName") ?: "Player"
                GameScreen(navController, playerName)
            }
            composable(
                route = MainRoutes.Results.route,
                arguments = listOf(
                    navArgument("playerName") { type = NavType.StringType },
                    navArgument("score") { type = NavType.IntType }
                )
            ) { backStackEntry ->
                val playerName = backStackEntry.arguments?.getString("playerName") ?: "Player"
                val score = backStackEntry.arguments?.getInt("score") ?: 0
                ResultsScreen(navController, playerName, score)
            }
        }
    }
}

@Composable
fun MainScaffold(
    navController: NavHostController,
    content: @Composable () -> Unit
) {
    Scaffold(
        bottomBar = {
            val navBackStackEntry by navController.currentBackStackEntryAsState()
            val currentRoute = navBackStackEntry?.destination?.route

            NavigationBar {
                NavigationBarItem(
                    icon = { Icon(Icons.Default.Home, contentDescription = "Home") },
                    label = { Text("Inicio") },
                    selected = currentRoute == MainRoutes.Home.route,
                    onClick = { navController.navigate(MainRoutes.Home.route) }
                )
                NavigationBarItem(
                    icon = { Icon(Icons.Default.Search, contentDescription = "Search") },
                    label = { Text("Búsqueda") },
                    selected = currentRoute == MainRoutes.Search.route,
                    onClick = { navController.navigate(MainRoutes.Search.route) }
                )
                NavigationBarItem(
                    icon = { Icon(Icons.Default.PlayArrow, contentDescription = "Game") },
                    label = { Text("Juego") },
                    selected = currentRoute?.startsWith("game") == true,
                    onClick = { navController.navigate(MainRoutes.Start.route) }
                )
                NavigationBarItem(
                    icon = { Icon(Icons.Default.Person, contentDescription = "Profile") },
                    label = { Text("Perfil") },
                    selected = currentRoute == MainRoutes.Profile.route,
                    onClick = { navController.navigate(MainRoutes.Profile.route) }
                )
            }
        }
    ) { innerPadding ->
        org.jetbrains.annotations.Nullable
        androidx.compose.foundation.layout.Box(modifier = Modifier.padding(innerPadding)) {
            content()
        }
    }
}
