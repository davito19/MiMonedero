package com.davito.mimonerdero.ui.movements

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.davito.mimonerdero.databinding.FragmentMovementsBinding

class MovementsFragment : Fragment() {


    private lateinit var binding: FragmentMovementsBinding
    private lateinit var viewModel: MovementsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMovementsBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this)[MovementsViewModel::class.java]
        return binding.root
    }

}