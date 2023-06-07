package com.davito.mimonerdero.ui.expenses

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.davito.mimonerdero.data.IncomeOutComeRepository
import com.davito.mimonerdero.data.ResourceRemote
import com.davito.mimonerdero.model.Outcome
import com.google.firebase.firestore.ktx.toObject
import kotlinx.coroutines.launch
import java.time.Instant


class ExpensesViewModel : ViewModel() {
    private val incomeOutComeRepository = IncomeOutComeRepository()

    private val _errorMsg: MutableLiveData<String?> = MutableLiveData()
    val errorMsg: LiveData<String?> = _errorMsg

    private val _createOutcomeSucces: MutableLiveData<String?> = MutableLiveData()
    val creatOutcomeSucces: LiveData<String?> = _createOutcomeSucces

    private val _outcomes: MutableLiveData<String?> = MutableLiveData()
    val outcomes: LiveData<String?> = _outcomes

    @RequiresApi(Build.VERSION_CODES.O)
    fun validateData(
        amount: String,
        home: Boolean,
        food: Boolean,
        education: Boolean,
        others: Boolean
    ) {
        if (amount.isEmpty()) _errorMsg.value = "the amount is required"
        else {
            viewModelScope.launch {
                val outcome = Outcome(
                    amount = amount.toDouble(),
                    category = whatCategory(home,food,education,others),
                    date = Instant.now().toString()
                )
                val  result = incomeOutComeRepository.saveOutcome(outcome)
                result.let { resourceRemote ->
                    when(resourceRemote){
                        is ResourceRemote.Success -> {
                            _errorMsg.postValue("save outcome")
                            _createOutcomeSucces.postValue(result.data)
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
        if (salary) return "home"
        if (loan) return "food"
        if (commissions) return "education"
        if (others) return "others"
        return "home"
    }

    fun loadOutcomes() {
        var aux = 0.0
        viewModelScope.launch {
            val result = incomeOutComeRepository.loadOutcomes()
            result.let { resourceRemote ->
                when(resourceRemote) {
                    is ResourceRemote.Success -> {
                        resourceRemote.data?.documents?.forEach{document ->
                            val income = document.toObject<Outcome>()
                            income?.let {
                                aux = if (income.amount != null) income.amount!!+aux else aux
                            }
                        }
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