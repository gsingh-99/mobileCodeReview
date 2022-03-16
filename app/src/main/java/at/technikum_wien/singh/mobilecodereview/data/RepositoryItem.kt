package at.technikum_wien.singh.mobilecodereview.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@kotlinx.serialization.Serializable
@Entity(tableName = "repository_item", indices = [Index(value = ["url"], unique = true)])
class RepositoryItem(
    @ColumnInfo(name = "_id")
    @PrimaryKey(autoGenerate = true)
    var id: Long?,

    @ColumnInfo(name = "url")
    var url: String,

    @ColumnInfo(name = "token")
    var token: String
) {
constructor(url: String, token: String): this(null, url, token)
}