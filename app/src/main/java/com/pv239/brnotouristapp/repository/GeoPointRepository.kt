package com.pv239.brnotouristapp.repository

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.pv239.brnotouristapp.api.GeoPointWebService
import com.pv239.brnotouristapp.api.RetrofitUtil
import com.pv239.brnotouristapp.api.data.FeaturePointEntity
import com.pv239.brnotouristapp.api.response.GeoPointResponse
import com.pv239.brnotouristapp.dao.FeatureDao
import com.pv239.brnotouristapp.database.FeatureDatabase
import com.pv239.brnotouristapp.district.District
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class GeoPointRepository(
    context: Context,
    private val geoPointWebService: GeoPointWebService = RetrofitUtil.createGeoResource(),
    private val dao: FeatureDao = FeatureDatabase.create(context).featureDao()
) {


    private val _featurePointList = MutableLiveData<List<FeaturePointEntity>>()
    val featurePointList: LiveData<List<FeaturePointEntity>> = _featurePointList

    fun getGeoPoints(
        success: (List<FeaturePointEntity>) -> Unit,
        fail: (List<FeaturePointEntity>) -> Unit
    ) {


        geoPointWebService.getGeoFile().enqueue(object : Callback<GeoPointResponse> {
            override fun onResponse(
                call: Call<GeoPointResponse>,
                response: Response<GeoPointResponse>
            ) {
                val responseBody = response.body()

                if (response.isSuccessful && responseBody != null) {
                    var featurePointEntityList: List<FeaturePointEntity> =
                        mapGeoResponse(responseBody)
                    featurePointEntityList.forEach { featurePointEntity ->
                        dao.persist(featurePointEntity)
                    }
                    success(featurePointEntityList)
                } else {
                    Log.e(this::class.simpleName, "body was null")

                }
            }

            override fun onFailure(call: Call<GeoPointResponse>, t: Throwable) {
                var featurePointEntityList: List<FeaturePointEntity> = dao.selectAllPoints()
                fail(featurePointEntityList)
            }
        })
    }


    public fun mapGeoResponse(response: GeoPointResponse): List<FeaturePointEntity> {
        val featurePointEntityList: ArrayList<FeaturePointEntity> = arrayListOf()

        response.features.forEach {
            if (it.properties.address != null && it.properties.name != null) {

                val newFeaturePointEntity = FeaturePointEntity(
                    it.id,
                    it.properties.name,
                    it.properties.text,
                    it.properties.image ?: "",
                    it.properties.url ?: "",
                    it.properties.tickets ?: "",
                    it.properties.address ?: "",
                    it.properties.latitude ?: "",
                    it.properties.longitude ?: "",
                    it.properties.address_street ?: "",
                    it.properties.address_city ?: ""
                )

                featurePointEntityList.add(newFeaturePointEntity)
            }
        }

        return featurePointEntityList

    }


}