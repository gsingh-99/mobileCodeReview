package at.technikum_wien.singh.mobilecodereview.data
import android.content.Context
class RepositoryItemRepository(context: Context) {
    private val repositoryItemDao by lazy { ApplicationDatabase.getDatabase(context).repositoryItemDao() }
    val repositoryItems by lazy { repositoryItemDao.repositoryItems }
    suspend fun insert(repositoryItem: RepositoryItem) {
        repositoryItemDao.insert(repositoryItem = repositoryItem)
    }
}