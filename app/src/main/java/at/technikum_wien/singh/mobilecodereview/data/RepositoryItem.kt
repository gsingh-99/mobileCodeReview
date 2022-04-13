package at.technikum_wien.singh.mobilecodereview.data

import androidx.room.*
import at.technikum_wien.singh.mobilecodereview.data.enum.Host

@kotlinx.serialization.Serializable
@Entity(tableName = "repository_item", indices = [Index(value = ["url"], unique = true)])
data class RepositoryItem(
    @ColumnInfo(name = "_id")
    @PrimaryKey(autoGenerate = true)
    var id: Long,

    @ColumnInfo(name = "url")
    var url: String,

    @ColumnInfo(name = "token")
    var token: String,

    @ColumnInfo(name = "host")
    var host: String
) {
    constructor(url: String, token: String, type: Host) : this(0, url, token, type.name)
    constructor() : this(0, "", "", Host.GITHUB.name)
}