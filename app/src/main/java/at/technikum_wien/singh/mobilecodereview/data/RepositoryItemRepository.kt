package at.technikum_wien.singh.mobilecodereview.data

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData

class RepositoryItemRepository(context: Context) {
    val repositoryItemDao by lazy {
        ApplicationDatabase.getDatabase(context).repositoryItemDao()
    }
    val repositoryItems by lazy { repositoryItemDao.repositoryItems }
    suspend fun insert(repositoryItem: RepositoryItem) {
        repositoryItemDao.insert(repositoryItem = repositoryItem)
    }

    fun findById(id: Long): LiveData<RepositoryItem> {
        return repositoryItemDao.findById(id)
    }
}