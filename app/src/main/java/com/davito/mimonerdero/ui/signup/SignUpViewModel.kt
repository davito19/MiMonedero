package com.davito.mimonerdero.ui.signup

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.davito.mimonerdero.data.ResourceRemote
import com.davito.mimonerdero.data.UserRepository
import com.davito.mimonerdero.model.User
import kotlinx.coroutines.launch

class SignUpViewModel : ViewModel() {

    private val userRepository = UserRepository()

    private val _errorMsg: MutableLiveData<String?> = MutableLiveData()
    val errorMsg: LiveData<String?> = _errorMsg

    private val _isSuccessSignUp: MutableLiveData<Boolean> = MutableLiveData()
    val isSuccessSignUp: LiveData<Boolean> = _isSuccessSignUp

    fun validateSignUp(
        name: String,
        email: String,
        password: String,
        replyPassword: String,
        phone: String,
        job: String
    ) {
        if (email.isEmpty() || password.isEmpty() || replyPassword.isEmpty() || name.isEmpty())
            _errorMsg.value = "You must enter all the fields"
        else {
            if (password.length < 6) {
                _errorMsg.value = "Password must have at least 6 digits"
            } else {
                if (password != replyPassword) {
                    _errorMsg.value = "passwords must be the same"
                } else {
                    viewModelScope.launch {
                        val result = userRepository.signUpUser(email, password)
                        result.let {
                            when (result) {
                                is ResourceRemote.Success -> {
                                    val user = User(
                                        uid = result.data,
                                        name = name,
                                        email = email,
                                        phoneNumber = phone,
                                        job = job
                                    )
                                    createUser(user)
                                }
                                is ResourceRemote.Error -> {
                                    var msg = result.message
                                    when (result.message) {
                                        "A network error (such as timeout, interrupted connection or unreachable host) has occurred." -> msg =
                                            "Check your internet connection"
                                        "The email address is already in use by another account." -> msg =
                                            "email already exists"
                                        "The email address is badly formatted." -> msg =
                                            "the email is not valid"
                                    }
                                    _errorMsg.postValue(msg)
                                }
                                is ResourceRemote.Loading -> _errorMsg.postValue("an error has occurred contact the developer omardbonett@gmail.com")
                            }
                        }
                    }
                }
            }
        }
    }

    private fun createUser(user: User) {
        viewModelScope.launch {
            val result = userRepository.createUser(user)
            result.let { resourceRemote ->
                when(resourceRemote){
                    is ResourceRemote.Success -> {
                        _isSuccessSignUp.postValue(true)
                        _errorMsg.postValue("Successful registration")
                    }
                    is ResourceRemote.Error -> {
                        val msg = result.message
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