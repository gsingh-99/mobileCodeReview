package at.technikum_wien.singh.mobilecodereview.view

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import at.technikum_wien.singh.mobilecodereview.data.RepositoryItem
import at.technikum_wien.singh.mobilecodereview.viewmodel.CodeReviewViewModel

@Composable
fun PullRequestDetailFilesScreen(
    navController: NavController,
    viewModel: CodeReviewViewModel,
    repositoryItem: RepositoryItem?,
) {
    LaunchedEffect(Unit, block = {
        if (repositoryItem != null) {
            viewModel.getPullRequestFiles(
                "${viewModel.vscPullRequestDetail.value._links.self.href}/files",
                repositoryItem.token
            )
        }
    })
    viewModel.title.value = "Files"
    val maxScreenWidth = LocalConfiguration.current.screenWidthDp.times(0.7)
    val maxScreenHeight = LocalConfiguration.current.screenHeightDp.times(0.85)
    Column {
        Box(
            modifier = Modifier
                .heightIn(0.dp, maxScreenHeight.dp)
                .fillMaxHeight()
                .fillMaxWidth()
        ) {
            LazyColumn(Modifier.fillMaxWidth()) {
                items(viewModel.vscPullRequestDetailFiles) { file ->
                    Box(Modifier.padding(8.dp)) {
                        Row() {
                            Column(modifier = Modifier
                                .widthIn(0.dp, maxScreenWidth.dp)
                                .clickable { navController.navigate(Screen.PullRequestDetailFilesDetailScreen.route + "/${file.filename}") }) {
                                Text(
                                    text = file.filename,
                                    color = MaterialTheme.colors.primary,
                                    style = MaterialTheme.typography.h5,
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis
                                )
                            }
                            Spacer(modifier = Modifier.weight(1f))
                              Checkbox(checked = false, onCheckedChange = {},  colors = CheckboxDefaults.colors(
                                  Color.Black, Color.Black),)
                        }
                    }
                }
            }

            ExtendedFloatingActionButton(
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(8.dp),
                onClick = { viewModel.openAddNewReviewDialog.value = true },
                backgroundColor = MaterialTheme.colors.primary,
                text = {
                    Text(
                        text = "Write a review",
                        style = MaterialTheme.typography.subtitle1,
                        color = MaterialTheme.colors.secondary,
                    )
                }
            )
        }
    }
    if (viewModel.openAddNewReviewDialog.value)
        AddReviewDialog(viewModel = viewModel, repositoryItem = repositoryItem)
}