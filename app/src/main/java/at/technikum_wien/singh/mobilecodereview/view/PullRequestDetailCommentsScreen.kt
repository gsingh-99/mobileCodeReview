package at.technikum_wien.singh.mobilecodereview.view

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.ExtendedFloatingActionButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import at.technikum_wien.singh.mobilecodereview.data.RepositoryItem
import at.technikum_wien.singh.mobilecodereview.ui.theme.Black
import at.technikum_wien.singh.mobilecodereview.viewmodel.CodeReviewViewModel

@Composable
fun PullRequestDetailCommentsScreen(
    viewModel: CodeReviewViewModel,
    repositoryItem: RepositoryItem?
) {
    viewModel.title.value = "Comments"
    val maxScreenHeight = LocalConfiguration.current.screenHeightDp.times(0.85)
    Box(
        modifier = Modifier
            .padding(8.dp, 4.dp)
            .heightIn(0.dp, maxScreenHeight.dp)
            .fillMaxSize()
    ) {
        if (viewModel.vscPullRequestDetailComments.isEmpty())
            Text(
                text = "No comments found...",
                color = Black,
                textAlign = TextAlign.Right,
                style = MaterialTheme.typography.h5,
            )
        LazyColumn(modifier = Modifier.fillMaxWidth()) {
            items(viewModel.vscPullRequestDetailComments) { comment ->
                PullRequestComments(viewModel = viewModel, comment = comment)
            }
        }
        ExtendedFloatingActionButton(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(8.dp),
            onClick = { viewModel.openAddNewCommentsDialog.value = true },
            backgroundColor = MaterialTheme.colors.primary,
            text = {
                Text(
                    text = "Write a comment",
                    style = MaterialTheme.typography.subtitle1,
                    color = MaterialTheme.colors.secondary,
                )
            }
        )
    }
    if (viewModel.openAddNewCommentsDialog.value)
        AddCommentDialog(viewModel, repositoryItem)

}