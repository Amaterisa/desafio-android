package com.picpay.desafio.android.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.picpay.desafio.android.data.source.remote.toDomain
import com.picpay.desafio.android.model.User
import com.picpay.desafio.android.repositories.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(private val userRepository: UserRepository): ViewModel() {
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
            try {
                val response = userRepository.getUsers()
                if (response.isSuccessful) {
                    _requestStatus.postValue(UserRequestStatus.SUCCESS)
                    _usersResult.postValue(response.body())
                } else {
                    _requestStatus.postValue(UserRequestStatus.ERROR)
                    Log.i(TAG, response.errorBody().toString())
                }
            }
            catch (e: Exception) {
                _requestStatus.postValue(UserRequestStatus.ERROR)
                Log.i(TAG, e.toString())
            }
        }
    }
}