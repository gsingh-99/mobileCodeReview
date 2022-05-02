package at.technikum_wien.singh.mobilecodereview.view

sealed class Screen(val route: String) {
    object MainScreen : Screen(route = "main_screen")
    object WelcomeScreen : Screen(route = "welcome_screen")
    object PullRequestScreen : Screen(route = "pullRequest_screen")
    object RepositoriesScreen : Screen(route = "repositories_screen")
    object PullRequestDetailScreen : Screen(route = "pullRequest_detail_screen")
    object PullRequestDetailStaticAnalyseScreen : Screen(route="pullRequest_detail_screen_static_code_review")
    object PullRequestDetailCommitsScreen : Screen(route = "pullRequest_detail_screen_commits")
    object PullRequestDetailFilesScreen : Screen(route = "pullRequest_detail_screen_files")
    object PullRequestDetailFilesDetailScreen:Screen(route = "pullRequest_detail_screen_files_details")
    object PullRequestDetailCommentsScreen:Screen(route="pullRequest_detail_screen_comments")
    object PullRequestDetailReviewsScreen:Screen(route="pullRequest_detail_screen_reviews")
}
