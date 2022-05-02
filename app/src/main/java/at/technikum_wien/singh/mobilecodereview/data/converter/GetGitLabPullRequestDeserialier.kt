package at.technikum_wien.singh.mobilecodereview.data.converter

import at.technikum_wien.singh.mobilecodereview.data.vscModules.*
import com.google.gson.JsonDeserializer
import kotlin.Throws
import com.google.gson.JsonParseException
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonElement
import java.lang.reflect.Type
import java.util.*

class GetGitLabPullRequestDeserialier : JsonDeserializer<VSCPullrequest> {
    override fun deserialize(
        json: JsonElement,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): VSCPullrequest {
        val jsonObject = json.asJsonObject
        val repoId = jsonObject["project_id"].asLong
        val id = jsonObject["id"].asInt
        val title = jsonObject["title"].asString
        val url = ""
        val updated_at = jsonObject["updated_at"].asString
        val number = jsonObject["iid"].asInt
        val state = jsonObject["state"].asString
        val body = jsonObject["description"].asString
        val commits = 0
        val changed_files = jsonObject["changes"].asJsonArray.size()
        val user = VSCUser(
            jsonObject["author"].asJsonObject["id"].asInt,
            jsonObject["author"].asJsonObject["username"].asString,
            jsonObject["author"].asJsonObject["avatar_url"].asString
        )
        val head = VSCHead(
            user,
            VSCRepositoryItem(
                0,
                jsonObject["references"].asJsonObject["full"].asString,
                jsonObject["references"].asJsonObject["full"].asString,
                Date(updated_at),
                url,
                user
            ),
            jsonObject["target_branch"].asString
        )
        val base = VSCBase(jsonObject["source_branch"].asString)
        val _links = VSCLinks(VSCHref(""), VSCHref(""), VSCHref(""), VSCHref(""), VSCHref(""))
        val comments = 0
        return VSCPullrequest(
            repoId,
            id,
            title,
            url,
            Date(updated_at),
            number,
            state,
            body,
            comments,
            changed_files,
            user,
            head,
            base,
            _links,
            comments
        )
    }
}