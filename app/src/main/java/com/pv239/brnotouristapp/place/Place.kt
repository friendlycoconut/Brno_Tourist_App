package com.pv239.brnotouristapp.place

data class Place(
    val name: String,
    val point: String?,
    val lat: Double,
    val lng: Double,
    val address: String,
    val imageUrl: String,
    val text: String
)