package at.technikum_wien.singh.mobilecodereview.view

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import at.technikum_wien.singh.mobilecodereview.R
import at.technikum_wien.singh.mobilecodereview.data.RepositoryItem
import at.technikum_wien.singh.mobilecodereview.viewmodel.CodeReviewViewModel

@Composable
fun RepositoriesScreen(navController: NavController?, viewModel: CodeReviewViewModel) {
    viewModel.title.value = stringResource(R.string.home_repository)
    val repositoryItems by viewModel.repositoryItems.observeAsState()
    LazyColumn(Modifier.fillMaxWidth()) {
        itemsIndexed(repositoryItems ?: listOf()) { index, repositoryItem ->
            RepositoryItemRow(
                navController = navController,
                index = index,
                repositoryItem = repositoryItem,
                viewModel = viewModel
            )
        }
    }
}

@Composable
fun RepositoryItemRow(
    navController: NavController?,
    index: Int,
    repositoryItem: RepositoryItem,
    viewModel: CodeReviewViewModel
) {
    Text(text = repositoryItem.url)
}
