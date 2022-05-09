package at.technikum_wien.singh.mobilecodereview.view

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import at.technikum_wien.singh.mobilecodereview.viewmodel.CodeReviewViewModel

@Composable
fun PullRequestDetailStaticAnalyseScreen(
    navController: NavController,
    viewModel: CodeReviewViewModel
) {
    viewModel.title.value = "Static Code Analysis"
    val scrollState = rememberScrollState()
    val maxScreenHeight = LocalConfiguration.current.screenHeightDp.times(0.868)
    Column(
        Modifier
            .heightIn(0.dp, maxScreenHeight.dp)
            .verticalScroll(scrollState)
    ) {
        Row() {
            Spacer(modifier = Modifier.width(36.dp))
            Column() {
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    text = "Total issues: 5",
                    style = MaterialTheme.typography.h5,
                    color = MaterialTheme.colors.primary
                )
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    text = "Bugs found: 2",
                    style = MaterialTheme.typography.subtitle2,
                    color = MaterialTheme.colors.primaryVariant
                )
                Text(
                    text = "Code smell found found: 3",
                    style = MaterialTheme.typography.subtitle2,
                    color = MaterialTheme.colors.primaryVariant
                )
                Text(
                    text = "Vulnerability found: 0",
                    style = MaterialTheme.typography.subtitle2,
                    color = MaterialTheme.colors.primaryVariant
                )
            }
            Spacer(modifier = Modifier.weight(1f))
        }
        Spacer(modifier = Modifier.height(24.dp))
        Row() {
            Spacer(modifier = Modifier.width(24.dp))
            Column() {
                IssueBox(
                    "Code Smell",
                    "Add a 'protected' constructor or the 'static' keyword to the class declaration.",
                    "9-9",
                    "alert.js"
                )
                IssueBox(
                    "Bug",
                    "Return statement will always return 'false'.",
                    "23-23",
                    "navigation.js"
                )
                IssueBox(
                    "Code Smell",
                    "Add a 'protected' constructor or the 'static' keyword to the class declaration.",
                    "9-9",
                    "alert.js"
                )
                IssueBox(
                    "Code Smell",
                    "Add a 'protected' constructor or the 'static' keyword to the class declaration.",
                    "9-9",
                    "alert.js"
                )
                IssueBox(
                    "Bug",
                    "Return statement will always return 'false'..",
                    "23-23",
                    "navigation.js"
                )
            }
            Spacer(modifier = Modifier.weight(1f))
        }
    }
}

@Composable
fun IssueBox(type: String, message: String, lines: String, component: String) {
    Box {
        Column() {
            Text(
                text = type,
                style = MaterialTheme.typography.subtitle2,
                color = MaterialTheme.colors.primaryVariant
            )
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                text = message,
                style = MaterialTheme.typography.subtitle1,
                color = MaterialTheme.colors.primary
            )
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                text = "[Line] $lines",
                style = MaterialTheme.typography.subtitle2,
                color = MaterialTheme.colors.primaryVariant
            )
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                text = "[Component] $component",
                style = MaterialTheme.typography.subtitle2,
                color = MaterialTheme.colors.primaryVariant
            )
            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}