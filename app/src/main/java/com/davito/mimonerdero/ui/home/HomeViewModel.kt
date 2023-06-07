package com.davito.mimonerdero.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.davito.mimonerdero.data.IncomeOutComeRepository
import com.davito.mimonerdero.data.ResourceRemote
import com.davito.mimonerdero.model.Income
import com.google.firebase.firestore.ktx.toObject
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {

    private val repository = IncomeOutComeRepository()

    private val _errorMsg: MutableLiveData<String?> = MutableLiveData()
    val errorMsg: LiveData<String?> = _errorMsg

    private val _balanceMsg: MutableLiveData<String?> = MutableLiveData()
    val balanceMsg: LiveData<String?> = _balanceMsg

    fun loadBalance() {
        var amoutOut = 0.0
        var ammoutIn = 0.0
        viewModelScope.launch {
            val result = repository.loadIncomes()
            result.let { resourceRemote ->
                when (resourceRemote) {
                    is ResourceRemote.Success -> {
                        resourceRemote.data?.documents?.forEach { document ->
                            val serie = document.toObject<Income>()
                            serie?.let {
                                ammoutIn += serie.amount!!
                            }
                        }
                        val result2 = repository.loadOutcomes()
                        result2.let { resourceRemoteOut ->
                            when (resourceRemoteOut) {
                                is ResourceRemote.Success -> {
                                    resourceRemoteOut.data?.documents?.forEach { document ->
                                        val serie = document.toObject<Income>()
                                        serie?.let {
                                            amoutOut+=serie.amount!!
                                        }
                                    }
                                    val balance = if (ammoutIn - amoutOut<0) 0 else ammoutIn - amoutOut
                                    _balanceMsg.postValue(balance.toString())
                                }
                                is ResourceRemote.Error -> {
                                    _errorMsg.postValue(result2.message)
                                }
                                else -> {
                                    _errorMsg.postValue("omardbonett@gmail.com")
                                }
                            }
                        }
                    }
                    is ResourceRemote.Error -> {
                        _errorMsg.postValue(result.message)
                    }
                    else -> {
                        _errorMsg.postValue("omardbonett@gmail.com")
                    }
                }
            }
        }
    }


}