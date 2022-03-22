package at.technikum_wien.singh.mobilecodereview.view

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import at.technikum_wien.singh.mobilecodereview.data.RepositoryItem
import at.technikum_wien.singh.mobilecodereview.viewmodel.CodeReviewViewModel

@Composable
fun PullRequestDetailCommitsScreen(
    navController: NavController?,
    viewModel: CodeReviewViewModel,
    repositoryItem: RepositoryItem?,
) {
    LaunchedEffect(Unit, block = {
        if (repositoryItem != null) {
            viewModel.getPullRequestCommits(
                viewModel.vscPullRequestDetail.value._links.commits.href,
                repositoryItem.token
            )
        }
    })
    viewModel.title.value = "Commits"
    val maxScreenWidth = LocalConfiguration.current.screenWidthDp.times(0.7)
    LazyColumn(Modifier.fillMaxWidth()) {
        items(viewModel.vscPullRequestDetailCommits) { commit ->
            Box(Modifier.padding(8.dp)) {
                Row() {
                    Column(modifier = Modifier.widthIn(0.dp, maxScreenWidth.dp)) {
                        Text(
                            text = commit.commit.message,
                            color = MaterialTheme.colors.primary,
                            style = MaterialTheme.typography.h1,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                        Text(
                            text = commit.commit.author.name,
                            color = MaterialTheme.colors.primaryVariant,
                            style = MaterialTheme.typography.subtitle2
                        )
                    }
                    Spacer(modifier = Modifier.weight(1f))
                    Text(
                        text = viewModel.calcUpdateDuration(
                            commit.commit.author.date,
                        ),
                        color = MaterialTheme.colors.primaryVariant,
                        style = MaterialTheme.typography.subtitle2
                    )
                }
            }
        }
    }
}