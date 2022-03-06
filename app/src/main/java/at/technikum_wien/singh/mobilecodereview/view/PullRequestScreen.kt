package at.technikum_wien.singh.mobilecodereview.view

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import at.technikum_wien.singh.mobilecodereview.R
import at.technikum_wien.singh.mobilecodereview.viewmodel.CodeReviewViewModel

@Composable
fun PullRequestScreen(navController: NavController?, viewModel: CodeReviewViewModel) {
    viewModel.title.value = stringResource(R.string.home_pull_request)
}