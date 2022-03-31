package at.technikum_wien.singh.mobilecodereview.view

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.dp
import at.technikum_wien.singh.mobilecodereview.data.RepositoryItem
import at.technikum_wien.singh.mobilecodereview.ui.theme.TransparentBlack
import at.technikum_wien.singh.mobilecodereview.viewmodel.CodeReviewViewModel

@Composable
fun AddReviewDialog(viewModel: CodeReviewViewModel, repositoryItem: RepositoryItem?) {
    val maxScreenWidth = LocalConfiguration.current.screenWidthDp.times(0.82)
    val maxScreenHeight = LocalConfiguration.current.screenHeightDp.times(0.78)
    val maxScreenHeightTextArea = LocalConfiguration.current.screenHeightDp.times(0.38)
    val focusManager = LocalFocusManager.current
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
                .padding(24.dp, 18.dp)
                .clickable { }

        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .clickable { focusManager.clearFocus() }
            ) {
                Text(
                    text = "Add new review...",
                    style = MaterialTheme.typography.h1
                )
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
                    maxLines = 10,
                    modifier = Modifier.heightIn(
                        maxScreenHeightTextArea.dp,
                        maxScreenHeightTextArea.dp
                    ),
                )
                ReviewRadioButtonGroup(viewModel = viewModel, focusManager = focusManager)
                Spacer(modifier = Modifier.weight(1f))
                Button(
                    onClick = {
                        if (repositoryItem != null)
                            viewModel.createPullRequestReview(
                                "${viewModel.vscPullRequestDetail.value._links.self.href}/reviews",
                                repositoryItem.token
                            )
                        viewModel.openAddNewReviewDialog.value = false
                    },
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

@Composable
fun ReviewRadioButtonGroup(viewModel: CodeReviewViewModel, focusManager: FocusManager) {

    Box(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
            .background(MaterialTheme.colors.secondary)
    ) {
        val onSelectedChange = { text: String ->
            viewModel.reviewSelectedType.value = text
        }
        Column {
            viewModel.reviewRadioButtonOptions.forEach { text ->
                Row(Modifier
                    .fillMaxWidth()
                    .selectable(
                        selected = (text.key == viewModel.reviewSelectedType.value),
                        onClick = {
                            onSelectedChange(text.key)
                            focusManager.clearFocus()
                        }
                    )
                    .padding(vertical = 5.dp)
                ) {
                    RadioButton(
                        selected = (text.key == viewModel.reviewSelectedType.value),
                        onClick = {
                            onSelectedChange(text.key)
                            focusManager.clearFocus()
                        },
                        colors = RadioButtonDefaults.colors(Color.Black, Color.Black),
                        modifier = Modifier.size(22.dp)
                    )
                    Text(
                        text = text.value,
                        style = MaterialTheme.typography.subtitle2.merge(),
                        modifier = Modifier.padding(start = 12.dp)
                    )
                }
            }
        }
    }
}
