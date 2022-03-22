package at.technikum_wien.singh.mobilecodereview.view

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import at.technikum_wien.singh.mobilecodereview.ui.theme.Black
import at.technikum_wien.singh.mobilecodereview.viewmodel.CodeReviewViewModel

@Composable
fun PullRequestDetailCommentsScreen(viewModel: CodeReviewViewModel) {
    viewModel.title.value = "Comments"
    Box(modifier = Modifier.padding(8.dp, 4.dp)) {
        if (viewModel.vscPullRequestDetailComments.isEmpty())
            Text(
                text = "No comments found...",
                color = Black,
                textAlign = TextAlign.Right,
                style = MaterialTheme.typography.h1,
            )
        LazyColumn(modifier = Modifier.fillMaxWidth()) {
            items(viewModel.vscPullRequestDetailComments) { comment ->
                PullRequestComments(viewModel = viewModel, comment = comment)
            }
        }
    }
}