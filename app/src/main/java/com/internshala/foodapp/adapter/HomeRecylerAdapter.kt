package com.internshala.foodapp.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.internshala.foodapp.R
import com.internshala.foodapp.activity.RestaurantMenu
import com.internshala.foodapp.model.restaurant
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.recyler_home_single_row.view.*

class HomeRecylerAdapter(val context: Context, val itemList: ArrayList<restaurant>) :
    RecyclerView.Adapter<HomeRecylerAdapter.HomeViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.recyler_home_single_row, parent, false)

        return HomeViewHolder(view)
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    override fun onBindViewHolder(holder: HomeViewHolder, position: Int) {
        val restaurant = itemList[position]
        holder.txtRestaurantName.text = restaurant.restaurantName
        holder.txtRestaurantPrice.text = "${restaurant.restaurantPrice}/person"
        holder.txtRestaurantRating.text = restaurant.restaurantRating

        Picasso.get().load(restaurant.restaurantImage).error(R.drawable.ic_launcher_foreground)
            .into(holder.imgRestaurantImage)

        holder.llContent.setOnClickListener {
            val intent = Intent(context, RestaurantMenu::class.java)
            intent.putExtra("restaurant_id", restaurant.restaurantId)
            intent.putExtra("restaurant_name",restaurant.restaurantName)
            context.startActivity(intent)
        }
    }

    class HomeViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val txtRestaurantName: TextView = view.findViewById(R.id.txtRestaurantName)
        val txtRestaurantPrice: TextView = view.findViewById(R.id.txtRestaurantPrice)
        val txtRestaurantRating: TextView = view.findViewById(R.id.txtRestaurantRating)
        val imgRestaurantImage: ImageView = view.findViewById(R.id.imgRestaurantImage)
        val llContent: LinearLayout = view.findViewById(R.id.llContent)
    }
}