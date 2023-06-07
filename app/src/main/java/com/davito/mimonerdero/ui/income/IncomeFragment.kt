package com.davito.mimonerdero.ui.income

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.davito.mimonerdero.databinding.FragmentIncomeBinding


class IncomeFragment : Fragment() {


    private lateinit var binding: FragmentIncomeBinding
    private lateinit var viewModel: IncomeViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel = ViewModelProvider(this)[IncomeViewModel::class.java]
        binding = FragmentIncomeBinding.inflate(inflater, container, false)

        viewModel.errorMsg.observe(viewLifecycleOwner){ errorMsg ->
            Toast.makeText(requireContext(), errorMsg, Toast.LENGTH_LONG)
                .show()
        }

        viewModel.creatIncomeSucces.observe(viewLifecycleOwner) {
            binding.incomeEditText.text.clear()
            viewModel.loadIncomes()
        }

        viewModel.incomes.observe(viewLifecycleOwner){
            binding.incomesTitleTextView.text = buildString {
                append("Ingresos: $ ")
                append(it)
            }
        }

        viewModel.loadIncomes()

        with(binding){
            backImageView.setOnClickListener {
                requireActivity().onBackPressedDispatcher.onBackPressed()
            }
            cancelButtonTextView.setOnClickListener {
                requireActivity().onBackPressedDispatcher.onBackPressed()
            }
            saveButonTextView.setOnClickListener {
                viewModel.validateData(
                    incomeEditText.text.toString(),
                    salaryRadioButton.isChecked,
                    loanRadioButton.isChecked,
                    commissionsRadioButton.isChecked,
                    othersRadioButtton.isChecked,
                )
            }
        }


        return binding.root
    }


}