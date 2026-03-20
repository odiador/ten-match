package co.edu.uniquindio.ingesis.tenmatch.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import co.edu.uniquindio.ingesis.tenmatch.ui.screens.MemoryGameStartScreen
import co.edu.uniquindio.ingesis.tenmatch.ui.screens.GameScreen
import co.edu.uniquindio.ingesis.tenmatch.ui.screens.ResultsScreen

@Composable
fun MainNavigation(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = MainRoutes.Start.route
    ) {
        // 1. Start / Onboarding Screen
        composable(MainRoutes.Start.route) {
            MemoryGameStartScreen(navController)
        }

        // 2. Game Screen
        composable(
            route = MainRoutes.Game.route,
            arguments = listOf(navArgument("playerName") { type = NavType.StringType })
        ) { backStackEntry ->
            val playerName = backStackEntry.arguments?.getString("playerName") ?: "Jugador"
            GameScreen(navController, playerName)
        }

        // 3. Results Screen
        composable(
            route = MainRoutes.Results.route,
            arguments = listOf(
                navArgument("playerName") { type = NavType.StringType },
                navArgument("score") { type = NavType.IntType }
            )
        ) { backStackEntry ->
            val playerName = backStackEntry.arguments?.getString("playerName") ?: "Jugador"
            val score = backStackEntry.arguments?.getInt("score") ?: 0
            ResultsScreen(navController, playerName, score)
        }
    }
}
