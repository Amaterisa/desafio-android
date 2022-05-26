package com.picpay.desafio.android.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.picpay.desafio.android.model.UserListState
import com.picpay.desafio.android.repositories.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(private val userRepository: UserRepository) : ViewModel() {
    companion object {
        private const val TAG = "UserViewModel"
    }

    private val _usersResult = MutableLiveData<UserListState>()
    val userResult: LiveData<UserListState>
        get() = _usersResult

    fun getUsers() {
        viewModelScope.launch {
            userRepository.getUsers().collect { _usersResult.postValue(it) }
        }
    }
}