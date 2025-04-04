package test.app.domain.usecase

import kotlinx.coroutines.flow.Flow
import test.app.domain.model.Repo
import test.app.domain.repository.NetworkRepo
import test.app.domain.utils.ResponseWrapper

class GetRepoDetailsUseCase(private val repository: NetworkRepo) {
    operator fun invoke(repo: String): Flow<ResponseWrapper<Repo>> {
        return repository.getRepoDetails(repo)
    }
}
