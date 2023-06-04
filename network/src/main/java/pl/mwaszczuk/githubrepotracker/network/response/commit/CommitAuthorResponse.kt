package pl.mwaszczuk.githubrepotracker.network.response.commit

import com.google.gson.annotations.SerializedName

data class CommitAuthorResponse(
    @SerializedName("name")
    val name: String
)
