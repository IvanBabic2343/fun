package test.app.domain.usecase

import kotlinx.coroutines.flow.Flow
import test.app.domain.model.User
import test.app.domain.repository.NetworkRepo
import test.app.domain.utils.ResponseWrapper

class GetUserUseCase(private val repository: NetworkRepo) {
    operator fun invoke(): Flow<ResponseWrapper<User>> {
        return repository.getUser()
    }
}
