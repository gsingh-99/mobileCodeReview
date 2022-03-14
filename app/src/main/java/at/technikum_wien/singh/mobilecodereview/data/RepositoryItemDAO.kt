package at.technikum_wien.singh.mobilecodereview.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
@Dao
abstract class RepositoryItemDAO {
    @get:Query("SELECT * FROM repository_item")
    abstract val repositoryItems: LiveData<List<RepositoryItem>>
    @Query("SELECT * FROM repository_item")
    abstract fun loadAllRepositoryItems(): List<RepositoryItem>
    @Query("SELECT * FROM repository_item WHERE _id LIKE :id")
    abstract fun findById(id: Long): LiveData<RepositoryItem>
    @Insert(onConflict = OnConflictStrategy.ABORT)
    abstract suspend fun insert(repositoryItem: RepositoryItem): Long
}