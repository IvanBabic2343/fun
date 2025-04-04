package test.app.presentation.di

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import test.app.presentation.viewmodel.UserRepoDetailsViewModel
import test.app.presentation.viewmodel.UserRepositoriesViewModel
import test.app.presentation.viewmodel.UserViewModel

val presentationModule = module {
    viewModel { UserViewModel(get()) }
    viewModel { UserRepositoriesViewModel(get()) }
    viewModel { UserRepoDetailsViewModel(get(), get()) }
}