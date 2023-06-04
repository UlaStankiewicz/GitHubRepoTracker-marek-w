package pl.mwaszczuk.githubrepotracker.network.response.commit

import com.google.gson.annotations.SerializedName

data class CommitResponse(
    @SerializedName("sha")
    val sha: String,
    @SerializedName("commit")
    val commitInfo: CommitInfoResponse
)
