package com.pv239.brnotouristapp

import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.tasks.CancellationToken
import com.google.android.gms.tasks.CancellationTokenSource
import com.google.android.gms.tasks.OnTokenCanceledListener
import com.pv239.brnotouristapp.api.data.FeaturePointEntity
import com.pv239.brnotouristapp.databinding.FragmentMapBinding
import com.pv239.brnotouristapp.district.District
import com.pv239.brnotouristapp.district.DistrictListAdapter
import com.pv239.brnotouristapp.district.DistrictRepository
import com.pv239.brnotouristapp.repository.GeoPointRepository

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class MapFragment : Fragment() {

    private lateinit var mMap: GoogleMap
    private val districtRepository = DistrictRepository()


    private var _binding: FragmentMapBinding? = null
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var requestPermissionLauncher: ActivityResultLauncher<String>

    private val geoPointRepository: GeoPointRepository by lazy {
        GeoPointRepository(requireContext())
    }


    private val callback = OnMapReadyCallback { googleMap ->
        /**
         * Manipulates the map once available.
         * This callback is triggered when the map is ready to be used.
         * This is where we can add markers or lines, add listeners or move the camera.
         * In this case, we just add a marker near Sydney, Australia.
         * If Google Play services is not installed on the device, the user will be prompted to
         * install it inside the SupportMapFragment. This method will only be triggered once the
         * user has installed Google Play services and returned to the app.
         */
        mMap = googleMap
        showLocation(49.194167, 16.608611)
        geoPointRepository.getGeoPoints(
            success = { featurePointEntities: List<FeaturePointEntity> ->
                addPointFeatureMarkers(
                    featurePointEntities
                )
            },
            fail = { featurePointEntities: List<FeaturePointEntity> ->
                addPointFeatureMarkers(
                    featurePointEntities
                )
            })
        mMap.setOnMarkerClickListener {
            showDetails(it)
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentMapBinding.inflate(inflater, container, false)

        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)

        districtRepository.loadDistricts()


        binding.districtList.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        val districtListAdapter = DistrictListAdapter {
            showLocation(it.lat, it.lng)
        }

        binding.districtList.adapter = districtListAdapter

        val districtListObserver = Observer<List<District>> {
            districtListAdapter.submitList(it)
        }
        districtRepository.districtList.observe(requireActivity(), districtListObserver)


        requestPermissionLauncher =
            registerForActivityResult(
                ActivityResultContracts.RequestPermission()
            ) { isGranted: Boolean ->
                if (!isGranted) {
                    Toast.makeText(context, "Permission needed.", Toast.LENGTH_SHORT).show()
                }
            }
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireContext())
        binding.findLocationButton.setOnClickListener { findLocation() }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun findLocation(){
        if (ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            requestPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
            requestPermissionLauncher.launch(Manifest.permission.ACCESS_COARSE_LOCATION)
        }

        fusedLocationClient.getCurrentLocation(Priority.PRIORITY_HIGH_ACCURACY, object : CancellationToken() {
            override fun onCanceledRequested(p0: OnTokenCanceledListener) = CancellationTokenSource().token
            override fun isCancellationRequested() = false
        })
            .addOnSuccessListener { location: Location? ->
                if (location == null)
                    Toast.makeText(context, "Please turn on the GPS.", Toast.LENGTH_SHORT).show()
                else {
                    showLocation(location.latitude, location.longitude)
                }
            }

    }

    private fun showLocation(lat: Double, lng: Double) {
        mMap.animateCamera(
            CameraUpdateFactory.newCameraPosition(
                CameraPosition.Builder()
                    .target(LatLng(lat, lng))
                    .zoom(15f)
                    .tilt(30f)
                    .build()
            )
        )
    }

    private fun showDetails(marker: Marker): Boolean {
        val place: FeaturePointEntity = marker.tag as FeaturePointEntity
        val action = MapFragmentDirections.actionMapFragmentToPlaceDetails(
            place.name.replace("&nbsp;",  " "),
            place.address.replace("&nbsp;",   " "),
            place.image,
            place.text.replace("&nbsp;",  " ")
        )
        findNavController().navigate(action)
        return true
    }

    private fun addPointFeatureMarkers(featurePointEntities: List<FeaturePointEntity>) {
        featurePointEntities.forEach {

            if (it.name != null && it.latitude != null && it.longitude != null) {
                val latLngPoint = LatLng(it.latitude.toDouble(), it.longitude.toDouble())


                if(this::mMap.isInitialized){
                    mMap.addMarker(
                        MarkerOptions()
                            .snippet(it.name)
                            .icon(selectTypePlace(it.name))
                            .position(latLngPoint)
                            .title(it.name)

                    )?.tag = it
                }

                    }
            }}

    private fun selectTypePlace(placeName: String): BitmapDescriptor{
        if(placeName.contains("muzeum",ignoreCase = true)){return BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE)}
        if(placeName.contains("divadlo",ignoreCase = true)){return BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)}
        if(placeName.contains("kostel",ignoreCase = true)){return BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA)}
        if(placeName.contains("kated",ignoreCase = true)){return BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA)}
        if(placeName.contains("Náměstí", ignoreCase = true)
            ||placeName.contains("Trh", ignoreCase = true)){return BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)}

        return BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)
    }
}


