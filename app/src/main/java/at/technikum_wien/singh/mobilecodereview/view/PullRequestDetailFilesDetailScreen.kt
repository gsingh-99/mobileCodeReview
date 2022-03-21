package at.technikum_wien.singh.mobilecodereview.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import at.technikum_wien.singh.mobilecodereview.data.vscModules.VSCFile
import at.technikum_wien.singh.mobilecodereview.ui.theme.Black
import at.technikum_wien.singh.mobilecodereview.viewmodel.CodeReviewViewModel

@Composable
fun PullRequestDetailFilesDetailScreen(
    navController: NavController?,
    viewModel: CodeReviewViewModel,
    file: VSCFile?,
) {
    viewModel.title.value = file?.filename ?: "This file is too large to preview."
    val stringList = viewModel.breakLineToArray(file?.patch ?: "")
    val numberList = viewModel.breakLineNumbersToArray(stringList)
    var maxScreenWidth = LocalConfiguration.current.screenWidthDp.times(0.09)
    var maxScreenHeight = LocalConfiguration.current.screenHeightDp.times(0.87)

    Box(modifier = Modifier.heightIn(0.dp, maxScreenHeight.dp)) {
    LazyColumn(Modifier.fillMaxWidth()) {
        itemsIndexed(stringList) { index, text ->
            Row(
                verticalAlignment = Alignment.Top,
                modifier = Modifier.background(viewModel.patchTextColor(text))
            ) {
                Column(
                    Modifier
                        .widthIn(maxScreenWidth.dp, maxScreenWidth.dp)
                ) {
                    Text(
                        text = numberList[index],
                        color = Black,
                        textAlign = TextAlign.Right,
                        style = MaterialTheme.typography.h6,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
                Column(
                    Modifier
                        .fillMaxWidth()
                        .background(viewModel.patchTextColorLighter(text))
                ) {
                    Text(
                        text = text,
                        color = Black,
                        style = MaterialTheme.typography.h6
                    )
                }
            }
        }
    }}
    //Text(text = file?.patch ?: "")
}

/*@Preview
@Composable
fun FileDetailPreview() {
    MobileCodeReviewTheme {
        PullRequestDetailFilesDetailScreen(null, null, VSCFile("", "", 1, 1, 1, "@@ -16,7 +16,6 @@\\n @REM specific language governing permissions and limitations\\n @REM under the License.\\n @REM ----------------------------------------------------------------------------\\n-\\n @REM ----------------------------------------------------------------------------\\n @REM Maven Start Up Batch script\\n @REM", ""))
    }
}*/