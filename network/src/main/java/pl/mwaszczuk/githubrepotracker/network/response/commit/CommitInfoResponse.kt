package pl.mwaszczuk.githubrepotracker.network.response.commit

import com.google.gson.annotations.SerializedName

data class CommitInfoResponse(
    @SerializedName("message")
    val message: String,
    @SerializedName("author")
    val author: CommitAuthorResponse
)
