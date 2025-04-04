package test.app.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import test.app.domain.model.Repo
import test.app.domain.model.Tag
import test.app.domain.usecase.GetRepoDetailsUseCase
import test.app.domain.usecase.GetRepoTagsUseCase
import test.app.domain.utils.ResponseWrapper
import test.app.presentation.utils.UIState

class UserRepoDetailsViewModel(
    private val getRepoDetailsUseCase: GetRepoDetailsUseCase,
    private val getRepoTagsUseCase: GetRepoTagsUseCase
) : ViewModel() {

    private val _repoDetails = MutableStateFlow<UIState<Repo?>>(UIState.Loading)
    val repoDetails: StateFlow<UIState<Repo?>> = _repoDetails.asStateFlow()

    private val _repoTags = MutableStateFlow<UIState<List<Tag>>>(UIState.Loading)
    val repoTags: StateFlow<UIState<List<Tag>>> = _repoTags.asStateFlow()

    fun fetchRepoDetails(repoName: String) {
        viewModelScope.launch {
            getRepoDetailsUseCase.invoke(repoName)
                .onStart { _repoDetails.value = UIState.Loading }
                .catch { e ->
                    _repoDetails.value = UIState.Error(e.localizedMessage ?: "Unknown error")
                }
                .collect { result ->
                    when (result) {
                        is ResponseWrapper.Success -> {
                            _repoDetails.value = UIState.Success(result.data)
                        }

                        is ResponseWrapper.Error -> {
                            _repoDetails.value = UIState.Error(result.message)
                        }
                    }
                }

            getRepoTagsUseCase.invoke(repoName)
                .onStart { _repoTags.value = UIState.Loading }
                .catch { e ->
                    _repoTags.value = UIState.Error(e.localizedMessage ?: "Unknown error")
                }
                .collect { result ->
                    when (result) {
                        is ResponseWrapper.Success -> {
                            _repoTags.value = UIState.Success(result.data)
                        }

                        is ResponseWrapper.Error -> {
                            _repoTags.value = UIState.Error(result.message)
                        }
                    }
                }
        }
    }
}
