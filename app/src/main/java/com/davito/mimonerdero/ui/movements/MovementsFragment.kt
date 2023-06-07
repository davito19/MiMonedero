package com.davito.mimonerdero.ui.movements

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.davito.mimonerdero.databinding.FragmentMovementsBinding
import com.davito.mimonerdero.model.Income

class MovementsFragment : Fragment() {


    private lateinit var binding: FragmentMovementsBinding
    private lateinit var viewModel: MovementsViewModel
    private var list : ArrayList<Income> = ArrayList()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMovementsBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this)[MovementsViewModel::class.java]

        val movementsAdapter = MovementsAdapter(
            movementsList = list,
            onItemClicked = {}
        )

        binding.movementsRecliclerView.apply {
            layoutManager = LinearLayoutManager(this@MovementsFragment.requireContext())
            adapter = movementsAdapter
            setHasFixedSize(false)
        }

        binding.backImageView.setOnClickListener {
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }

        viewModel.loadMovements()


        viewModel.errorMsg.observe(viewLifecycleOwner) { errorMsg ->
            Toast.makeText(requireContext(), errorMsg, Toast.LENGTH_LONG)
                .show()
        }

        viewModel.movementsList.observe(viewLifecycleOwner) {list ->
            movementsAdapter.appendItems(list)
        }

        viewModel.outMsg.observe(viewLifecycleOwner){
            binding.outcomeAmountTextView.text = buildString {
                append("$ ")
                append(it)
            }
        }

        viewModel.inMsg.observe(viewLifecycleOwner){
            binding.incomeAmountTextVie.text = buildString {
                append("$ ")
                append(it)
            }
        }

        return binding.root
    }

}