package test.app.presentation.navigation

sealed class NavigationRoute(val route: String) {
    data object UserRepositories : NavigationRoute("user_repositories")
    data object RepoDetails : NavigationRoute("repo_details/{repoName}") {
        fun createRoute(
            repoName: String
        ) = "repo_details/$repoName"
    }
}
