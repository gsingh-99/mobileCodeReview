package at.technikum_wien.singh.mobilecodereview.view

sealed class Screen (val route : String) {
    object MainScreen : Screen(route = "main_screen")
    object WelcomeScreen : Screen(route = "welcome_screen")
    object PullRequestScreen : Screen(route = "pullRequest_screen")
    object RepositoriesScreen : Screen(route = "repositories_screen")
    object PullRequestDetailScreen: Screen(route="pullRequest_detail_screen")
}
