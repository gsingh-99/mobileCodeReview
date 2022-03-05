package at.technikum_wien.singh.mobilecodereview.view

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import at.technikum_wien.singh.mobilecodereview.R
import at.technikum_wien.singh.mobilecodereview.ui.theme.MobileCodeReviewTheme

@Composable
fun MainScreen(navController: NavController?, setTitle:(String) -> Unit) {
        Column {
            Button(
                onClick = { navController?.navigate(Screen.RepositoriesScreen.route) },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(backgroundColor = Color.White)
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
            Button(
                onClick = { navController?.navigate(Screen.PullRequestScreen.route) },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(backgroundColor = Color.White)
            ) {
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
}

@Preview(showBackground = true)
@Composable
fun MainScreenPreview() {
    MobileCodeReviewTheme {
        MainScreen(null)
    }
}