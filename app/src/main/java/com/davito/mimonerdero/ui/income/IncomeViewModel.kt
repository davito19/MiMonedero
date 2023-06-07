package com.davito.mimonerdero.ui.income

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.davito.mimonerdero.data.IncomeOutComeRepository
import com.davito.mimonerdero.data.ResourceRemote
import com.davito.mimonerdero.model.Income
import com.google.firebase.firestore.ktx.toObject
import kotlinx.coroutines.launch
import java.time.Instant

class IncomeViewModel : ViewModel() {

    private val incomeOutComeRepository = IncomeOutComeRepository()

    private val _errorMsg: MutableLiveData<String?> = MutableLiveData()
    val errorMsg: LiveData<String?> = _errorMsg

    private val _createIncomeSucces: MutableLiveData<String?> = MutableLiveData()
    val creatIncomeSucces: LiveData<String?> = _createIncomeSucces

    private val _incomes: MutableLiveData<String?> = MutableLiveData()
    val incomes: LiveData<String?> = _incomes

    @RequiresApi(Build.VERSION_CODES.O)
    fun validateData(
        amount: String,
        salary: Boolean,
        loan: Boolean,
        commissions: Boolean,
        others: Boolean
    ) {
        if (amount.isEmpty()) _errorMsg.value = "the amount is required"
        else {
            viewModelScope.launch {
                val income = Income(
                    amount = amount.toDouble(),
                    category = whatCategory(salary,loan,commissions,others),
                    date = Instant.now().toString()
                )
                val  result = incomeOutComeRepository.saveIncome(income)
                result.let { resourceRemote ->
                    when(resourceRemote){
                        is ResourceRemote.Success -> {
                            _errorMsg.postValue("save income")
                            _createIncomeSucces.postValue(result.data)
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
    private fun whatCategory(salary: Boolean,
        loan: Boolean,
        commissions: Boolean,
        others: Boolean) : String {
        if (salary) return "salary"
        if (loan) return "loan"
        if (commissions) return "commission"
        if (others) return "others"
        return "salary"
    }

    fun loadIncomes() {
        var aux = 0.0
        viewModelScope.launch {
            val result = incomeOutComeRepository.loadIncomes()
            result.let { resourceRemote ->
                when(resourceRemote) {
                    is ResourceRemote.Success -> {
                        resourceRemote.data?.documents?.forEach{document ->
                            val income = document.toObject<Income>()
                            income?.let {
                                aux = if (income.amount != null) income.amount!!+aux else aux
                            }
                        }
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

}


