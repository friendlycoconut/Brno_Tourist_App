package com.pv239.brnotouristapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.pv239.brnotouristapp.databinding.FragmentMapBinding

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class MapFragment : Fragment(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private val districtRepository = DistrictRepository()

    private var _binding: FragmentMapBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMapBinding.inflate(inflater, container, false)

        view?.findViewById<MapView>(R.id.map)?.getMapAsync(this)

        districtRepository.loadDistricts()

        binding.districtList.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        val districtListAdapter = DistrictListAdapter {
            mMap.animateCamera(
                CameraUpdateFactory.newCameraPosition(
                    CameraPosition.Builder()
                        .target(LatLng(it.lat, it.lng))
                        .zoom(12.5f)
                        .tilt(30f)
                        .build()
                )
            )
        }

        binding.districtList.adapter = districtListAdapter

        val districtListObserver = Observer<List<District>> {
            districtListAdapter.submitList(it)
        }
        districtRepository.districtList.observe(requireActivity(), districtListObserver)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        // Add a marker in Sydney and move the camera
        val brno = LatLng(49.195061, 16.606836)
        mMap.addMarker(MarkerOptions().position(brno).title("Marker in Brno"))
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(brno))
        val cameraPosition = CameraPosition.Builder()
            .target(brno)
            .zoom(12.5f)
            .tilt(30f)
            .build()
        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition))
    }

}