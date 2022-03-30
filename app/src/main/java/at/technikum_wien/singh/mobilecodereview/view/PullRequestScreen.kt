package at.technikum_wien.singh.mobilecodereview.view

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import at.technikum_wien.singh.mobilecodereview.R
import at.technikum_wien.singh.mobilecodereview.data.vscModules.VSCPullrequest
import at.technikum_wien.singh.mobilecodereview.viewmodel.CodeReviewViewModel
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import java.util.*

@Composable
fun PullRequestScreen(navController: NavController, viewModel: CodeReviewViewModel) {
    val isRefreshing by viewModel.isRefreshing.collectAsState()
    // viewModel.refreshApiCallsDetails.value = true
    LaunchedEffect(Unit, block = {
        if (viewModel.refreshApiCalls.value) {
            viewModel.getPullRequestList()
            viewModel.getGithubList()
            viewModel.refreshApiCalls.value = false
        }
    })
    viewModel.title.value = stringResource(R.string.home_pull_request)
    Column {
        if (viewModel.errorMessage.isNotEmpty())
            Text(text = viewModel.errorMessage)
        SwipeRefresh(
            state = rememberSwipeRefreshState(isRefreshing),
            onRefresh = { viewModel.getPullRequestList() },
        ) {
            LazyColumn(Modifier.fillMaxWidth()) {
                items(viewModel.vscPullRequestList) { pullRequestItem ->
                    PullRequestItemRow(
                        pullRequestItem = pullRequestItem,
                        viewModel = viewModel,
                        navController = navController
                    )
                }
            }
        }
    }
}

@Composable
fun PullRequestItemRow(
    pullRequestItem: VSCPullrequest,
    viewModel: CodeReviewViewModel,
    navController: NavController
) {
    Button(
        onClick = {
            viewModel.refreshApiCallsDetails.value = true
            navController.navigate(Screen.PullRequestDetailScreen.route + "/url=${pullRequestItem.repoId}&number=${pullRequestItem.number}")
        },
        colors = ButtonDefaults.buttonColors(backgroundColor = Color.White)
    ) {
        Row(Modifier.fillMaxSize()) {
            Spacer(modifier = Modifier.width(5.dp))
            Box(Modifier.fillMaxSize()) {

                Column(Modifier.fillMaxSize()) {
                    Row {
                        Text(
                            text = pullRequestItem.head.repo.full_name ?: "",
                            color = MaterialTheme.colors.primaryVariant,
                            style = MaterialTheme.typography.subtitle1
                        )
                        Spacer(modifier = Modifier.weight(1f))
                        Text(
                            text = viewModel.calcUpdateDuration(
                                pullRequestItem.updated_at ?: Date()
                            ),
                            color = MaterialTheme.colors.primaryVariant,
                            style = MaterialTheme.typography.subtitle1
                        )
                    }
                    Text(
                        text = pullRequestItem.title ?: "",
                        color = MaterialTheme.colors.primary,
                        style = MaterialTheme.typography.h1
                    )
                }
            }
            Spacer(modifier = Modifier.width(16.dp))
        }
    }
}
