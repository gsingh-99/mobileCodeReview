package at.technikum_wien.singh.mobilecodereview.data.vscModules

import android.util.Log
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*
import java.util.*

data class VSCRepositoryItem(
    val id: Int?,
    val name: String?,
    val full_name: String?,
    val updated_at: Date?,
    val url: String,
    val owner: VSCUser
)

data class VSCPullrequest(
    var repoId: Long,
    val id: Int?,
    val title: String?,
    val url: String?,
    val updated_at: Date?,
    val number: Int,
    val state: String?,
    val body: String?,
    val commits: Int?,
    val changed_files: Int?,
    val user: VSCUser,
    val head: VSCHead,
    val base: VSCBase,
    val _links: VSCLinks,
    val comments: Int
)

data class VSCLinks(
    val self: VSCHref,
    val issue: VSCHref,
    val comments: VSCHref,
    val review_comments: VSCHref,
    val commits: VSCHref
)

data class VSCHref(
    val href: String
)

data class VSCUser(
    val id: Int,
    val login: String,
    val avatar_url: String
)

data class VSCHead(
    val user: VSCUser,
    val repo: VSCRepositoryItem,
    val ref: String
)

data class VSCReview(
    val id: Int,
    val user: VSCUser,
    val body: String,
    val state: String,
    val submitted_at: Date?
)

data class VSCWriteReview(
    val body: String,
    val event: String
)

data class VSCWriteComment(
    val body: String
)

data class VSCBase(
    val ref: String
)

data class VSCCommitAuthor(
    val name: String,
    val date: Date
)

data class VSCCommit(
    val author: VSCCommitAuthor,
    val message: String
)


data class VSCFile(
    val sha: String,
    val filename: String,
    val additions: Int,
    val deletions: Int,
    val changes: Int,
    val patch: String,
    val raw_url: String
)

data class VSCCommits(
    val commit: VSCCommit
)

data class VSCComment(
    val user: VSCUser,
    val body: String,
    val updated_at: Date,
)

fun String.capitalized(): String {
    return this.replaceFirstChar {
        if (it.isLowerCase())
            it.titlecase(Locale.getDefault())
        else it.toString()
    }
}

interface APIService {
    @Headers(
        "User-Agent: gsingh-99"
    )
    @GET
    suspend fun getUser(
        @Url url: String = "https://api.github.com/user",
        @Header("Authorization") authorization: String
    ): VSCUser

    @Headers(
        "User-Agent: gsingh-99"
    )
    @GET
    suspend fun getRepository(
        @Url url: String,
        @Header("Authorization") authorization: String
    ): VSCRepositoryItem

    @Headers(
        "User-Agent: gsingh-99"
    )
    @GET
    suspend fun getPullRequests(
        @Url url: String,
        @Header("Authorization") authorization: String
    ): List<VSCPullrequest>

    @Headers(
        "User-Agent: gsingh-99"
    )
    @GET
    suspend fun getPullRequest(
        @Url url: String,
        @Header("Authorization") authorization: String
    ): VSCPullrequest

    @Headers(
        "User-Agent: gsingh-99"
    )
    @GET
    suspend fun getPullRequestCommits(
        @Url url: String,
        @Header("Authorization") authorization: String
    ): List<VSCCommits>

    @Headers(
        "User-Agent: gsingh-99"
    )
    @GET
    suspend fun getPullRequestFiles(
        @Url url: String,
        @Header("Authorization") authorization: String
    ): List<VSCFile>

    @Headers(
        "User-Agent: gsingh-99"
    )
    @GET
    suspend fun getPullRequestComments(
        @Url url: String,
        @Header("Authorization") authorization: String
    ): List<VSCComment>

    @Headers(
        "User-Agent: gsingh-99"
    )
    @GET
    suspend fun getPullRequestReviews(
        @Url url: String,
        @Header("Authorization") authorization: String
    ): List<VSCReview>

    @Headers(
        "User-Agent: gsingh-99"
    )
    @POST
    suspend fun createPullRequestReview(
        @Url url: String,
        @Header("Authorization") authorization: String,
        @Body body: VSCWriteReview
    ): Response<VSCReview>

    @POST
    suspend fun createPullRequestComment(
        @Url url: String,
        @Header("Authorization") authorization: String,
        @Body body: VSCWriteComment
    ): Response<VSCWriteComment>

    companion object {
        var logging: HttpLoggingInterceptor = HttpLoggingInterceptor()
        var apiService: APIService? = null
        fun getInstance(): APIService {
            logging.setLevel(HttpLoggingInterceptor.Level.BODY)
            var httpClient: OkHttpClient =
                OkHttpClient().newBuilder().addInterceptor(logging).build()
            Log.d("Network", "Api call requested")
            if (apiService == null) {
                apiService = Retrofit.Builder()
                    .baseUrl("https://api.github.com")
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(httpClient)
                    .build().create(APIService::class.java)
            }
            return apiService!!
        }
    }
}