package at.technikum_wien.singh.mobilecodereview.view

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import at.technikum_wien.singh.mobilecodereview.data.RepositoryItem
import at.technikum_wien.singh.mobilecodereview.data.vscModules.capitalized
import at.technikum_wien.singh.mobilecodereview.viewmodel.CodeReviewViewModel

@Composable
fun PullRequestDetailScreen(
    navController: NavController,
    viewModel: CodeReviewViewModel,
    repositoryItem: RepositoryItem?,
) {
    viewModel.title.value =
        viewModel.vscPullRequestDetail.value.title ?: "Pull Request Detail Screen"
    Row(modifier = Modifier.fillMaxWidth()) {
        Spacer(modifier = Modifier.width(12.dp))
        Column() {
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = "Status (${viewModel.vscPullRequestDetail.value.state?.capitalized() ?: ""}) ${viewModel.vscPullRequestDetail.value.head.ref} -> ${viewModel.vscPullRequestDetail.value.base.ref}",
                color = MaterialTheme.colors.primary,
                style = MaterialTheme.typography.subtitle1
            )
            Spacer(modifier = Modifier.height(24.dp))
            Text(
                text = "Created by ${viewModel.vscPullRequestDetail.value.user.login}",
                color = MaterialTheme.colors.primaryVariant,
                style = MaterialTheme.typography.subtitle1
            )
            if (viewModel.vscPullRequestDetail.value.body != null) {

                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = viewModel.vscPullRequestDetail.value.body ?: "",
                    color = MaterialTheme.colors.primary,
                    style = MaterialTheme.typography.subtitle2
                )
                Spacer(modifier = Modifier.height(24.dp))
            } else {
                Spacer(modifier = Modifier.height(18.dp))
            }
            Box(modifier = Modifier.clickable
            { navController.navigate(Screen.PullRequestDetailFilesScreen.route + "/${repositoryItem?.id}") }) {
                if (viewModel.vscPullRequestDetail.value.changed_files != 1)
                    Text(
                        text = "${viewModel.vscPullRequestDetail.value.changed_files} files changed",
                        color = MaterialTheme.colors.primary,
                        style = MaterialTheme.typography.h5
                    )
                else
                    Text(
                        text = "${viewModel.vscPullRequestDetail.value.changed_files} file changed",
                        color = MaterialTheme.colors.primary,
                        style = MaterialTheme.typography.h5
                    )
            }
            Spacer(modifier = Modifier.height(24.dp))
            Box(modifier = Modifier.clickable
            { navController.navigate(Screen.PullRequestDetailCommitsScreen.route + "/${repositoryItem?.id}") }) {
                if (viewModel.vscPullRequestDetail.value.commits != 1)
                    Text(
                        text = "${viewModel.vscPullRequestDetail.value.commits} commits",
                        color = MaterialTheme.colors.primary,
                        style = MaterialTheme.typography.h5

                    )
                else
                    Text(
                        text = "${viewModel.vscPullRequestDetail.value.commits} commit",
                        color = MaterialTheme.colors.primary,
                        style = MaterialTheme.typography.h5
                    )
            }
            Spacer(modifier = Modifier.height(24.dp))
            Text(
                text = "Static code analysis",
                color = MaterialTheme.colors.primary,
                style = MaterialTheme.typography.h5
            )
            Spacer(modifier = Modifier.height(24.dp))
            Box(modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    navController.navigate(route = Screen.PullRequestDetailReviewsScreen.route + "/${repositoryItem?.id}")
                }) {
                Text(
                    text = "Reviews",
                    color = MaterialTheme.colors.primary,
                    style = MaterialTheme.typography.h5
                )
            }
            Spacer(modifier = Modifier.height(24.dp))
            Box(modifier = Modifier.clickable {
                navController.navigate(route = Screen.PullRequestDetailCommentsScreen.route)
            }) {
                Column {
                    Text(
                        text = "Comments",
                        color = MaterialTheme.colors.primary,
                        style = MaterialTheme.typography.h5
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Box(
                        modifier = Modifier
                            .heightIn(0.dp, 180.dp)
                            .padding(0.dp, 0.dp, 12.dp, 0.dp)
                    ) {
                        LazyColumn(modifier = Modifier.fillMaxWidth()) {
                            items(viewModel.vscPullRequestDetailComments) { comment ->
                                PullRequestComments(viewModel = viewModel, comment = comment)
                            }
                        }
                    }
                }
            }
        }
        Spacer(modifier = Modifier.width(12.dp))

    }
}