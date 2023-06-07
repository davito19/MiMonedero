package com.davito.mimonerdero.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.davito.mimonerdero.data.ResourceRemote
import com.davito.mimonerdero.data.UserRepository
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {

    private val userRepository = UserRepository()

    private val _errorMsg: MutableLiveData<String?> = MutableLiveData()
    val errorMsg: LiveData<String?> = _errorMsg

    private val _isSuccessSignIn: MutableLiveData<Boolean> = MutableLiveData()
    val isSuccessSignIn: LiveData<Boolean> = _isSuccessSignIn

    private val _isSessionActive: MutableLiveData<Boolean> = MutableLiveData()
    val isSessionActive: LiveData<Boolean> = _isSessionActive

    fun validateLogin(email: String, password: String) {
        if (email.isEmpty() || password.isEmpty()) _errorMsg.value = "You must enter all the fields"
        else {
            viewModelScope.launch {
                val result = userRepository.singInUser(email, password)
                result.let {
                    when (result) {
                        is ResourceRemote.Success -> {
                            _isSuccessSignIn.postValue(true)
                        }
                        is ResourceRemote.Error -> {
                            var msg = result.message
                            when (result.message) {
                                "A network error (such as timeout, interrupted connection or unreachable host) has occurred." -> msg =
                                    "Check your internet connection"
                                "The email address is badly formatted." -> msg =
                                    "the email is not valid"
                                "The password is invalid or the user does not have a password." -> msg =
                                    "the username or password are invalid"
                            }
                            _errorMsg.postValue(msg)
                        }
                        else -> {
                            _errorMsg.postValue("an error has occurred contact the developer omardbonett@gmail.com")
                        }
                    }
                }
            }
        }
    }

    fun validateSessionActive() {
        _isSessionActive.postValue(userRepository.isSessionActive())
    }

}