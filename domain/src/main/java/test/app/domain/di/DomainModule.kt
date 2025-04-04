package test.app.domain.di

import org.koin.dsl.module
import test.app.domain.usecase.GetRepoDetailsUseCase
import test.app.domain.usecase.GetRepoTagsUseCase
import test.app.domain.usecase.GetUserReposUseCase
import test.app.domain.usecase.GetUserUseCase

val domainModule = module {
    factory { GetUserUseCase(get()) }
    factory { GetUserReposUseCase(get()) }
    factory { GetRepoTagsUseCase(get()) }
    factory { GetRepoDetailsUseCase(get()) }
}
