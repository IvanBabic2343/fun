package test.app.data.api

import retrofit2.http.GET
import retrofit2.http.Path
import test.app.data.model.RepoDto
import test.app.data.model.TagDto
import test.app.data.model.UserDto

interface FunAppApi {
    @GET("users/octocat")
    suspend fun getUser(): UserDto

    @GET("users/octocat/repos")
    suspend fun getUserRepos(): List<RepoDto>

    @GET("repos/octocat/{repo}")
    suspend fun getRepoDetails(@Path("repo") repo: String): RepoDto

    @GET("repos/octocat/{repo}/tags")
    suspend fun getRepoTags(@Path("repo") repo: String): List<TagDto>
}
