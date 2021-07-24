package com.pilekumatlar.foodorderingapp.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.FirebaseFirestore
import com.pilekumatlar.foodorderingapp.models.restaurants

class FeedViewModel:ViewModel() {
    val restaurants=MutableLiveData<List<restaurants>>()
    val restaurantsError=MutableLiveData<Boolean>()
    val restaurantsLoading=MutableLiveData<Boolean>()




    fun refreshData(restaurantName:String,restaurantLocation:String){
        val restaurant=restaurants(restaurantName,restaurantLocation)
        val restaurantList= arrayListOf<restaurants>(restaurant)

        restaurants.value=restaurantList
        restaurantsError.value=false
        restaurantsLoading.value=false

    }



}