package at.technikum_wien.singh.mobilecodereview.viewmodel

import android.app.Application
import android.util.Log
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import at.technikum_wien.singh.mobilecodereview.data.RepositoryItem
import at.technikum_wien.singh.mobilecodereview.data.RepositoryItemRepository
import at.technikum_wien.singh.mobilecodereview.data.vscModules.*
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.time.LocalDateTime
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
    private val _githubRepositoryList = mutableStateListOf<VSCRepositoryItem>()
    val VSCRepositoryItemList: List<VSCRepositoryItem>
        get() = _githubRepositoryList
    private val _pullRequestList = mutableStateListOf<VSCPullrequest>()
    val VSCPullRequestList: List<VSCPullrequest>
        get() = _pullRequestList
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
            VSCUser(1, ""),
            VSCHead(VSCUser(1, ""), VSCRepositoryItem(1, "", "", Date(), "", VSCUser(1, "")), ""),
            VSCBase(""),
            VSCLinks(VSCHref(""), VSCHref(""), VSCHref(""), VSCHref(""))
        )
    )
    val VSCPullrequestDetail: MutableState<VSCPullrequest>
        get() = _pullRequestDetail

    private val _pullRequestDetailCommits = mutableStateListOf<VSCCommits>()
    val VSCPullrequestDetailCommits: List<VSCCommits>
        get() = _pullRequestDetailCommits

    private val _pullRequestDetailFiles = mutableStateListOf<VSCFile>()
    val VSCPullrequestDetailFiles: List<VSCFile>
        get() = _pullRequestDetailFiles
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
            _githubRepositoryList.clear()
            repositoryItems.value?.forEach {
                try {
                    _githubRepositoryList.add(
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