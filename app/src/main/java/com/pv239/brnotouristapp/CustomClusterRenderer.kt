package com.pv239.brnotouristapp

import android.content.Context
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.MarkerOptions
import com.google.maps.android.clustering.ClusterManager
import com.google.maps.android.clustering.view.DefaultClusterRenderer
import com.pv239.brnotouristapp.api.data.FeaturePointEntity

class CustomClusterRenderer(
    context: Context?,
    map: GoogleMap?,
    clusterManager: ClusterManager<FeaturePointEntity>?
) : DefaultClusterRenderer<FeaturePointEntity>(context, map, clusterManager) {

    override fun onBeforeClusterItemRendered(
        item: FeaturePointEntity,
        markerOptions: MarkerOptions
    ) {
        markerOptions.icon(selectTypePlace(item.name))
        super.onBeforeClusterItemRendered(item, markerOptions)
    }

    private fun selectTypePlace(placeName: String): BitmapDescriptor {
        if(placeName.contains("muzeum",ignoreCase = true)){return BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE)}
        if(placeName.contains("divadlo",ignoreCase = true)){return BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)}
        if(placeName.contains("kostel",ignoreCase = true)){return BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA)}
        if(placeName.contains("kated",ignoreCase = true)){return BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA)}
        if(placeName.contains("Náměstí", ignoreCase = true)
            ||placeName.contains("Trh", ignoreCase = true)){return BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)}

        return BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)
    }

}