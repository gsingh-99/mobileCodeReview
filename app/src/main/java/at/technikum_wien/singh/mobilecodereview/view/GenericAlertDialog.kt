package at.technikum_wien.singh.mobilecodereview.view

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import at.technikum_wien.singh.mobilecodereview.viewmodel.CodeReviewViewModel

@Composable
fun GenericAlertDialog(viewModel: CodeReviewViewModel) {
    if (viewModel.openGenericDialog.value) {
        AlertDialog(
            backgroundColor = MaterialTheme.colors.secondary,
            onDismissRequest = { viewModel.openGenericDialog.value = false },
            title = { Text(text = "Info") },
            text = { Text(text = viewModel.openGenericDialogMessage.value) },
            confirmButton = {
                Button(
                    onClick = { viewModel.openGenericDialog.value = false },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.primary)
                ) {
                    Text(
                        text = "Close",
                        style = MaterialTheme.typography.subtitle1,
                        color = MaterialTheme.colors.secondary,
                    )
                }
            })
    }
}