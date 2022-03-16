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
import at.technikum_wien.singh.mobilecodereview.data.vscModules.APIService
import at.technikum_wien.singh.mobilecodereview.data.vscModules.VSCPullrequest
import at.technikum_wien.singh.mobilecodereview.data.vscModules.VSCRepositoryItem
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.time.LocalDateTime
import java.util.*

class CodeReviewViewModel(
    private val application: Application,
    private val repository: RepositoryItemRepository
) :
    ViewModel() {
    val title = mutableStateOf("")
    val openGenericDialog = mutableStateOf(false)
    val openGenericDialogMessage = mutableStateOf("")
    val repositoryItems by lazy { repository.repositoryItems }
    private val _githubRepositoryList = mutableStateListOf<VSCRepositoryItem>()
    val VSCRepositoryItemList: List<VSCRepositoryItem>
        get() = _githubRepositoryList
    private val _pullRequestList = mutableStateListOf<VSCPullrequest>()
    val VSCPullRequestList: List<VSCPullrequest>
        get() = _pullRequestList
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
            val repo = repository.repositoryItemDao.findById(9).observeForever {
                val apiService = APIService.getInstance()
                runBlocking {
                    try {
                        _githubRepositoryList.clear()
                        _githubRepositoryList.add(
                            apiService.getRepository(
                                it.url,
                                it.token
                            )
                        )

                    } catch (e: Exception) {
                        errorMessage = e.message.toString()
                    }
                }
            }
        }

    }

    fun getPullRequestList() {
        viewModelScope.launch {
            val apiService = APIService.getInstance()
            try {
                _pullRequestList.clear()
                _pullRequestList.addAll(
                    apiService.getPullRequests(
                        "https://api.github.com/repos/gsingh-99/XSS-injection/pulls",
                        "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"
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
        if (days > 1)
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