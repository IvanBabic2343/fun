package test.app.domain.usecase

import kotlinx.coroutines.flow.Flow
import test.app.domain.model.Repo
import test.app.domain.repository.NetworkRepo
import test.app.domain.utils.ResponseWrapper

class GetUserReposUseCase(private val repository: NetworkRepo) {
    operator fun invoke(): Flow<ResponseWrapper<List<Repo>>> {
        return repository.getUserRepos()
    }
}
