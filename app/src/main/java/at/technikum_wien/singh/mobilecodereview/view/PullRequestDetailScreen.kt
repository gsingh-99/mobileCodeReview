package at.technikum_wien.singh.mobilecodereview.view

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import at.technikum_wien.singh.mobilecodereview.data.RepositoryItem
import at.technikum_wien.singh.mobilecodereview.data.RepositoryItemRepository
import at.technikum_wien.singh.mobilecodereview.data.vscModules.VSCPullrequest
import at.technikum_wien.singh.mobilecodereview.data.vscModules.capitalized
import at.technikum_wien.singh.mobilecodereview.ui.theme.MobileCodeReviewTheme
import at.technikum_wien.singh.mobilecodereview.viewmodel.CodeReviewViewModel
import at.technikum_wien.singh.mobilecodereview.viewmodel.CodeReviewViewModelFactory

@Composable
fun PullRequestDetailScreen(
    navController: NavController?,
    viewModel: CodeReviewViewModel,
    repositoryItem: RepositoryItem?,
) {
    viewModel.title.value =
        viewModel.VSCPullrequestDetail.value.title ?: "Pull Request Detail Screen"

    Row(modifier = Modifier.fillMaxWidth()) {
        Spacer(modifier = Modifier.width(12.dp))
        Column() {
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = "Status (${viewModel.VSCPullrequestDetail.value.state?.capitalized() ?: ""}) ${viewModel.VSCPullrequestDetail.value.head.ref} -> ${viewModel.VSCPullrequestDetail.value.base.ref}",
                color = MaterialTheme.colors.primary,
                style = MaterialTheme.typography.subtitle2
            )
            Spacer(modifier = Modifier.height(24.dp))
            Text(
                text = "Created by ${viewModel.VSCPullrequestDetail.value.user.login ?: ""}",
                color = MaterialTheme.colors.primaryVariant,
                style = MaterialTheme.typography.subtitle2
            )
            if (viewModel.VSCPullrequestDetail.value.body != null) {

                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = viewModel.VSCPullrequestDetail.value.body ?: "",
                    color = MaterialTheme.colors.primary,
                    style = MaterialTheme.typography.subtitle1
                )
                Spacer(modifier = Modifier.height(24.dp))
            } else {
                Spacer(modifier = Modifier.height(18.dp))
            }

            if (viewModel.VSCPullrequestDetail.value.changed_files != 1)
                Text(
                    text = "${viewModel.VSCPullrequestDetail.value.changed_files} files changed",
                    color = MaterialTheme.colors.primary,
                    style = MaterialTheme.typography.h1
                )
            else
                Text(
                    text = "${viewModel.VSCPullrequestDetail.value.changed_files} file changed",
                    color = MaterialTheme.colors.primary,
                    style = MaterialTheme.typography.h1
                )

            Spacer(modifier = Modifier.height(24.dp))
            Box(modifier = Modifier.clickable
            { navController?.navigate(Screen.PulRequestDetailCommitsScreen.route + "/${repositoryItem?.id}") }) {
                if (viewModel.VSCPullrequestDetail.value.commits != 1)
                    Text(
                        text = "${viewModel.VSCPullrequestDetail.value.commits} commits",
                        color = MaterialTheme.colors.primary,
                        style = MaterialTheme.typography.h1

                    )
                else
                    Text(
                        text = "${viewModel.VSCPullrequestDetail.value.commits} commit",
                        color = MaterialTheme.colors.primary,
                        style = MaterialTheme.typography.h1
                    )
            }
            Spacer(modifier = Modifier.height(24.dp))
            Text(
                text = "Static code analysis",
                color = MaterialTheme.colors.primary,
                style = MaterialTheme.typography.h1
            )
            Spacer(modifier = Modifier.height(24.dp))
            Text(
                text = "Reviews",
                color = MaterialTheme.colors.primary,
                style = MaterialTheme.typography.h1
            )
            Spacer(modifier = Modifier.height(24.dp))
            Text(
                text = "Comments",
                color = MaterialTheme.colors.primary,
                style = MaterialTheme.typography.h1
            )


        }
        Spacer(modifier = Modifier.width(12.dp))

    }
}