package com.pilekumatlar.foodorderingapp.fragments.lists

import android.app.Dialog
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.firestore.FirebaseFirestore
import com.pilekumatlar.foodorderingapp.R
import com.pilekumatlar.foodorderingapp.databinding.FragmentListRestaurantBinding
import com.pilekumatlar.foodorderingapp.models.restaurants
import com.pilekumatlar.foodorderingapp.viewmodel.FeedViewModel

class FragmentListRestaurant: Fragment(R.layout.fragment_list_restaurant) {
    private lateinit var binding:FragmentListRestaurantBinding
    private val adapter=ListAdapter()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding= FragmentListRestaurantBinding.bind(view)
        initViews(view)
        getDataFromFirebase()
        //getDataFromFirestore()
    }

   private fun getDataFromFirebase(){
        val db=FirebaseFirestore.getInstance()
        db.collection("Restaurants")
            .get()
            .addOnSuccessListener {
                val listTwo=ArrayList<restaurants>()
                for (i in it.documents){
                    val restaurantInformations=i.toObject(restaurants::class.java)!!
                    FeedViewModel().refreshData(restaurantInformations.restaurantName,restaurantInformations.restaurantLocation)
                    listTwo.add(restaurants(restaurantInformations.restaurantName,restaurantInformations.restaurantLocation))
                }
                adapter.setRestaurantDataTwo(listTwo)
            }.addOnFailureListener {
                Log.v("Hata","Hata Alındı")
            } }
    /*private fun getDataFromFirestore(){
        val list=ArrayList<Restaurant>()
        var database=FirebaseDatabase.getInstance().reference
        var getdata=object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for(i in snapshot.children){
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
    }*/

    private fun initViews(view: View){
        binding.addRestaurantButton.setOnClickListener {
            showDialog()
        }
        binding.restaurantsRecyclerView.layoutManager= LinearLayoutManager(context)
        binding.restaurantsRecyclerView.adapter=adapter
        adapter.setRestaurantOnClickListener(object :IRestaurantOnClickListener{
            override fun onClick(restaurants: restaurants) {
                Toast.makeText(context,"${restaurants.restaurantName}",Toast.LENGTH_SHORT).show()
                /*val action=FragmentListRestaurantDirections.actionFragmentListRestaurantToFragmentDetailRestaurants(
                    restaurant.name,
                    restaurant.imageUrl
                )
                findNavController().navigate(action)*/
            }
        }) }

    private fun showDialog() {
        val customDialog = Dialog(requireActivity())
        customDialog.setContentView(R.layout.add_restaurant_dialog)
        customDialog.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        customDialog.show()
    }
}

