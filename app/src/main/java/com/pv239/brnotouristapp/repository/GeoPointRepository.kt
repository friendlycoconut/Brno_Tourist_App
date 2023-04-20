package com.pv239.brnotouristapp.repository

import android.util.Log
import com.pv239.brnotouristapp.api.GeoPointWebService
import com.pv239.brnotouristapp.api.RetrofitUtil
import com.pv239.brnotouristapp.api.response.GeoPointResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.Calendar

class GeoPointRepository(
    private val geoPointWebService: GeoPointWebService = RetrofitUtil.createGeoResource()
) {

     fun getGeoPoints() {

        geoPointWebService.getGeoFile().enqueue(object : Callback<GeoPointResponse> {
            override fun onResponse(call: Call<GeoPointResponse>, response: Response<GeoPointResponse>) {
                val responseBody = response.body()

                if (response.isSuccessful && responseBody != null) {
                    Log.d( responseBody.features.get(1).geometry.coordinates.toString(), responseBody.features.get(1).geometry.coordinates.toString())
                } else {
                    Log.e(this::class.simpleName, "body was null")

                }
            }

            override fun onFailure(call: Call<GeoPointResponse>, t: Throwable) {
                TODO("Not yet implemented")
            }


        })
    }

}