package at.technikum_wien.singh.mobilecodereview.view

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import at.technikum_wien.singh.mobilecodereview.data.vscModules.VSCFile
import at.technikum_wien.singh.mobilecodereview.ui.theme.Black
import at.technikum_wien.singh.mobilecodereview.viewmodel.CodeReviewViewModel
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState

@Composable
fun PullRequestDetailFilesDetailScreen(
    //navController: NavController,
    viewModel: CodeReviewViewModel,
    file: VSCFile?,
) {
    val stringList = viewModel.breakLineToArray(file?.patch ?: "")
    val numberList = viewModel.breakLineNumbersToArray(stringList)
    val maxScreenWidth = LocalConfiguration.current.screenWidthDp.times(0.09)
    val maxScreenHeight = LocalConfiguration.current.screenHeightDp.times(0.85)

    if (numberList.isNotEmpty()) {
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
            }
        }
    } else {
        Text(text = "This file is too large to preview.")
    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun PullRequestDetailFilesScreenPager(viewModel: CodeReviewViewModel, file: VSCFile?) {

    val pagerState = rememberPagerState()
    val maxScreenHeight = LocalConfiguration.current.screenHeightDp

    HorizontalPager(count = viewModel.vscPullRequestDetailFiles.size, state = pagerState) {
        val selectedFile = viewModel.vscPullRequestDetailFiles[currentPage]
        Box(modifier = Modifier.fillMaxSize()) {
            PullRequestDetailFilesDetailScreen(viewModel, selectedFile)
        }
        viewModel.title.value = selectedFile.filename
    }
    LaunchedEffect(Unit, block = {
        pagerState.scrollToPage(viewModel.vscPullRequestDetailFiles.indexOf(file))
    })
}

/*@Preview
@Composable
fun FileDetailPreview() {
    MobileCodeReviewTheme {
        PullRequestDetailFilesDetailScreen(null, null, VSCFile("", "", 1, 1, 1, "@@ -16,7 +16,6 @@\\n @REM specific language governing permissions and limitations\\n @REM under the License.\\n @REM ----------------------------------------------------------------------------\\n-\\n @REM ----------------------------------------------------------------------------\\n @REM Maven Start Up Batch script\\n @REM", ""))
    }
}*/