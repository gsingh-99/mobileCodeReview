package at.technikum_wien.singh.mobilecodereview.viewmodel

import android.app.Application
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import at.technikum_wien.singh.mobilecodereview.data.RepositoryItem
import at.technikum_wien.singh.mobilecodereview.data.RepositoryItemRepository
import kotlinx.coroutines.launch

class CodeReviewViewModel(
    private val application: Application,
    private val repository: RepositoryItemRepository
) :
    ViewModel() {
    val repositoryItems by lazy { repository.repositoryItems }
    val title = mutableStateOf<String>("")

    init {
       /* var repositoryItem = RepositoryItem("test", "test")
        viewModelScope.launch {
            repository.insert(repositoryItem)
        }*/
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