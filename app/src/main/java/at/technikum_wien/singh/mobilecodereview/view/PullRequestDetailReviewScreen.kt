package at.technikum_wien.singh.mobilecodereview.view

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import at.technikum_wien.singh.mobilecodereview.R
import at.technikum_wien.singh.mobilecodereview.data.RepositoryItem
import at.technikum_wien.singh.mobilecodereview.viewmodel.CodeReviewViewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest

@Composable
fun PullRequestDetailReviewScreen(
    navController: NavController,
    viewModel: CodeReviewViewModel,
    repositoryItem: RepositoryItem?,
) {
    LaunchedEffect(Unit, block = {
        if (repositoryItem != null) {
            viewModel.getPullRequestReviews(
                "${viewModel.vscPullRequestDetail.value._links.self.href}/reviews",
                repositoryItem.token
            )
        }
    })
    viewModel.title.value = "Reviews"
    val maxScreenHeight = LocalConfiguration.current.screenHeightDp.times(0.89)

    LazyColumn(
        Modifier
            .fillMaxWidth()
            .heightIn(0.dp, maxScreenHeight.dp)
    ) {
        items(viewModel.vscPullRequestDetailReviews) { review ->
            Box(Modifier.padding(6.dp, 6.dp)) {
                Column {
                    Row {
                        AsyncImage(
                            model = ImageRequest.Builder(LocalContext.current)
                                .data(review.user.avatar_url)
                                .crossfade(true)
                                .build(),
                            placeholder = painterResource(R.drawable.ic_baseline_account_circle_24),
                            contentDescription = "user_pic",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .clip(CircleShape)
                                .size(24.dp)
                        )
                        Text(
                            text = " ${review.user.login} ",
                            color = Color.Black,
                            style = MaterialTheme.typography.subtitle1
                        )
                        Text(
                            text = viewModel.parseReviewStateText(review.state),
                            color = viewModel.parseReviewStateColor(review.state),
                            style = MaterialTheme.typography.subtitle1
                        )
                        Spacer(modifier = Modifier.weight(1f))
                        if (review.submitted_at != null)
                            Text(
                                text = viewModel.calcUpdateDuration(review.submitted_at),
                                color = MaterialTheme.colors.primaryVariant,
                                style = MaterialTheme.typography.subtitle2
                            )
                    }
                    Row(Modifier.padding(0.dp, 0.dp)) {
                        Text(
                            text = review.body,
                            color = Color.Black,
                            style = MaterialTheme.typography.h6
                        )
                    }
                }
            }
        }
    }
}