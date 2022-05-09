package at.technikum_wien.singh.mobilecodereview.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import at.technikum_wien.singh.mobilecodereview.viewmodel.CodeReviewViewModel

@Composable
fun SettingsScreen(
    navController: NavController,
    viewModel: CodeReviewViewModel
) {
    viewModel.title.value = "Settings"
    Column(Modifier.padding(12.dp, 0.dp)) {
        TextField(
            value = viewModel.tfSonarQubeUrl,
            onValueChange = { viewModel.updateSonarQubeAddress(it) },
            textStyle = MaterialTheme.typography.subtitle1,
            label = {
                Text(
                    text = "SonarQube Server Address",
                    color = MaterialTheme.colors.primary
                )
            },
            placeholder = { Text(text = "SonarQube Server Address", color = Color.LightGray) },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )
        TextField(
            value = viewModel.tfGitHubUrl,
            onValueChange = { viewModel.updateGithubApiUrl(it) },
            textStyle = MaterialTheme.typography.subtitle1,
            label = {
                Text(
                    text = "GitHub API Url",
                    color = MaterialTheme.colors.primary
                )
            },
            placeholder = { Text(text = "GitHub API Url", color = Color.LightGray) },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )
        TextField(
            value = viewModel.tfGitLabUrl,
            onValueChange = { viewModel.updateGitlabApiUrl(it) },
            textStyle = MaterialTheme.typography.subtitle1,
            label = {
                Text(
                    text = "GitLab API Url",
                    color = MaterialTheme.colors.primary
                )
            },
            placeholder = { Text(text = "GitLab API Url", color = Color.LightGray) },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )
    }
}