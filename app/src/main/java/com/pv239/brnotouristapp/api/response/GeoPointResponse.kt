package com.pv239.brnotouristapp.api.response


data class GeoPointResponse(
    val type: String,
    val features: List<Feature>
)


data class Feature(
    val type: String,
    val id: String,
    val geometry: GeometryType,
    val properties: PropertyType
)

data class GeometryType(
    val type: String,
    val coordinates: List<String>
)

data class PropertyType(
    val name: String,
    val text: String,
    val image: String,
    val url: String,
    val contact_email: String,
    val contact_website: String,
    val tickets: String,
    val contact_phone: String,
    val address: String,
    val latitude: String,
    val longitude: String,
    val ObjectId: String,
    val GlobalId: String,
    val address_street: String,
    val address_city: String
)