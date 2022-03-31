package at.technikum_wien.singh.mobilecodereview.view

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import at.technikum_wien.singh.mobilecodereview.R
import at.technikum_wien.singh.mobilecodereview.ui.theme.MobileCodeReviewTheme
import at.technikum_wien.singh.mobilecodereview.viewmodel.CodeReviewViewModel

@Composable
fun MainScreen(navController: NavController, viewModel: CodeReviewViewModel) {
    viewModel.title.value = stringResource(R.string.home_home)
    viewModel.repositoryItems.observeAsState()
    Box(Modifier.fillMaxHeight()) {
        Column {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp)
                    .clickable { navController.navigate(Screen.RepositoriesScreen.route) }
            ) {
                Icon(
                    painter = painterResource(R.drawable.outline_storage_24),
                    contentDescription = "Repository"
                )
                Spacer(modifier = Modifier.width(12.dp))
                Text(
                    text = stringResource(R.string.home_repository),
                    color = MaterialTheme.colors.primary,
                    style = MaterialTheme.typography.button
                )
                Spacer(modifier = Modifier.weight(1f))
            }
            Row(modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp)
                .clickable {
                    navController.navigate(Screen.PullRequestScreen.route)
                }) {
                Icon(
                    painter = painterResource(R.drawable.ic_pull_request),
                    modifier = Modifier.size(24.dp),
                    contentDescription = "Repository"
                )
                Spacer(modifier = Modifier.width(12.dp))
                Text(
                    text = stringResource(R.string.home_pull_request),
                    color = MaterialTheme.colors.primary,
                    style = MaterialTheme.typography.button
                )
                Spacer(modifier = Modifier.weight(1f))
            }
        }
        FloatingActionButton(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(12.dp, 35.dp),
            onClick = { viewModel.openAddNewRepositoryDialog.value = true }) {
            Icon(
                Icons.Filled.Add,
                contentDescription = stringResource(R.string.home_add_repository_FAB),
                modifier = Modifier.size(32.dp),
                tint = MaterialTheme.colors.primary
            )
        }
    }
    if (viewModel.openAddNewRepositoryDialog.value)
        AddRepositoryDialog(viewModel = viewModel)

}

@Preview(showBackground = true)
@Composable
fun MainScreenPreview() {
    MobileCodeReviewTheme {
        Row {
            Text(text = "test")
        }
    }
}