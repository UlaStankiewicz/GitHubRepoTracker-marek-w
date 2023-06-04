package pl.mwaszczuk.githubrepotracker.network.api

import pl.mwaszczuk.githubrepotracker.network.response.commit.CommitResponse
import pl.mwaszczuk.githubrepotracker.network.response.repository.RepositoryResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface RepositoriesApi {

    @GET("repos/{owner}/{repo}")
    suspend fun getRepository(
        @Path("owner") owner: String,
        @Path("repo") repo: String
    ) : Response<RepositoryResponse>

    @GET("repos/{owner}/{repo}/commits")
    suspend fun getCommits(
        @Path("owner") owner: String,
        @Path("repo") repo: String
    ) : Response<List<CommitResponse>>
}
