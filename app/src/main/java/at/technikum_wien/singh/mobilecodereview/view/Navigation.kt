package at.technikum_wien.singh.mobilecodereview.view

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import at.technikum_wien.singh.mobilecodereview.data.RepositoryItem
import at.technikum_wien.singh.mobilecodereview.data.vscModules.VSCFile
import at.technikum_wien.singh.mobilecodereview.data.vscModules.VSCPullrequest
import at.technikum_wien.singh.mobilecodereview.viewmodel.CodeReviewViewModel

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
                    if (navBackStackEntry?.destination?.route.equals("pullRequest_detail_screen/{repositoryLink}"))
                        Text(
                            text = viewModel.vscPullRequestDetail.value.head.repo.full_name ?: "",
                            color = MaterialTheme.colors.secondary,
                            style = MaterialTheme.typography.subtitle2
                        )
                }
                Spacer(modifier = Modifier.weight(1f))
                if (navBackStackEntry?.destination?.route.equals("main_screen")) {
                    IconButton(onClick = { navController.navigate(Screen.SettingsScreen.route) }) {
                        Icon(
                            imageVector = Icons.Filled.Settings,
                            tint = MaterialTheme.colors.background,
                            contentDescription = "Settings"
                        )
                    }
                }
            }
        },
        content = {
            NavHost(navController = navController, startDestination = Screen.MainScreen.route) {
                composable(route = Screen.MainScreen.route) {
                    MainScreen(navController = navController, viewModel = viewModel)
                }
                composable(route = Screen.RepositoriesScreen.route) {
                    RepositoriesScreen(//navController = navController,
                        viewModel = viewModel
                    )
                }
                composable(route = Screen.PullRequestScreen.route) {
                    PullRequestScreen(navController = navController, viewModel = viewModel)
                }
                composable(route = Screen.PullRequestDetailScreen.route + "/{repositoryLink}",
                    arguments = listOf(
                        navArgument(name = "repositoryLink") {
                            type = NavType.StringType
                        }
                    )) {
                    val link = it.arguments?.getString("repositoryLink")
                    //link format item=##&number=##
                    val itemIndex =
                        link?.substring(link.lastIndexOf("item=") + 5, link.indexOf("&number="))
                    val numberIndex =
                        link?.substring(link.lastIndexOf("&number=") + 8)
                    val repoItem: RepositoryItem? =
                        viewModel.repositoryItems.value?.find { item -> item.id == itemIndex?.toLong() }
                    if (repoItem != null) {
                        if (viewModel.refreshApiCallsDetails.value) {
                            viewModel.getPullRequest(
                                "${repoItem.url}/pulls/$numberIndex",
                                repoItem.token
                            )
                            viewModel.refreshApiCallsDetails.value = false
                        }
                        PullRequestDetailScreen(
                            navController = navController,
                            viewModel = viewModel,
                            repositoryItem = repoItem
                        )
                    }
                }
                composable(route = Screen.PullRequestDetailStaticAnalyseScreen.route) {
                    PullRequestDetailStaticAnalyseScreen(
                        navController = navController,
                        viewModel = viewModel
                    )
                }
                composable(route = Screen.SettingsScreen.route) {
                    SettingsScreen(navController = navController, viewModel = viewModel)
                }
                composable(
                    route = Screen.PullRequestDetailCommitsScreen.route + "/{repositoryIndex}",
                    arguments = listOf(
                        navArgument(name = "repositoryIndex") {
                            type = NavType.LongType
                        })
                ) {
                    val index = it.arguments?.getLong("repositoryIndex")
                    val repoItem: RepositoryItem? =
                        viewModel.repositoryItems.value?.find { item -> item.id == index }
                    PullRequestDetailCommitsScreen(
                        // navController = navController,
                        viewModel = viewModel,
                        repositoryItem = repoItem
                    )
                }
                composable(
                    route = Screen.PullRequestDetailFilesScreen.route + "/{repositoryIndex}",
                    arguments = listOf(
                        navArgument(name = "repositoryIndex") {
                            type = NavType.LongType
                        })
                ) {
                    val index = it.arguments?.getLong("repositoryIndex")
                    val repoItem: RepositoryItem? =
                        viewModel.repositoryItems.value?.find { item -> item.id == index }
                    PullRequestDetailFilesScreen(
                        navController = navController,
                        viewModel = viewModel,
                        repositoryItem = repoItem
                    )
                }
                composable(
                    route = Screen.PullRequestDetailFilesDetailScreen.route + "/{fileName}",
                    arguments = listOf(
                        navArgument(name = "fileName") {
                            type = NavType.StringType
                        })
                ) {
                    val index = it.arguments?.getString("fileName")
                    val vscFile =
                        viewModel.vscPullRequestDetailFiles.find { item -> item.filename == index }
                    PullRequestDetailFilesScreenPager(
                        // navController = navController,
                        viewModel = viewModel,
                        file = vscFile
                    )

                }
                composable(
                    route = Screen.PullRequestDetailCommentsScreen.route + "/{repositoryIndex}",
                    arguments = listOf(
                        navArgument(name = "repositoryIndex") {
                            type = NavType.LongType
                        })
                ) {
                    val index = it.arguments?.getLong("repositoryIndex")
                    val repoItem: RepositoryItem? =
                        viewModel.repositoryItems.value?.find { item -> item.id == index }
                    PullRequestDetailCommentsScreen(
                        viewModel = viewModel,
                        repositoryItem = repoItem
                    )
                }
                composable(
                    route = Screen.PullRequestDetailReviewsScreen.route + "/{repositoryIndex}",
                    arguments = listOf(
                        navArgument(name = "repositoryIndex") {
                            type = NavType.LongType
                        })
                ) {
                    val index = it.arguments?.getLong("repositoryIndex")
                    val repoItem: RepositoryItem? =
                        viewModel.repositoryItems.value?.find { item -> item.id == index }
                    PullRequestDetailReviewScreen(
                        navController = navController,
                        viewModel = viewModel,
                        repositoryItem = repoItem
                    )
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
                    style = MaterialTheme.typography.subtitle2
                )
                Spacer(modifier = Modifier.weight(1f))
            }
        }
    )
}
