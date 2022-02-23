package at.technikum_wien.singh.mobilecodereview.view

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import at.technikum_wien.singh.mobilecodereview.ui.theme.MobileCodeReviewTheme

const val name = "User"
@Composable
fun Navigation() {
    Text(text = "Hello $name")
}
@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    MobileCodeReviewTheme {
        Navigation()
    }
}