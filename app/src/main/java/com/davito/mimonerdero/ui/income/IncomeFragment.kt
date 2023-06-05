package com.davito.mimonerdero.ui.income

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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


        return binding.root
    }


}