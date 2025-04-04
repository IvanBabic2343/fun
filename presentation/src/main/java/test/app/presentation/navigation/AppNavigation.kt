package test.app.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import test.app.presentation.ui.UserRepositoriesScreen
import test.app.presentation.ui.UserRepositoriesDetailsScreen

@Composable
fun AppNavigation() {

    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = NavigationRoute.UserRepositories.route) {
        composable(NavigationRoute.UserRepositories.route) {
            UserRepositoriesScreen(navController = navController)
        }
        composable(
            route = NavigationRoute.RepoDetails.route,
            arguments = listOf(
                navArgument("repoName") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val repoName = backStackEntry.arguments?.getString("repoName")

            UserRepositoriesDetailsScreen(
                repoName = repoName ?: ""
            )
        }
    }
}