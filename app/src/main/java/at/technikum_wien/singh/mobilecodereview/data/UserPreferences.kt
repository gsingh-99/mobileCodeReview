package at.technikum_wien.singh.mobilecodereview.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

data class UserPreferences(
    val sonarQubeAddress: String,
    val githubApiUrl: String,
    val gitlabApiUrl: String
    )
private const val USER_PREFERENCES_NAME = "user_preferences"
val Context.dataStore by preferencesDataStore(
    name = USER_PREFERENCES_NAME
)
class UserPreferencesRepository(private val userPreferenceDataStore: DataStore<Preferences>) {
    private object PreferencesKeys {
        val SQADRESS = stringPreferencesKey("sonarQubeAddress")
        val GHAPIURL = stringPreferencesKey("githubApiUrl")
        val GLAPIURL = stringPreferencesKey("gitlabApiUrl")
    }
    val userPreferencesFlow: Flow<UserPreferences> = userPreferenceDataStore.data
        .map { preferences ->
            val sonarQubeAddress = preferences[PreferencesKeys.SQADRESS]?: "https://localhost:9001"
            val githubApiUrl = preferences[PreferencesKeys.GHAPIURL]?: "https://api.github"
            val gitlabApiUrl = preferences[PreferencesKeys.GLAPIURL]?: "https://api.gitlab"
            UserPreferences(sonarQubeAddress, githubApiUrl, gitlabApiUrl)
        }
    suspend fun updateSonarQubeAddress(newSonarQubeAddress: String) {
        userPreferenceDataStore.edit { preferences ->
            preferences[PreferencesKeys.SQADRESS] = newSonarQubeAddress
        }
    }
    suspend fun updateGithubApiUrl(newGithubApiUrl: String) {
        userPreferenceDataStore.edit { preferences ->
            preferences[PreferencesKeys.GHAPIURL] = newGithubApiUrl
        }
    }
    suspend fun updateGitlabApiUrl(newGitlabApiUrl: String) {
        userPreferenceDataStore.edit { preferences ->
            preferences[PreferencesKeys.GLAPIURL] = newGitlabApiUrl
        }
    }

}