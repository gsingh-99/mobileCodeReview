package at.technikum_wien.singh.mobilecodereview.view

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import at.technikum_wien.singh.mobilecodereview.ui.theme.TransparentBlack
import at.technikum_wien.singh.mobilecodereview.viewmodel.CodeReviewViewModel

@Composable
fun AddReviewDialog(viewModel: CodeReviewViewModel) {
    val maxScreenWidth = LocalConfiguration.current.screenWidthDp.times(0.82)
    val maxScreenHeight = LocalConfiguration.current.screenHeightDp.times(0.78)
    Box(
        Modifier
            .background(TransparentBlack)
            .fillMaxSize()
            .clickable { viewModel.openAddNewReviewDialog.value = false }) {
        Box(
            Modifier
                .background(MaterialTheme.colors.secondary)
                .align(Alignment.Center)
                .heightIn(0.dp, maxScreenHeight.dp)
                .widthIn(0.dp, maxScreenWidth.dp)
                .fillMaxSize()

        ) {
            Column() {
                Text(text = "Add new review...")
                OutlinedTextField(
                    value = viewModel.tfAddReviewText,
                    onValueChange = { viewModel.tfAddReviewText = it },
                    label = {
                        Text(
                            text = "Review...",
                            color = MaterialTheme.colors.primary
                        )
                    },
                    placeholder = { Text(text = "Review...", color = Color.LightGray) },
                    maxLines = 10
                )
                Spacer(modifier = Modifier.weight(1f))
                Button(
                    onClick = { viewModel.addRepository() },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.primary)
                ) {
                    Text(
                        text = "Confirm",
                        style = MaterialTheme.typography.subtitle1,
                        color = MaterialTheme.colors.secondary,
                    )
                }
            }
        }
    }
}
