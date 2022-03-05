package at.technikum_wien.singh.mobilecodereview.view

import android.util.Log
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import at.technikum_wien.singh.mobilecodereview.R
import at.technikum_wien.singh.mobilecodereview.ui.theme.MobileCodeReviewTheme
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController

const val name = "User"

@Composable
fun Navigation() {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val baseTitle =stringResource(R.string.home_home)
    val (title, setTitle) = rememberSaveable  { mutableStateOf(baseTitle) }
    Scaffold(
        topBar = {
            TopAppBar(backgroundColor = MaterialTheme.colors.primary) {
                Spacer(modifier = Modifier.width(50.dp))
                Text(
                    text = title,
                    color = MaterialTheme.colors.secondary,
                    style = MaterialTheme.typography.body1
                )
            }
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { /* ... */ }) {
                Icon(
                    Icons.Filled.Add,
                    contentDescription = stringResource(R.string.home_add_repository_FAB),
                    modifier = Modifier.size(32.dp),
                    tint = MaterialTheme.colors.primary
                )
            }
        }) {
        NavHost(navController = navController, startDestination = Screen.MainScreen.route) {
            composable(route = Screen.MainScreen.route) {
                MainScreen(navController = navController)
            }
            composable(route = Screen.RepositoriesScreen.route) {
                RepositoriesScreen(navController = navController)
            }
            composable(route = Screen.PullRequestScreen.route) {
                PullRequestScreen(navController = navController)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    MobileCodeReviewTheme {
        Navigation()
    }
}