package com.pv239.brnotouristapp

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
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
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.tasks.CancellationToken
import com.google.android.gms.tasks.CancellationTokenSource
import com.google.android.gms.tasks.OnTokenCanceledListener
import com.google.maps.android.clustering.ClusterManager
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
    private lateinit var clusterManager: ClusterManager<FeaturePointEntity>
    private var isMapInitialized : Boolean = false


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

    @SuppressLint("PotentialBehaviorOverride")
    private val callback = OnMapReadyCallback { googleMap ->
        if(isMapInitialized) return@OnMapReadyCallback
        mMap = googleMap

        clusterManager = ClusterManager(activity,googleMap)
        clusterManager.renderer = CustomClusterRenderer(activity, googleMap, clusterManager)
        clusterManager.setOnClusterItemClickListener { showDetails(it) }
        clusterManager.setOnClusterClickListener {
            showLocation(it.position.latitude,it.position.longitude)
            true
        }
        clusterManager.setAnimation(false)

        geoPointRepository.getGeoPoints(
            success = { clusterManager.addItems(it) },
            fail = { clusterManager.addItems(it) }
        )

        clusterManager.cluster()
        mMap.setOnCameraIdleListener(clusterManager)
        mMap.setOnMarkerClickListener(clusterManager)
        mMap.uiSettings.isMyLocationButtonEnabled = false

        showLocation(49.194167, 16.608611)
        isMapInitialized = true
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentMapBinding.inflate(inflater, container, false)

        val isOnline = activity?.getSharedPreferences("preferences", Context.MODE_PRIVATE)?.getBoolean("isOnline", false) ?: false

        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        
        mapFragment?.getMapAsync(callback)

        districtRepository.loadDistricts()



        requestPermissionLauncher =
            registerForActivityResult(
                ActivityResultContracts.RequestPermission()
            ) { isGranted: Boolean ->
                if (!isGranted) {
                    Toast.makeText(context, "Permission needed.", Toast.LENGTH_SHORT).show()
                }
            }
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireContext())

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

        if (!isOnline) binding.findLocationButton.isEnabled = false

        binding.findLocationButton.setOnClickListener { findLocation() }

        return binding.root
    }

    override fun onResume() {
        super.onResume()
        (activity as AppCompatActivity?)?.supportActionBar?.hide()
    }

    override fun onStop() {
        super.onStop()
        (activity as AppCompatActivity?)?.supportActionBar?.show()
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
                if (location == null) {
                    mMap.isMyLocationEnabled = false
                    Toast.makeText(context, "Please turn on the GPS.", Toast.LENGTH_SHORT).show()
                } else {
                    mMap.isMyLocationEnabled = true
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
                    .build()
            )
        )
    }

    private fun showDetails(pointEntity: FeaturePointEntity): Boolean {
        val place: FeaturePointEntity = pointEntity
        val action = MapFragmentDirections.actionMapFragmentToPlaceDetails(
            place.name.replace("&nbsp;",  " "),
            place.address.replace("&nbsp;",   " "),
            place.image,
            place.text.replace("&nbsp;",  " ")
        )
        findNavController().navigate(action)
        return true
    }

}