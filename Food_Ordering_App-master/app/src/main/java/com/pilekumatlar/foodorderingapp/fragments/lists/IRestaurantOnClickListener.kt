package com.pilekumatlar.foodorderingapp.fragments.lists

import com.pilekumatlar.foodorderingapp.models.Restaurant


interface IRestaurantOnClickListener {
    fun onClick(name: Restaurant)
}