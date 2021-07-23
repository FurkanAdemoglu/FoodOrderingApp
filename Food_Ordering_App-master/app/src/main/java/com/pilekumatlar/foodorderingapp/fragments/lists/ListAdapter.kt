package com.pilekumatlar.foodorderingapp.fragments.lists

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.pilekumatlar.foodorderingapp.R
import com.pilekumatlar.foodorderingapp.models.Restaurant

class ListAdapter:RecyclerView.Adapter<ListAdapter.ListViewHolder>() {
    private var list=ArrayList<Restaurant>()
    private var listener:IRestaurantOnClickListener?=null

    class ListViewHolder(val view: View):RecyclerView.ViewHolder(view){

        val nameTextView=view.findViewById<TextView>(R.id.nameTextView)
        //val avatarImageView=view.findViewById<ImageView>(R.id.avatarImageView)
        val locationTextView=view.findViewById<TextView>(R.id.locationTextView)
        val containerCardView=view.findViewById<CardView>(R.id.containerCardView)

        fun bind(restaurant: Restaurant,listener: IRestaurantOnClickListener?){
            nameTextView.text=restaurant.name
            locationTextView.text=restaurant.location
            containerCardView.setOnClickListener { listener?.onClick(restaurant)}
          //  Glide.with(view.context).load(restaurant.imageUrl).into(avatarImageView)

        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val view= LayoutInflater.from(parent.context).inflate(R.layout.item_restaurants,parent,false)
        return ListViewHolder(view)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        var item=this.list[position]
        holder.bind(item,listener)
    }

    override fun getItemCount(): Int =list.size

    fun setRestaurantData(list:ArrayList<Restaurant>){
        this.list=list
        notifyDataSetChanged()
    }
    fun setRestaurantOnClickListener(listener:IRestaurantOnClickListener){
        this.listener=listener
    }

}