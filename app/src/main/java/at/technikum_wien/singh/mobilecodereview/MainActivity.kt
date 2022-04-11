package at.technikum_wien.singh.mobilecodereview

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import at.technikum_wien.singh.mobilecodereview.data.RepositoryItemRepository
import at.technikum_wien.singh.mobilecodereview.ui.theme.MobileCodeReviewTheme
import at.technikum_wien.singh.mobilecodereview.view.Navigation
import at.technikum_wien.singh.mobilecodereview.view.PullRequestDetailScreen
import at.technikum_wien.singh.mobilecodereview.viewmodel.CodeReviewViewModel
import at.technikum_wien.singh.mobilecodereview.viewmodel.CodeReviewViewModelFactory

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MobileCodeReviewTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    var viewModel: CodeReviewViewModel = viewModel(
                        factory = CodeReviewViewModelFactory(
                            application,
                            RepositoryItemRepository(applicationContext)
                        )
                    )
                    /*Column(modifier = Modifier.fillMaxSize()) {


                    Text(text = "Beispiel Text body1", style = MaterialTheme.typography.body1)
                    Text(text = "Beispiel Text button", style = MaterialTheme.typography.button)
                    Text(text = "Beispiel Text h5", style = MaterialTheme.typography.h5)
                    Text(text = "Beispiel Text h6", style = MaterialTheme.typography.h6)
                    Text(text = "Beispiel Text subtitle1", style = MaterialTheme.typography.subtitle1)
                    Text(text = "Beispiel Text subtitle2", style = MaterialTheme.typography.subtitle2)
                    }*/
                    Navigation(viewModel = viewModel)
                }
            }
        }
    }
}

