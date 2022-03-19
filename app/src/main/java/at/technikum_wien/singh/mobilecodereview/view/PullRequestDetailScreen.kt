package at.technikum_wien.singh.mobilecodereview.view

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import at.technikum_wien.singh.mobilecodereview.data.RepositoryItem
import at.technikum_wien.singh.mobilecodereview.data.vscModules.VSCPullrequest
import at.technikum_wien.singh.mobilecodereview.viewmodel.CodeReviewViewModel

@Composable
fun PullRequestDetailScreen(
    navController: NavController,
    viewModel: CodeReviewViewModel,
    repositoryItem: RepositoryItem,
) {
    viewModel.title.value = "Pull Request Detail Screen"
    Column() {
        Text("Test")
        Text(repositoryItem.token)
        Text(repositoryItem.url)
        Text(viewModel.VSCPullrequestDetail.value.title ?: "")
    }
}