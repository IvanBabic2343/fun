package test.app.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import test.app.domain.model.Repo
import test.app.domain.usecase.GetUserReposUseCase
import test.app.domain.utils.ResponseWrapper
import test.app.presentation.utils.UIState

class UserRepositoriesViewModel(
    private val getUserReposUseCase: GetUserReposUseCase
) : ViewModel() {

    private val _repositories = MutableStateFlow<UIState<List<Repo>>>(UIState.Loading)
    val repositoriesState: StateFlow<UIState<List<Repo>>> = _repositories

    fun fetchRepositories() {
        viewModelScope.launch {
            getUserReposUseCase.invoke()
                .onStart {
                    _repositories.value = UIState.Loading
                }
                .catch { e ->
                    _repositories.value = UIState.Error("Error: ${e.localizedMessage}")
                }
                .collect { result ->
                    when (result) {
                        is ResponseWrapper.Success -> {
                            _repositories.value = UIState.Success(result.data)
                        }

                        is ResponseWrapper.Error -> {
                            _repositories.value = UIState.Error(result.message)
                        }
                    }
                }
        }
    }
}
