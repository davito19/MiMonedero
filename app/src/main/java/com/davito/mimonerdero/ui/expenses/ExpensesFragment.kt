package com.davito.mimonerdero.ui.expenses

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.davito.mimonerdero.databinding.FragmentExpensesBinding


class ExpensesFragment : Fragment() {


    private lateinit var binding: FragmentExpensesBinding
    private lateinit var viewModel: ExpensesViewModel
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel = ViewModelProvider(this)[ExpensesViewModel::class.java]
        binding = FragmentExpensesBinding.inflate(inflater, container, false)

        viewModel.errorMsg.observe(viewLifecycleOwner){ errorMsg ->
            Toast.makeText(requireContext(), errorMsg, Toast.LENGTH_LONG)
                .show()
        }

        viewModel.creatOutcomeSucces.observe(viewLifecycleOwner) {
            binding.outcomeEditText.text.clear()
            viewModel.loadOutcomes()
        }

        viewModel.outcomes.observe(viewLifecycleOwner){
            binding.incomesTitleTextView.text = buildString {
                append("Outcome: $ ")
                append(it)
            }
        }

        viewModel.loadOutcomes()

        with(binding){
            backImageView.setOnClickListener {
                requireActivity().onBackPressedDispatcher.onBackPressed()
            }
            cancelButtonTextView.setOnClickListener {
                requireActivity().onBackPressedDispatcher.onBackPressed()
            }
            saveButonTextView.setOnClickListener {
                viewModel.validateData(
                    outcomeEditText.text.toString(),
                    homeRadioButton.isChecked,
                    foodRadioButton.isChecked,
                    educationRadioButton.isChecked,
                    othersRadioButtton.isChecked,
                )
            }
        }

        return binding.root
    }


}