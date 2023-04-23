package com.pv239.brnotouristapp.api.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "featureTable")
data class FeaturePointEntity(
    @PrimaryKey
    var id: String = "0",
    var name: String = "",
    var text: String = "",
    var image: String = "",
    var url: String = "",
    var tickets: String = "",
    var address: String = "",
    var latitude: String = "0.0",
    var longitude: String = "0.0",
    var address_street: String = "",
    var address_city: String = ""
)
