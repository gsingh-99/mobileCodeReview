package at.technikum_wien.singh.mobilecodereview.view

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import at.technikum_wien.singh.mobilecodereview.R
import at.technikum_wien.singh.mobilecodereview.ui.theme.MobileCodeReviewTheme
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import at.technikum_wien.singh.mobilecodereview.data.RepositoryItem
import at.technikum_wien.singh.mobilecodereview.data.vscModules.VSCPullrequest
import at.technikum_wien.singh.mobilecodereview.viewmodel.CodeReviewViewModel
import at.technikum_wien.singh.mobilecodereview.viewmodel.CodeReviewViewModelFactory

const val name = "User"

@Composable
fun Navigation(viewModel: CodeReviewViewModel) {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    Scaffold(
        topBar = {
            TopAppBar(backgroundColor = MaterialTheme.colors.primary) {
                if (navController.previousBackStackEntry != null) {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            tint = MaterialTheme.colors.background,
                            contentDescription = "Back"
                        )
                    }
                } else {
                    Spacer(modifier = Modifier.width(48.dp))
                }
                Column {
                    Text(
                        text = viewModel.title.value,
                        color = MaterialTheme.colors.secondary,
                        style = MaterialTheme.typography.body1
                    )
                    if (navBackStackEntry?.destination?.route.equals("pullRequest_detail_screen/{repositoryIndex}"))
                        Text(
                            text = viewModel.VSCPullrequestDetail.value.head.repo.full_name ?: "",
                            color = MaterialTheme.colors.secondary,
                            style = MaterialTheme.typography.subtitle1
                        )
                }
            }
        },
        content = {
            NavHost(navController = navController, startDestination = Screen.MainScreen.route) {
                composable(route = Screen.MainScreen.route) {
                    MainScreen(navController = navController, viewModel = viewModel)
                }
                composable(route = Screen.RepositoriesScreen.route) {
                    RepositoriesScreen(navController = navController, viewModel = viewModel)
                }
                composable(route = Screen.PullRequestScreen.route) {
                    PullRequestScreen(navController = navController, viewModel = viewModel)
                }
                composable(route = Screen.PullRequestDetailScreen.route + "/{repositoryIndex}",
                    arguments = listOf(
                        navArgument(name = "repositoryIndex") {
                            type = NavType.StringType
                        }
                    )) {
                    var repoItem: RepositoryItem? = null
                    var pullRequestItem: VSCPullrequest? = null
                    val link = it.arguments?.getString("repositoryIndex")
                    //link format item=##&number=##
                    val itemIndex =
                        link?.substring(link.lastIndexOf("item=") + 5, link.indexOf("&number="))
                    val numberIndex =
                        link?.substring(link.lastIndexOf("&number=") + 8)
                    repoItem =
                        viewModel.repositoryItems.value?.find { item -> item.id == itemIndex?.toLong() }
                    if (repoItem != null) {
                        viewModel.getPullRequest(
                            repoItem.url + "/pulls/${numberIndex}",
                            repoItem.token
                        )
                        PullRequestDetailScreen(
                            navController = navController,
                            viewModel = viewModel,
                            repositoryItem = repoItem
                        )
                    }
                }
            }
            GenericAlertDialog(viewModel = viewModel)
        },
        bottomBar = {
            BottomAppBar(
                backgroundColor = MaterialTheme.colors.secondary, modifier = Modifier
                    .height(25.dp)
                    .fillMaxWidth()
            ) {
                Spacer(modifier = Modifier.weight(1f))
                Text(
                    text = "Prototype by Gurparkash Singh 2022",
                    style = MaterialTheme.typography.subtitle1
                )
                Spacer(modifier = Modifier.weight(1f))
            }
        }
    )
}
