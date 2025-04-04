package test.app.domain.usecase

import kotlinx.coroutines.flow.Flow
import test.app.domain.model.Tag
import test.app.domain.repository.NetworkRepo
import test.app.domain.utils.ResponseWrapper

class GetRepoTagsUseCase(private val repository: NetworkRepo) {
    operator fun invoke(repo: String): Flow<ResponseWrapper<List<Tag>>> {
        return repository.getRepoTags(repo)
    }
}