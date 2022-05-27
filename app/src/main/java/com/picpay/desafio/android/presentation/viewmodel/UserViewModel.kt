package com.picpay.desafio.android.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.picpay.desafio.android.domain.model.UserListState
import com.picpay.desafio.android.data.repositories.UserRepositoryImpl
import com.picpay.desafio.android.domain.usecases.GetUsersUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(private val getUsersUseCase: GetUsersUseCase) : ViewModel() {
    companion object {
        private const val TAG = "UserViewModel"
    }

    private val _usersResult = MutableLiveData<UserListState>()
    val userResult: LiveData<UserListState>
        get() = _usersResult

    fun getUsers() {
        viewModelScope.launch {
            getUsersUseCase().collect { _usersResult.postValue(it) }
        }
    }
}