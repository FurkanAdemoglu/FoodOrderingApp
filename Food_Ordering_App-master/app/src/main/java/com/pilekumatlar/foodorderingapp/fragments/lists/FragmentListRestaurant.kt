package com.pilekumatlar.foodorderingapp.fragments.lists


import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.pilekumatlar.foodorderingapp.R
import com.pilekumatlar.foodorderingapp.databinding.FragmentListRestaurantBinding

import com.pilekumatlar.foodorderingapp.models.Restaurant

class FragmentListRestaurant: Fragment(R.layout.fragment_list_restaurant) {
    lateinit var restaurantRecyclerView: RecyclerView
    private var fragmentListRestaurantBinding: FragmentListRestaurantBinding?=null
    private val adapter=ListAdapter()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews(view)
        getDataFromFirestore()
    }
    override fun onDestroyView() {
        fragmentListRestaurantBinding = null
        super.onDestroyView()
    }

    private fun getDataFromFirestore(){
        val list=ArrayList<Restaurant>()
        var database=FirebaseDatabase.getInstance().reference
        var getdata=object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for(i in snapshot.children){
                    Log.v("index","Çıktı: ${i}")
                    val restaurantLocation=i.child("restaurantLocation").getValue().toString()
                    val restaurantName=i.child("restaurantName").getValue().toString()
                    list.add(
                        Restaurant(
                        restaurantName,
                        restaurantLocation)
                    )


                }
                adapter.setRestaurantData(list)
            }
            override fun onCancelled(error: DatabaseError) {

            }

        }

        database.addListenerForSingleValueEvent(getdata)


    }

    private fun initViews(view: View){
//        fragmentListRestaurantBinding!!.restaurantsRecyclerView.layoutManager=LinearLayoutManager(context)
       restaurantRecyclerView=view.findViewById(R.id.restaurantsRecyclerView)
        restaurantRecyclerView.layoutManager= LinearLayoutManager(context)
        //fragmentListRestaurantBinding!!.restaurantsRecyclerView.adapter=adapter
        restaurantRecyclerView.adapter=adapter
        adapter.setRestaurantOnClickListener(object :IRestaurantOnClickListener{
            override fun onClick(restaurant: Restaurant) {
                Toast.makeText(context,"${restaurant.name}",Toast.LENGTH_SHORT).show()
                /*val action=FragmentListRestaurantDirections.actionFragmentListRestaurantToFragmentDetailRestaurants(
                    restaurant.name,
                    restaurant.imageUrl
                )
                findNavController().navigate(action)*/
            }

        })
    }

}

