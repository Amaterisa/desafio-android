package com.picpay.desafio.android.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.picpay.desafio.android.model.User
import com.picpay.desafio.android.repositories.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(private val userRepository: UserRepository) : ViewModel() {
    companion object {
        private const val TAG = "UserViewModel"
    }

    private val _usersResult = MutableLiveData<List<User>>()
    val userResult: LiveData<List<User>>
        get() = _usersResult

    private val _requestStatus = MutableLiveData<UserRequestStatus>()
    val requestStatus: LiveData<UserRequestStatus>
        get() = _requestStatus

    fun getUsers() {
        viewModelScope.launch {
            _requestStatus.postValue(UserRequestStatus.LOADING)
            userRepository.getUsers()
                .catch { error ->
                _requestStatus.postValue(UserRequestStatus.ERROR)
                Log.i(TAG, error.message.toString()) }
                .collect {
                    _requestStatus.postValue(UserRequestStatus.SUCCESS)
                    _usersResult.postValue(it) }
        }
    }
}