package com.pv239.brnotouristapp.api

import com.pv239.brnotouristapp.api.response.GeoPointResponse
import retrofit2.Call
import retrofit2.http.GET

interface GeoPointWebService {

      @GET("query?outFields=*&where=1%3D1&f=geojson")
     fun getGeoFile() : Call<GeoPointResponse>
}