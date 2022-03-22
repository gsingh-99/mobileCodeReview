package at.technikum_wien.singh.mobilecodereview.viewmodel

import android.app.Application
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import at.technikum_wien.singh.mobilecodereview.data.RepositoryItem
import at.technikum_wien.singh.mobilecodereview.data.RepositoryItemRepository
import at.technikum_wien.singh.mobilecodereview.data.vscModules.*
import at.technikum_wien.singh.mobilecodereview.ui.theme.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import java.util.*

class CodeReviewViewModel(
    private val application: Application,
    private val repository: RepositoryItemRepository
) :
    ViewModel() {
    val isRefreshing = MutableStateFlow(false)
    val title = mutableStateOf("")
    val openGenericDialog = mutableStateOf(false)
    val openGenericDialogMessage = mutableStateOf("")
    val refreshApiCalls = mutableStateOf(true)
    val refreshApiCallsDetails = mutableStateOf(true)
    val repositoryItems by lazy { repository.repositoryItems }

    //RepositoryList
    private val _repositoryList = mutableStateListOf<VSCRepositoryItem>()
    val vscRepositoryItemList: List<VSCRepositoryItem>
        get() = _repositoryList

    //PullRequestList
    private val _pullRequestList = mutableStateListOf<VSCPullrequest>()
    val vscPullRequestList: List<VSCPullrequest>
        get() = _pullRequestList

    //Pull Request
    private val _pullRequestDetail = mutableStateOf(
        VSCPullrequest(
            1L,
            1,
            "",
            "",
            Date(),
            1,
            "",
            "",
            1,
            1,
            VSCUser(1, "", ""),
            VSCHead(
                VSCUser(1, "", ""),
                VSCRepositoryItem(1, "", "", Date(), "", VSCUser(1, "", "")),
                ""
            ),
            VSCBase(""),
            VSCLinks(VSCHref(""), VSCHref(""), VSCHref(""), VSCHref(""), VSCHref("")), 0
        )
    )
    val vscPullRequestDetail: MutableState<VSCPullrequest>
        get() = _pullRequestDetail

    //COMMITS
    private val _pullRequestDetailCommits = mutableStateListOf<VSCCommits>()
    val vscPullRequestDetailCommits: List<VSCCommits>
        get() = _pullRequestDetailCommits

    //FILES
    private val _pullRequestDetailFiles = mutableStateListOf<VSCFile>()
    val vscPullRequestDetailFiles: List<VSCFile>
        get() = _pullRequestDetailFiles

    //COMMENTS
    private val _pullRequestDetailComments = mutableStateListOf<VSCComment>()
    val vscPullRequestDetailComments: List<VSCComment>
        get() = _pullRequestDetailComments

    var errorMessage: String by mutableStateOf("")

    private val TAG = "ViewModel"

    fun addRepository() {
        application.onTerminate()
        val repositoryItem = RepositoryItem(
            "https://api.github.com/repos/gsingh-99/XSS-injection",
            "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"
        )
        viewModelScope.launch {
            repository.insert(repositoryItem)
        }
    }

    init {
        viewModelScope.launch {
            repositoryItems.value
        }
    }

    fun getGithubList() {
        viewModelScope.launch {
            val apiService = APIService.getInstance()
            _repositoryList.clear()
            repositoryItems.value?.forEach {
                try {
                    _repositoryList.add(
                        apiService.getRepository(
                            it.url,
                            " token " + it.token
                        )
                    )
                } catch (e: Exception) {
                    errorMessage = e.message.toString()
                }
            }
        }

    }

    fun getPullRequestList() {
        viewModelScope.launch {
            val apiService = APIService.getInstance()

            _pullRequestList.clear()
            repositoryItems.value?.forEach {

                try {
                    var pullRequestList = apiService.getPullRequests(
                        it.url + "/pulls",
                        " token " + it.token
                    )
                    pullRequestList.forEach { b -> b.repoId = it.id ?: 0 }
                    _pullRequestList.addAll(
                        pullRequestList
                    )
                } catch (e: Exception) {
                    errorMessage = e.message.toString()
                }


            }

        }
    }

    fun getPullRequest(url: String, token: String) {
        viewModelScope.launch {
            val apiService = APIService.getInstance()
            try {
                _pullRequestDetail.value =
                    apiService.getPullRequest(
                        url,
                        " token $token"
                    )
                _pullRequestDetailComments.clear()
                if (_pullRequestDetail.value.comments > 0) {
                    val issueUrl = _pullRequestDetail.value._links.issue.href
                    getPullRequestComments("$issueUrl/comments", token)
                }
            } catch (e: Exception) {
                errorMessage = e.message.toString()
            }
        }
    }

    private fun getPullRequestComments(url: String, token: String) {
        viewModelScope.launch {
            val apiService = APIService.getInstance()
            try {
                _pullRequestDetailComments.clear()
                _pullRequestDetailComments.addAll(
                    apiService.getPullRequestComments(url, " token $token")
                )
            } catch (e: Exception) {
                errorMessage = e.message.toString()
            }
        }
    }

    fun getPullRequestCommits(url: String, token: String) {
        viewModelScope.launch {
            val apiService = APIService.getInstance()
            try {
                _pullRequestDetailCommits.clear()
                _pullRequestDetailCommits.addAll(
                    apiService.getPullRequestCommits(
                        url,
                        " token $token"
                    )
                )
            } catch (e: Exception) {
                errorMessage = e.message.toString()
            }
        }
    }

    fun getPullRequestFiles(url: String, token: String) {
        viewModelScope.launch {
            val apiService = APIService.getInstance()
            try {
                _pullRequestDetailFiles.clear()
                _pullRequestDetailFiles.addAll(
                    apiService.getPullRequestFiles(
                        url,
                        " token $token"
                    )
                )
            } catch (e: Exception) {
                errorMessage = e.message.toString()
            }
        }
    }

    fun calcUpdateDuration(date: Date): String {
        val current = Date()
        val diff = current.time - date.time
        val seconds = diff / 1000
        val minutes = seconds / 60
        val hours = minutes / 60
        val days = hours / 24
        if (days >= 1)
            return "$days d"
        else if (hours in 1..24)
            return "$hours h"
        else if (minutes in 1..60)
            return "$minutes m"
        return "$seconds s"
    }

    fun breakLineToArray(text: String): List<String> {
        var text = text
        var stringList = mutableListOf<String>()
        while (text.contains("\n")) {
            stringList.add(text.substring(0, text.indexOf("\n")))
            text = text.replaceFirst(text.substring(0, text.indexOf("\n") + 1), "")
        }
        stringList.add(text)
        return stringList
    }

    fun breakLineNumbersToArray(textList: List<String>): List<String> {
        var minusIndex = 0
        var plusIndex = 0
        var stringList = mutableListOf<String>()
        if (textList.size > 1)
            textList.forEach {
                if (it.startsWith("@@")) {
                    minusIndex =
                        it.substring((it.indexOf("@@ -") + 4), it.indexOf(",")).toInt()
                    if (it.substring(it.indexOf("+") + 1, it.length).contains(",")) {
                        plusIndex =
                            it.substring(it.indexOf("+") + 1, it.lastIndexOf(",")).toInt()
                    } else {
                        plusIndex =
                            it.substring(it.indexOf("+") + 1, it.length - 3).toInt()
                    }
                    stringList.add("")
                } else {
                    if (it.startsWith("+")) {
                        stringList.add("$plusIndex")
                        plusIndex++
                    } else if (it.startsWith("-")) {
                        stringList.add("$minusIndex")
                        minusIndex++
                    } else {
                        stringList.add("$minusIndex")
                        plusIndex++
                        minusIndex++
                    }

                }
            }
        return stringList
    }

    fun patchTextColor(text: String): Color {
        if (text.startsWith("@@"))
            return BabyBlue
        if (text.startsWith("+"))
            return LimeGreen
        if (text.startsWith("-"))
            return WineRed
        return WhiteGray
    }

    fun patchTextColorLighter(text: String): Color {
        if (text.startsWith("@@"))
            return BabyBlueLighter
        if (text.startsWith("+"))
            return LimeGreenLighter
        if (text.startsWith("-"))
            return WineRedLighter
        return White
    }

}

class CodeReviewViewModelFactory(
    private val application: Application,
    private val repository: RepositoryItemRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CodeReviewViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return CodeReviewViewModel(application = application, repository = repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}