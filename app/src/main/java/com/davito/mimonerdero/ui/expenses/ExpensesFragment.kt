package com.davito.mimonerdero.ui.expenses

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.davito.mimonerdero.databinding.FragmentExpensesBinding


class ExpensesFragment : Fragment() {


    private lateinit var binding: FragmentExpensesBinding
    private lateinit var viewModel: ExpensesViewModel
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel = ViewModelProvider(this)[ExpensesViewModel::class.java]
        binding = FragmentExpensesBinding.inflate(inflater, container, false)


        return binding.root
    }


}