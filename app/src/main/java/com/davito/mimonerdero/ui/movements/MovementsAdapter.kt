package com.davito.mimonerdero.ui.movements

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.davito.mimonerdero.R
import com.davito.mimonerdero.databinding.CardViewBalanceItemBinding
import com.davito.mimonerdero.model.Income

class MovementsAdapter(
    private val movementsList : ArrayList<Income>,
    private val onItemClicked : (Income) -> Unit
) : RecyclerView.Adapter<MovementsAdapter.MovementsViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovementsViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.card_view_balance_item, parent, false)
        return MovementsViewHolder(view)
    }

    override fun getItemCount(): Int = movementsList.size


    override fun onBindViewHolder(holder: MovementsViewHolder, position: Int) {
        val movement = movementsList[position]
        holder.bind(movement)
        holder.itemView.setOnClickListener { onItemClicked(movement) }

    }

    fun appendItems(newList: ArrayList<Income>){
        movementsList.clear()
        movementsList.addAll(newList)
        notifyDataSetChanged()
    }

    class MovementsViewHolder(itemView: View) :  RecyclerView.ViewHolder(itemView){

        private val binding = CardViewBalanceItemBinding.bind(itemView)

        fun bind(movement : Income){
            with(binding){
                dateTextView.text = movement.date.toString().substring(0,10)
                categoryTextView.text = movement.category
                amountTextView.text = buildString {
                    append("$ ")
                    append(movement.amount.toString())
                }
                if (movement.category.toString() in "otherhomefoodeducation"){
                    binding.view.setBackgroundColor(Color.parseColor("#ff0000"))
                }
            }
        }
    }
}