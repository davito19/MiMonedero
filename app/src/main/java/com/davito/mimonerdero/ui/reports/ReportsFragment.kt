package com.davito.mimonerdero.ui.reports

import android.graphics.Color
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.davito.mimonerdero.databinding.FragmentReportsBinding
import com.davito.mimonerdero.model.Income
import com.davito.mimonerdero.model.Outcome
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.utils.ColorTemplate

class ReportsFragment : Fragment() {


    private lateinit var binding: FragmentReportsBinding
    private lateinit var viewModel: ReportsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentReportsBinding.inflate(inflater, container, false)
        ViewModelProvider(this)[ReportsViewModel::class.java].also { viewModel = it }

        viewModel.loadOutcomes()
        viewModel.loadIncomes()

        viewModel.errorMsg.observe(viewLifecycleOwner) { errorMsg ->
            Toast.makeText(requireContext(), errorMsg, Toast.LENGTH_LONG)
                .show()
        }

        viewModel.incomes.observe(viewLifecycleOwner){
            binding.incomeAmountTextVie.text = buildString {
                append("$ ")
                append(it)
            }
        }
        viewModel.outcomes.observe(viewLifecycleOwner){
            binding.outcomeAmountTextView.text = buildString {
                append("$ ")
                append(it)
            }
        }
        viewModel.incomesList.observe(viewLifecycleOwner){imcomesList ->
            val pieChart : PieChart = binding.pieChart


            val list: ArrayList<PieEntry> = ArrayList()
            list.add(PieEntry(amountSalary(imcomesList),"Salary"))
            list.add(PieEntry(amountLoan(imcomesList),"loan"))
            list.add(PieEntry(amountCommission(imcomesList),"commission"))
            list.add(PieEntry(amountSalaryOthers(imcomesList),"others"))

            val pieDataSet = PieDataSet(list, "Incomes")

            pieDataSet.setColors(ColorTemplate.MATERIAL_COLORS, 255)

            pieDataSet.valueTextSize = 15f
            pieDataSet.valueTextColor = Color.BLACK

            val pieData = PieData(pieDataSet)

            pieChart.data = pieData
            pieChart.description.text="Pie chart"
            pieChart.centerText="Incomes"
            pieChart.animateY(2000)
        }
        viewModel.outcomesList.observe(viewLifecycleOwner){outcomeList ->
            val pieChart : PieChart = binding.pieChart

            val list: ArrayList<PieEntry> = ArrayList()
            list.add(PieEntry(amountHome(outcomeList),"home"))
            list.add(PieEntry(amountFood(outcomeList),"food"))
            list.add(PieEntry(amountEducation(outcomeList),"education"))
            list.add(PieEntry(amountOther(outcomeList),"others"))

            val pieDataSet = PieDataSet(list, "Outcomes")

            pieDataSet.setColors(ColorTemplate.MATERIAL_COLORS, 255)

            pieDataSet.valueTextSize = 15f
            pieDataSet.valueTextColor = Color.BLACK

            val pieData = PieData(pieDataSet)

            pieChart.data = pieData
            pieChart.description.text="Pie chart"
            pieChart.centerText="Outcomes"
            pieChart.animateY(2000)
        }

        with(binding){
            backImageView.setOnClickListener {
                requireActivity().onBackPressedDispatcher.onBackPressed()
            }
            incomeTile.setOnClickListener {
                viewModel.loadIncomes()
            }
            incomeAmountTextVie.setOnClickListener {
                viewModel.loadIncomes()
            }
            outcomeTitle.setOnClickListener {
                viewModel.loadOutcomes()
            }
            outcomeAmountTextView.setOnClickListener {
                viewModel.loadOutcomes()
            }
        }

        return binding.root
    }

    private fun amountOther(outcomeList: ArrayList<Outcome>): Float {
        var aux =0f
        outcomeList.forEach{
            if (it.category.equals("other")) aux+= it.amount?.toFloat() ?: 0f
        }
        return aux
    }


    private fun amountEducation(outcomeList: ArrayList<Outcome>): Float {
        var aux =0f
        outcomeList.forEach{
            if (it.category.equals("education")) aux+= it.amount?.toFloat() ?: 0f
        }
        return aux
    }

    private fun amountFood(outcomeList: ArrayList<Outcome>): Float {
        var aux =0f
        outcomeList.forEach{
            if (it.category.equals("food")) aux+= it.amount?.toFloat() ?: 0f
        }
        return aux
    }

    private fun amountHome(outcomeList: ArrayList<Outcome>): Float {
        var aux =0f
        outcomeList.forEach{
            if (it.category.equals("home")) aux+= it.amount?.toFloat() ?: 0f
        }
        return aux
    }

    private fun amountSalaryOthers(imcomesList: ArrayList<Income>): Float {
        var aux =0f
        imcomesList.forEach{
            if (it.category.equals("others")) aux+= it.amount?.toFloat() ?: 0f
        }
        return aux
    }

    private fun amountCommission(imcomesList: ArrayList<Income>): Float {
        var aux =0f
        imcomesList.forEach{
            if (it.category.equals("commission")) aux+= it.amount?.toFloat() ?: 0f
        }
        return aux
    }

    private fun amountLoan(imcomesList: ArrayList<Income>): Float {
        var aux =0f
        imcomesList.forEach{
            if (it.category.equals("loan")) aux+= it.amount?.toFloat() ?: 0f
        }
        return aux
    }

    private fun amountSalary(imcomesList: ArrayList<Income>): Float {
        var aux =0f
        imcomesList.forEach{
            if (it.category.equals("salary")) aux+= it.amount?.toFloat() ?: 0f
        }
        return aux
    }

}