package com.davito.mimonerdero.ui.movements

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.davito.mimonerdero.data.IncomeOutComeRepository
import com.davito.mimonerdero.data.ResourceRemote
import com.davito.mimonerdero.model.Income
import com.google.firebase.firestore.ktx.toObject
import kotlinx.coroutines.launch

class MovementsViewModel : ViewModel() {

    private val repository = IncomeOutComeRepository()
    private var movementList: ArrayList<Income> = ArrayList()

    private val _movementsList: MutableLiveData<ArrayList<Income>> = MutableLiveData()
    val movementsList: LiveData<ArrayList<Income>> = _movementsList

    private val _errorMsg: MutableLiveData<String?> = MutableLiveData()
    val errorMsg: LiveData<String?> = _errorMsg

    private val _outMsg: MutableLiveData<String?> = MutableLiveData()
    val outMsg: LiveData<String?> = _outMsg

    private val _inMsg: MutableLiveData<String?> = MutableLiveData()
    val inMsg: LiveData<String?> = _inMsg
    fun loadMovements() {
        var amoutOut = 0.0
        var ammoutIn = 0.0
        movementList.clear()
        viewModelScope.launch {
            val result = repository.loadIncomes()
            result.let { resourceRemote ->
                when (resourceRemote) {
                    is ResourceRemote.Success -> {
                        resourceRemote.data?.documents?.forEach { document ->
                            val serie = document.toObject<Income>()
                            serie?.let {
                                movementList.add(it)
                                ammoutIn += serie.amount!!
                            }
                        }
                        _inMsg.postValue(ammoutIn.toString())
                        val result2 = repository.loadOutcomes()
                        result2.let { resourceRemoteOut ->
                            when (resourceRemoteOut) {
                                is ResourceRemote.Success -> {
                                    resourceRemoteOut.data?.documents?.forEach { document ->
                                        val serie = document.toObject<Income>()
                                        serie?.let {
                                            movementList.add(it)
                                            amoutOut+=serie.amount!!
                                        }
                                    }
                                    movementList.sortBy { it.date }
                                    _movementsList.postValue(movementList)
                                    _outMsg.postValue(amoutOut.toString())
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