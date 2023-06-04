package pl.mwaszczuk.githubrepotracker.network.response.repository

import com.google.gson.annotations.SerializedName

data class RepositoryResponse(
    @SerializedName("id")
    val id: Int
)
