package com.internshala.foodapp.adapter

import android.content.Context
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.internshala.foodapp.R
import com.internshala.foodapp.model.Food


class FoodREcylerAdapter(val context: Context, val itemList: ArrayList<Food>) :
    RecyclerView.Adapter<FoodREcylerAdapter.FoodViewHolder>() {


    class FoodViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val txtFoodIndex: TextView = view.findViewById(R.id.txtFoodIndex)
        val txtFoodName: TextView = view.findViewById(R.id.txtFoodName)
        val txtFoodPrice: TextView = view.findViewById(R.id.txtFoodPrice)
        val addButton: Button = view.findViewById(R.id.addButton)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.recycler_food_single_row, parent, false)

        return FoodViewHolder(view)
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    override fun onBindViewHolder(holder: FoodViewHolder, position: Int) {
        Log.d("txt","working")
        val food = itemList[position]
        holder.txtFoodIndex.text = (position+1).toString()
        holder.txtFoodName.text = food.name
        holder.txtFoodPrice.text = "Rs.${food.cost_for_one}"
        holder.addButton.setOnClickListener {
            if(holder.addButton.text.toString()=="Add"){

                holder.addButton.text="Remove"
                holder.addButton.setBackgroundColor(ContextCompat.getColor(context, R.color.remove))
            }
            else{
                holder.addButton.text="Add"
                holder.addButton.setBackgroundColor(ContextCompat.getColor(context, R.color.add))
            }
            Toast.makeText(context,"you    clicked "+ food.name,Toast.LENGTH_LONG).show()
            println("this button ${holder.addButton.text.toString()}")
        }
    }
}