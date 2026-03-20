package co.edu.uniquindio.ingesis.tenmatch.ui.navigation

/**
 * Sealed class defining the routes for the NavHost.
 * Provides type-safety for navigation paths.
 */
sealed class MainRoutes(val route: String) {
    // Game Flow
    object Start : MainRoutes("start")
    object Game : MainRoutes("game/{playerName}") {
        fun createRoute(playerName: String) = "game/$playerName"
    }
    object Results : MainRoutes("results/{playerName}/{score}") {
        fun createRoute(playerName: String, score: Int) = "results/$playerName/$score"
    }
}
