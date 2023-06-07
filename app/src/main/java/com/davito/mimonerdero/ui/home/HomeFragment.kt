package com.davito.mimonerdero.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.davito.mimonerdero.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var viewModel: HomeViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentHomeBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this)[HomeViewModel::class.java]

        viewModel.loadBalance()

        viewModel.errorMsg.observe(viewLifecycleOwner) { errorMsg ->
            Toast.makeText(requireContext(), errorMsg, Toast.LENGTH_LONG)
                .show()
        }

        viewModel.balanceMsg.observe(viewLifecycleOwner) {
            binding.HomeTitleTextView.text = buildString {
                append("your balance is $ ")
                append(it)
            }
        }

        with(binding){
            incomeButon.setOnClickListener {
                findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToIncomeFragment())
            }
            outcomeButton.setOnClickListener {
                findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToExpensesFragment())
            }
            MovementsButton.setOnClickListener {
                findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToMovementsFragment())
            }
            reportsButton.setOnClickListener {
                findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToReportsFragment())
            }
        }

        return binding.root
    }

}