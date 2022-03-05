package at.technikum_wien.singh.mobilecodereview.view

import androidx.compose.material.Button
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import at.technikum_wien.singh.mobilecodereview.R
import at.technikum_wien.singh.mobilecodereview.ui.theme.MobileCodeReviewTheme

const val name = "User"

@Composable
fun Navigation() {
    MainScreen()
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    MobileCodeReviewTheme {
        Navigation()
    }
}