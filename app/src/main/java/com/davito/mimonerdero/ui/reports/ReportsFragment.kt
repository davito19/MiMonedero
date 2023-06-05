package com.davito.mimonerdero.ui.reports

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.davito.mimonerdero.databinding.FragmentReportsBinding

class ReportsFragment : Fragment() {


    private lateinit var binding: FragmentReportsBinding
    private lateinit var viewModel: ReportsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentReportsBinding.inflate(inflater, container, false)
        ViewModelProvider(this)[ReportsViewModel::class.java].also { viewModel = it }
        return binding.root
    }

}