package com.davito.mimonerdero.ui.reports

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.davito.mimonerdero.data.IncomeOutComeRepository
import com.davito.mimonerdero.data.ResourceRemote
import com.davito.mimonerdero.model.Income
import com.davito.mimonerdero.model.Outcome
import com.google.firebase.firestore.ktx.toObject
import kotlinx.coroutines.launch

class ReportsViewModel : ViewModel() {

    private val incomeOutComeRepository = IncomeOutComeRepository()
    private var outcomeList: ArrayList<Outcome> = ArrayList()
    private var incometList: ArrayList<Income> = ArrayList()

    private val _errorMsg: MutableLiveData<String?> = MutableLiveData()
    val errorMsg: LiveData<String?> = _errorMsg

    private val _incomes: MutableLiveData<String?> = MutableLiveData()
    val incomes: LiveData<String?> = _incomes

    private val _outcomes: MutableLiveData<String?> = MutableLiveData()
    val outcomes: LiveData<String?> = _outcomes

    private val _outcomesList: MutableLiveData<ArrayList<Outcome>> = MutableLiveData()
    val outcomesList: LiveData<ArrayList<Outcome>> = _outcomesList

    private val _incomesList: MutableLiveData<ArrayList<Income>> = MutableLiveData()
    val incomesList: LiveData<ArrayList<Income>> = _incomesList
    fun loadIncomes() {
        var aux = 0.0
        incometList.clear()
        viewModelScope.launch {
            val result = incomeOutComeRepository.loadIncomes()
            result.let { resourceRemote ->
                when(resourceRemote) {
                    is ResourceRemote.Success -> {
                        resourceRemote.data?.documents?.forEach{document ->
                            val income = document.toObject<Income>()
                            income?.let {
                                incometList.add(it)
                                aux = if (income.amount != null) income.amount!!+aux else aux
                            }
                        }
                        _incomesList.postValue(incometList)
                        _incomes.postValue(aux.toString())
                    }
                    is ResourceRemote.Error -> {
                        _errorMsg.postValue(result.message)
                    }else -> {
                    _errorMsg.postValue("omardbonett@gmail.com")
                }
                }
            }
        }
    }

    fun loadOutcomes() {
        var aux = 0.0
        outcomeList.clear()
        viewModelScope.launch {
            val result = incomeOutComeRepository.loadOutcomes()
            result.let { resourceRemote ->
                when(resourceRemote) {
                    is ResourceRemote.Success -> {
                        resourceRemote.data?.documents?.forEach{document ->
                            val income = document.toObject<Outcome>()
                            income?.let {
                                outcomeList.add(it)
                                aux = if (income.amount != null) income.amount!!+aux else aux
                            }
                        }
                        _outcomesList.postValue(outcomeList)
                        _outcomes.postValue(aux.toString())
                    }
                    is ResourceRemote.Error -> {
                        _errorMsg.postValue(result.message)
                    }else -> {
                    _errorMsg.postValue("omardbonett@gmail.com")
                }
                }
            }
        }
    }


}