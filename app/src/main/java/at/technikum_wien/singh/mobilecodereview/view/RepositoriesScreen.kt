package at.technikum_wien.singh.mobilecodereview.view

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import at.technikum_wien.singh.mobilecodereview.R
import at.technikum_wien.singh.mobilecodereview.data.vscModules.VSCRepositoryItem
import at.technikum_wien.singh.mobilecodereview.viewmodel.CodeReviewViewModel

@Composable
fun RepositoriesScreen(navController: NavController?, viewModel: CodeReviewViewModel) {
    LaunchedEffect(Unit, block = {
        viewModel.getGithubList()
    })
    viewModel.title.value = stringResource(R.string.home_repository)
    Column() {
       // Text(text = viewModel.errorMessage)
        Spacer(modifier = Modifier.width(24.dp))
        LazyColumn(Modifier.fillMaxWidth()) {

            items(viewModel.VSCRepositoryItemList) { repositoryItem ->
                RepositoryItemRow(
                    repositoryItemItem = repositoryItem,
                )
                Spacer(modifier = Modifier.width(16.dp))
            }
        }
    }
}

@Composable
fun RepositoryItemRow(
    repositoryItemItem: VSCRepositoryItem,
) {
    Row(Modifier.fillMaxSize()) {
        Spacer(modifier = Modifier.width(16.dp))
        Box(Modifier.fillMaxSize()) {

            Column(Modifier.fillMaxSize()) {
                Text(
                    text = "" + repositoryItemItem.owner.login,
                    color = MaterialTheme.colors.primary,
                    style = MaterialTheme.typography.subtitle1
                )
                Text(
                    text = repositoryItemItem.name ?: "",
                    color = MaterialTheme.colors.primary,
                    style = MaterialTheme.typography.button
                )
            }
        }
        Spacer(modifier = Modifier.width(16.dp))
    }

}

