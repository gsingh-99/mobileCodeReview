package at.technikum_wien.singh.mobilecodereview.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class CodeReviewViewModel:
    ViewModel() {
    val title = mutableStateOf<String>("")
    }
class CodeReviewViewModelFactory() : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CodeReviewViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return CodeReviewViewModel() as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}