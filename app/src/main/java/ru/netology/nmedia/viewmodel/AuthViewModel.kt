package ru.netology.nmedia.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.netology.nmedia.auth.AppAuth
import ru.netology.nmedia.auth.AuthState
import ru.netology.nmedia.model.FeedModelState
import ru.netology.nmedia.repository.PostRepository
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor (
    private val repository: PostRepository,
    private val appAuth: AppAuth,
) : ViewModel() {
        private val _dataState = MutableLiveData<FeedModelState>()
    val dataState: LiveData<FeedModelState>
        get() = _dataState

    val data: LiveData<AuthState> = appAuth
        .authStateFlow
        .asLiveData(Dispatchers.Default)
    val authenticated: Boolean
        get() = appAuth.authStateFlow.value.id != 0L

    fun signIn(login: String, password: String) {
        viewModelScope.launch {
            try {
                _dataState.value = FeedModelState(loading = true)
                val authState = repository.signIn(login,password)
                appAuth.setAuth(authState.id, authState.token)
                _dataState.value = FeedModelState()

            } catch (_: Exception) {
                _dataState.value = FeedModelState(error = true)
            }
        }
    }
}