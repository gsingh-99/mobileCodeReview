package at.technikum_wien.singh.mobilecodereview.data.vscModules

import android.util.Log
import at.technikum_wien.singh.mobilecodereview.data.enum.VSCType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.Url
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
    val base: VSCBase
)

data class VSCUser(
    val id: Int?,
    val login: String?
)

data class VSCHead(
    val user: VSCUser,
    val repo: VSCRepositoryItem,
    val ref: String
)

data class VSCBase(
    val ref: String
)

interface APIService {
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

    companion object {
        var logging: HttpLoggingInterceptor = HttpLoggingInterceptor()
        var apiService: APIService? = null
        fun getInstance(): APIService {
            logging.setLevel(HttpLoggingInterceptor.Level.HEADERS)
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