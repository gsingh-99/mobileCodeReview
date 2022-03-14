package at.technikum_wien.singh.mobilecodereview.view

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import at.technikum_wien.singh.mobilecodereview.R
import at.technikum_wien.singh.mobilecodereview.data.vscModules.VSCPullrequest
import at.technikum_wien.singh.mobilecodereview.data.vscModules.VSCRepositoryItem
import at.technikum_wien.singh.mobilecodereview.viewmodel.CodeReviewViewModel

@Composable
fun PullRequestScreen(navController: NavController?, viewModel: CodeReviewViewModel) {
    LaunchedEffect(Unit, block = {
        viewModel.getPullRequestList()
    })
    viewModel.title.value = stringResource(R.string.home_pull_request)
    Text(text = viewModel.errorMessage)
    LazyColumn(Modifier.fillMaxWidth()) {
        items(viewModel.VSCPullRequestList) { pullRequestItem ->
            PullRequestItemRow(
                pullRequestItem = pullRequestItem,
            )
        }
    }
}

@Composable
fun PullRequestItemRow(
    pullRequestItem: VSCPullrequest,
) {
    Text(text = "" + pullRequestItem.id)
    Text(text = pullRequestItem.title ?: "")
    Text(text = pullRequestItem.url ?: "")
    Text(text = "" + pullRequestItem.head.user.login)
    Text(text = "" + pullRequestItem.head.repo.full_name)
}
