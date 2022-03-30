package at.technikum_wien.singh.mobilecodereview.view

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import at.technikum_wien.singh.mobilecodereview.viewmodel.CodeReviewViewModel

@Composable
fun AddRepositoryDialog(viewModel: CodeReviewViewModel) {
    AlertDialog(
        backgroundColor = MaterialTheme.colors.secondary,
        onDismissRequest = { viewModel.openAddNewRepositoryDialog.value = false },
        title = { Text(text = "Add new repository...") },
        text = {
            Column {
                TextField(
                    value = viewModel.tfAddRepositoryUrl,
                    onValueChange = { viewModel.tfAddRepositoryUrl = it },
                    label = {
                        Text(
                            text = "Repository URL",
                            color = MaterialTheme.colors.primary
                        )
                    },
                    placeholder = { Text(text = "Repository URL", color = Color.LightGray) },
                    singleLine = true
                )
                TextField(
                    value = viewModel.tfAddRepositoryToken,
                    onValueChange = { viewModel.tfAddRepositoryToken = it },
                    label = { Text(text = "Token", color = MaterialTheme.colors.primary) },
                    placeholder = { Text(text = "Token", color = Color.LightGray) },
                    singleLine = true
                )
            }
        },
        confirmButton = {
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
        })
}
