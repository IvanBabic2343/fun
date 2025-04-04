package test.app.data.repository

import kotlinx.coroutines.flow.Flow
import safeApiCallWithRetry
import test.app.data.api.FunAppApi
import test.app.data.model.toDomainModel
import test.app.domain.model.Repo
import test.app.domain.model.Tag
import test.app.domain.model.User
import test.app.domain.repository.NetworkRepo
import test.app.domain.utils.ResponseWrapper

class NetworkRepoImpl(private val api: FunAppApi) : NetworkRepo {


    override fun getUser(): Flow<ResponseWrapper<User>> = safeApiCallWithRetry {
        api.getUser().toDomainModel()
    }

    override fun getUserRepos(): Flow<ResponseWrapper<List<Repo>>> = safeApiCallWithRetry {
        api.getUserRepos().map { it.toDomainModel() }
    }

    override fun getRepoDetails(repo: String): Flow<ResponseWrapper<Repo>> = safeApiCallWithRetry {
        api.getRepoDetails(repo).toDomainModel()
    }

    override fun getRepoTags(repo: String): Flow<ResponseWrapper<List<Tag>>> = safeApiCallWithRetry {
        api.getRepoTags(repo).map { it.toDomainModel() }
    }

//    override fun getUser(): Flow<ResponseWrapper<User>> = flow {
//        try {
//            val response = api.getUser().toDomainModel()
//            emit(ResponseWrapper.Success(response))
//        } catch (e: Exception) {
//            emit(ResponseWrapper.Error(ApiErrorHandler.getErrorMessage(e)))
//        }
//    }.flowOn(Dispatchers.IO)

//    override fun getUserRepos(): Flow<ResponseWrapper<List<Repo>>> = flow {
//        try {
//            val response = api.getUserRepos().map { it.toDomainModel() }
//            emit(ResponseWrapper.Success(response))
//        } catch (e: Exception) {
//            emit(ResponseWrapper.Error(ApiErrorHandler.getErrorMessage(e)))
//        }
//    }.flowOn(Dispatchers.IO)

//    override fun getRepoDetails(repo: String): Flow<ResponseWrapper<Repo>> = flow {
//        try {
//            val response = api.getRepoDetails(repo).toDomainModel()
//            emit(ResponseWrapper.Success(response))
//        } catch (e: Exception) {
//            emit(ResponseWrapper.Error(ApiErrorHandler.getErrorMessage(e)))
//        }
//    }.flowOn(Dispatchers.IO)
//
//    override fun getRepoTags(repo: String): Flow<ResponseWrapper<List<Tag>>> = flow {
//        try {
//            val response = api.getRepoTags(repo).map { it.toDomainModel() }
//            emit(ResponseWrapper.Success(response))
//        } catch (e: Exception) {
//            emit(ResponseWrapper.Error(ApiErrorHandler.getErrorMessage(e)))
//        }
//    }.flowOn(Dispatchers.IO)
}
