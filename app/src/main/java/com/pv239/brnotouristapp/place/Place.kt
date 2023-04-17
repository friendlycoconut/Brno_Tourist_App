package com.pv239.brnotouristapp.place

data class Place(
    val name: String,
    val lat: Double,
    val lng: Double,
    val address: String,
    val imageUrl: String,
    val description: String
)