package com.pv239.brnotouristapp

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class DistrictRepository {

    private val _districtList = MutableLiveData<List<District>>()
    val districtList: LiveData<List<District>> = _districtList

    fun loadDistricts() {
        val exampleDistricts = listOf(
            District(
                "Střed",
                "https://www.mistopisy.cz/modules/pruvodce/media/village/9136/nature.jpg",
                49.1879143,
                16.5601508
            ),
            District(
                "Řečkovice",
                "https://www.mistopisy.cz/modules/pruvodce/media/village/9136/nature.jpg",
                49.2487669,
                16.5727728
            ),
            District(
                "Líšeň",
                "https://www.mistopisy.cz/modules/pruvodce/media/village/9136/nature.jpg",
                49.215067,
                16.6591306
            ),
            District(
                "Královo Pole",
                "https://www.mistopisy.cz/modules/pruvodce/media/village/9136/nature.jpg",
                49.2273492,
                16.5778692
            ),
            District(
                "Vinohrady",
                "https://www.mistopisy.cz/modules/pruvodce/media/village/9136/nature.jpg",
                49.2075479,
                16.6559426
            ),
            District(
                "Bystrc",
                "https://www.mistopisy.cz/modules/pruvodce/media/village/9136/nature.jpg",
                49.226408,
                16.5223052
            ),
            District(
                "Bohunice",
                "https://www.mistopisy.cz/modules/pruvodce/media/village/9136/nature.jpg",
                49.1703593,
                16.5699176
            )
        )
        _districtList.value = exampleDistricts
    }
}