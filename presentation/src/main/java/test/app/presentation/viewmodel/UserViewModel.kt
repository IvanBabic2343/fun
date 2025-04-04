package test.app.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import test.app.domain.model.User
import test.app.domain.usecase.GetUserUseCase
import test.app.presentation.utils.UIState

class UserViewModel(
    private val getUserUseCase: GetUserUseCase
) : ViewModel() {

    private val _userState = MutableStateFlow<UIState<User>>(UIState.Loading)
    val userState: StateFlow<UIState<User>> = _userState

    fun getUser() {
        viewModelScope.launch {
            getUserUseCase.invoke()
                .onStart {
                    _userState.value = UIState.Loading
                }
                .catch { e ->
                    _userState.value = UIState.Error("Error: ${e.localizedMessage}")
                }
                .collect { result ->
                    when (result) {
                        is test.app.domain.utils.ResponseWrapper.Success -> {
                            _userState.value = UIState.Success(result.data)
                        }
                        is test.app.domain.utils.ResponseWrapper.Error -> {
                            _userState.value = UIState.Error(result.message)
                        }
                    }
                }
        }
    }
}
