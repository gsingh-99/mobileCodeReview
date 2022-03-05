package at.technikum_wien.singh.mobilecodereview.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import at.technikum_wien.singh.mobilecodereview.R
import at.technikum_wien.singh.mobilecodereview.ui.theme.MobileCodeReviewTheme

@Composable
fun MainScreen() {
    Scaffold(topBar = {
        TopAppBar() {
            Text(text = stringResource(R.string.home_home), style = MaterialTheme.typography.body1)
        }
    }) {
        Column {
            Button(onClick = { /*TODO*/ },
                modifier=Modifier.fillMaxWidth(),
                colors=ButtonDefaults.buttonColors(backgroundColor = Color.White)) {
                    Icon(Icons.Filled.Menu, contentDescription = "Repository")
                Spacer(modifier = Modifier.width(12.dp))
                Text(text = stringResource(R.string.home_repository), color=MaterialTheme.colors.primary, style = MaterialTheme.typography.button)
                Spacer(modifier = Modifier.weight(1f))
            }
            Button(onClick = { /*TODO*/ },
                modifier=Modifier.fillMaxWidth(),
                colors=ButtonDefaults.buttonColors(backgroundColor = Color.White)) {
                Text(text = stringResource(R.string.home_pull_request), color=MaterialTheme.colors.primary, style = MaterialTheme.typography.button)
                Spacer(modifier = Modifier.weight(1f))
            }
        }
    }
}
@Preview(showBackground = true)
@Composable
fun MainScreenPreview() {
    MobileCodeReviewTheme {
        MainScreen()
    }
}