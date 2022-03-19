package at.technikum_wien.singh.mobilecodereview.view

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import at.technikum_wien.singh.mobilecodereview.R
import at.technikum_wien.singh.mobilecodereview.data.vscModules.VSCRepositoryItem
import at.technikum_wien.singh.mobilecodereview.viewmodel.CodeReviewViewModel
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState

@Composable
fun RepositoriesScreen(navController: NavController?, viewModel: CodeReviewViewModel) {
    val isRefreshing by viewModel.isRefreshing.collectAsState()
    LaunchedEffect(Unit, block = {
        if (viewModel.refreshApiCalls.value) {
            viewModel.getPullRequestList()
            viewModel.getGithubList()
            viewModel.refreshApiCalls.value = false
        }
    })
    viewModel.title.value = stringResource(R.string.home_repository)
    Text(text = viewModel.errorMessage)
    Column() {
        // Text(text = viewModel.errorMessage)
        Spacer(modifier = Modifier.width(24.dp))
        SwipeRefresh(
            state = rememberSwipeRefreshState(isRefreshing),
            onRefresh = { viewModel.getPullRequestList() },
        ) {
            LazyColumn(Modifier.fillMaxWidth()) {

                items(viewModel.VSCRepositoryItemList) { repositoryItem ->
                    RepositoryItemRow(
                        repositoryItemItem = repositoryItem,
                        viewModel = viewModel
                    )
                    Spacer(modifier = Modifier.width(16.dp))
                }
            }
        }
    }
}

@Composable
fun RepositoryItemRow(
    repositoryItemItem: VSCRepositoryItem,
    viewModel: CodeReviewViewModel
) {
    Button(
        onClick = {
            viewModel.openGenericDialog.value = true
            viewModel.openGenericDialogMessage.value =
                "This is a prototype for code reviewing. Therefore, browsing the repository is not in the development scope. Please use another front-end to check your repository."
        },
        colors = ButtonDefaults.buttonColors(backgroundColor = Color.White)
    ) {
        Row(Modifier.fillMaxSize()) {
            Spacer(modifier = Modifier.width(16.dp))
            Box(Modifier.fillMaxSize()) {

                Column(Modifier.fillMaxSize()) {
                    Text(
                        text = "" + repositoryItemItem.owner.login,
                        color = MaterialTheme.colors.primaryVariant,
                        style = MaterialTheme.typography.subtitle1
                    )
                    Text(
                        text = repositoryItemItem.name ?: "",
                        color = MaterialTheme.colors.primary,
                        style = MaterialTheme.typography.h1
                    )
                }
            }
            Spacer(modifier = Modifier.width(16.dp))
        }
    }
}

