package at.technikum_wien.singh.mobilecodereview.viewmodel

import android.app.Application
import android.util.Log
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import at.technikum_wien.singh.mobilecodereview.data.RepositoryItem
import at.technikum_wien.singh.mobilecodereview.data.RepositoryItemRepository
import at.technikum_wien.singh.mobilecodereview.data.UserPreferencesRepository
import at.technikum_wien.singh.mobilecodereview.data.enum.Host
import at.technikum_wien.singh.mobilecodereview.data.vscModules.*
import at.technikum_wien.singh.mobilecodereview.ui.theme.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import java.util.*

class CodeReviewViewModel(
    private val application: Application,
    private val repository: RepositoryItemRepository,
    private val userPreferencesRepository: UserPreferencesRepository
) :
    ViewModel() {
    private val gitHubApiService = APIService.getGithubInstance()

    private val gitLabApiService = APIService.getGitLabInstance()

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
    val openAddNewCommentsDialog = mutableStateOf(false)
    var tfAddCommentText: String by mutableStateOf("")
    private val _pullRequestDetailComments = mutableStateListOf<VSCComment>()
    val vscPullRequestDetailComments: List<VSCComment>
        get() = _pullRequestDetailComments

    //Reviews
    private val _pullRequestDetailReviews = mutableStateListOf<VSCReview>()
    val vscPullRequestDetailReviews: List<VSCReview>
        get() = _pullRequestDetailReviews
    val openAddNewReviewDialog = mutableStateOf(false)
    var tfAddReviewText: String by mutableStateOf("")
    val reviewRadioButtonOptions = mapOf(
        "APPROVE" to "Approve",
        "COMMENT" to "Comment",
        "REQUEST_CHANGES" to "Request changes"
    )
    var reviewSelectedType = mutableStateOf("APPROVE")

    var errorMessage: String by mutableStateOf("")

    val fileSearcherProgress = mutableStateOf(false)

    // Add new Repository
    val openAddNewRepositoryDialog = mutableStateOf(false)
    var tfAddRepositoryUrl: String by mutableStateOf("")
    var tfAddRepositoryToken: String by mutableStateOf("")

    // Settings
    var tfSonarQubeUrl: String by mutableStateOf("https://localhost:9001")
    var tfGitHubUrl: String by mutableStateOf("https://api.github")
    var tfGitLabUrl: String by mutableStateOf("https://api.gitlab")
    private val TAG = "ViewModel"

    fun urlParser(url: String): String {
        var parsedUrl = ""
        if (url.startsWith("https://github.com/")) {
            parsedUrl =
                "https://api.github.com/repos/${url.replaceFirst("https://github.com/", "")}"
        }
        return parsedUrl
    }

    fun addRepository() {
        tfAddRepositoryUrl = urlParser(tfAddRepositoryUrl)
        val repositoryItem = RepositoryItem(
            tfAddRepositoryUrl,
            tfAddRepositoryToken,
            Host.GITHUB
        )
        tfAddRepositoryUrl = ""
        tfAddRepositoryToken = ""
        viewModelScope.launch {
            repository.insert(repositoryItem)
        }
        getPullRequestList()
        getGithubList()
        refreshApiCalls.value = false
        openAddNewRepositoryDialog.value = false

    }

    init {
        viewModelScope.launch {
            // var item = gitLabApiService.getPullRequest("https://gitlab.com/api/v4/projects/35444404/merge_requests/1"," Bearer glpat-8gzjyJsV6ob5pyp9yVve")
            userPreferencesRepository.userPreferencesFlow.collect {
                tfSonarQubeUrl = it.sonarQubeAddress
                tfGitHubUrl = it.githubApiUrl
                tfGitLabUrl = it.gitlabApiUrl
            }
        }
    }

    fun updateSonarQubeAddress(newSonarQubeAddress: String) {
        viewModelScope.launch { userPreferencesRepository.updateSonarQubeAddress(newSonarQubeAddress = newSonarQubeAddress); }
    }

    fun updateGithubApiUrl(newGithubApiUrl: String) {
        viewModelScope.launch { userPreferencesRepository.updateGithubApiUrl(newGithubApiUrl = newGithubApiUrl); }
    }

    fun updateGitlabApiUrl(newGitlabApiUrl: String) {
        viewModelScope.launch { userPreferencesRepository.updateGitlabApiUrl(newGitlabApiUrl = newGitlabApiUrl); }
    }

    fun getGithubList() {
        viewModelScope.launch {
            _repositoryList.clear()
            repositoryItems.value?.forEach {
                try {
                    _repositoryList.add(
                        gitHubApiService.getRepository(
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

            _pullRequestList.clear()
            repositoryItems.value?.forEach {

                try {
                    val pullRequestList = gitHubApiService.getPullRequests(
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
            try {
                _pullRequestDetail.value =
                    gitHubApiService.getPullRequest(
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
            try {
                _pullRequestDetailComments.clear()
                _pullRequestDetailComments.addAll(
                    gitHubApiService.getPullRequestComments(url, " token $token")
                )
            } catch (e: Exception) {
                errorMessage = e.message.toString()
            }
        }
    }

    fun getPullRequestCommits(url: String, token: String) {
        viewModelScope.launch {
            try {
                _pullRequestDetailCommits.clear()
                _pullRequestDetailCommits.addAll(
                    gitHubApiService.getPullRequestCommits(
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
            try {
                _pullRequestDetailFiles.clear()
                _pullRequestDetailFiles.addAll(
                    gitHubApiService.getPullRequestFiles(
                        url,
                        " token $token"
                    )
                )
            } catch (e: Exception) {
                errorMessage = e.message.toString()
            }
        }
    }

    fun getPullRequestReviews(url: String, token: String) {
        viewModelScope.launch {
            try {
                _pullRequestDetailReviews.clear()
                _pullRequestDetailReviews.addAll(
                    gitHubApiService.getPullRequestReviews(url, " token $token")
                )
                _pullRequestDetailReviews.forEach {
                    if (it.state == "PENDING")
                        _pullRequestDetailReviews.remove(it)
                }
            } catch (e: Exception) {
                errorMessage = e.message.toString()
            }
        }
    }


    fun createPullRequestReview(url: String, token: String) {
        viewModelScope.launch {
            try {
                val review = VSCWriteReview(tfAddReviewText, reviewSelectedType.value)
                val code =
                    gitHubApiService.createPullRequestReview(url, " token $token", review)
                        .code()
                if (code == 422) {
                    // if the user has a pending request search the id and overwrite it with the given body and event type
                    val user =
                        gitHubApiService.getUser("https://api.github.com/user", " token $token")
                    val reviews = gitHubApiService.getPullRequestReviews(url, " token $token")
                    for (reviewItem in reviews) {
                        if (reviewItem.state == "PENDING" && reviewItem.user.id == user.id) {
                            gitHubApiService.createPullRequestReview(
                                url + "/${reviewItem.id}/events",
                                " token $token",
                                review
                            )
                            break
                        }
                    }
                }
                tfAddReviewText = ""
                reviewSelectedType.value = "APPROVE"
            } catch (e: Exception) {
                errorMessage = e.message.toString()
            }
        }
    }

    fun createPullRequestComment(url: String, token: String) {
        viewModelScope.launch {
            try {
                val comment = VSCWriteComment(tfAddCommentText)
                val code =
                    gitHubApiService.createPullRequestComment(url, " token $token", comment)
                        .code()
                if (code == 201) {
                    getPullRequestComments(
                        "${_pullRequestDetail.value._links.issue.href}/comments", token
                    )
                }
                tfAddCommentText = ""
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
        var lineText = text
        val stringList = mutableListOf<String>()
        while (lineText.contains("\n")) {
            if (!lineText.startsWith("\\ No newline at end of file"))
                stringList.add(lineText.substring(0, lineText.indexOf("\n")))
            lineText = lineText.replaceFirst(lineText.substring(0, lineText.indexOf("\n") + 1), "")
        }
        stringList.add(lineText)
        return stringList
    }

    fun breakLineNumbersToArray(textList: List<String>): List<String> {
        var minusIndex = 0
        var plusIndex = 0
        val stringList = mutableListOf<String>()
        if (textList.size > 1)
            textList.forEach {
                if (it.startsWith("@@")) {
                    val minusText = it.removeRange(it.indexOf(" +"), it.length)
                    minusIndex =
                        if (minusText.substring(minusText.indexOf("-") + 1).contains(",")) {
                            minusText.substring(
                                (minusText.indexOf("@@ -") + 4),
                                minusText.indexOf(",")
                            ).toInt()
                        } else {
                            minusText.substring((minusText.indexOf("@@ -") + 4)).toInt()
                        }
                    plusIndex = if (it.substring(it.indexOf("+") + 1, it.length).contains(",")) {
                        it.substring(it.indexOf("+") + 1, it.lastIndexOf(",")).toInt()
                    } else {
                        it.substring(it.indexOf("+") + 1, it.length - 3).toInt()
                    }
                    stringList.add("")
                } else {
                    when {
                        it.startsWith("+") -> {
                            stringList.add("$plusIndex")
                            plusIndex++
                        }
                        it.startsWith("-") -> {
                            stringList.add("$minusIndex")
                            minusIndex++
                        }
                        else -> {
                            stringList.add("$minusIndex")
                            plusIndex++
                            minusIndex++
                        }

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

    fun parseReviewStateText(reviewState: String): String {
        return when (reviewState) {
            "COMMENTED" -> {
                "commented"
            }
            "APPROVED" -> {
                "approved"
            }
            "CHANGES_REQUESTED" -> {
                "requested changes"
            }
            else -> {
                reviewState
            }
        }
    }

    fun parseReviewStateColor(reviewState: String): Color {
        return when (reviewState) {
            "COMMENTED" -> {
                BabyBlue
            }
            "APPROVED" -> {
                ApproveGreen
            }
            "CHANGES_REQUESTED" -> {
                DisapproveRed
            }
            else -> {
                Color.Black
            }
        }
    }

    fun findFileBlocker(sha: String?): VSCFile {
        var foundFile: VSCFile = VSCFile("", "", 0, 0, 0, "", "")
        for (file: VSCFile in vscPullRequestDetailFiles) {
            if (file.sha == sha) {
                foundFile = file
                break
            }
        }
        fileSearcherProgress.value = true
        return foundFile
    }
}


class CodeReviewViewModelFactory(
    private val application: Application,
    private val repository: RepositoryItemRepository,
    private val userPreferencesRepository: UserPreferencesRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CodeReviewViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return CodeReviewViewModel(
                application = application,
                repository = repository,
                userPreferencesRepository = userPreferencesRepository
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}