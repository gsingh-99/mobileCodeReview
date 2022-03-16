package at.technikum_wien.singh.mobilecodereview.data.vscModules

import android.util.Log
import at.technikum_wien.singh.mobilecodereview.data.enum.VSCType
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
    val id: Int?,
    val title: String?,
    val url: String?,
    val updated_at: Date?,
    val head: VSCHead
)

data class VSCUser(
    val id: Int?,
    val login: String?
)

data class VSCHead(
    val user: VSCUser,
    val repo: VSCRepositoryItem
)

interface APIService {

    @GET
    suspend fun getRepository(@Url url: String, @Header("Authorization") authorization: String): VSCRepositoryItem
    @GET
    suspend fun getPullRequests(@Url url: String, @Header("Authorization") authorization: String): List<VSCPullrequest>
    @GET
    suspend fun getPullRequest(@Url url: String, @Header("Authorization") authorization: String): VSCPullrequest
    companion object {
        var apiService: APIService? = null
        fun getInstance(): APIService {
            if (apiService == null) {
                apiService = Retrofit.Builder()
                    .baseUrl("https://api.github.com")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build().create(APIService::class.java)
            }
            return apiService!!
        }
    }
}