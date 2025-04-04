package test.app.domain.repository

import kotlinx.coroutines.flow.Flow
import test.app.domain.model.Repo
import test.app.domain.model.Tag
import test.app.domain.model.User
import test.app.domain.utils.ResponseWrapper

interface NetworkRepo {
    fun getUser(): Flow<ResponseWrapper<User>>
    fun getUserRepos(): Flow<ResponseWrapper<List<Repo>>>
    fun getRepoDetails(repo: String): Flow<ResponseWrapper<Repo>>
    fun getRepoTags(repo: String): Flow<ResponseWrapper<List<Tag>>>
}
