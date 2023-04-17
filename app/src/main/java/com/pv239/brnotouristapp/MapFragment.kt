package com.pv239.brnotouristapp

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.pv239.brnotouristapp.databinding.FragmentMapBinding
import com.pv239.brnotouristapp.district.District
import com.pv239.brnotouristapp.district.DistrictListAdapter
import com.pv239.brnotouristapp.district.DistrictRepository

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class MapFragment : Fragment() {

//    private lateinit var mMap: GoogleMap
    private val districtRepository = DistrictRepository()

    private var _binding: FragmentMapBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

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
        val brno = LatLng(49.195061, 16.606836)
        googleMap.addMarker(MarkerOptions().position(brno).title("Marker in Brno"))
        val cameraPosition = CameraPosition.Builder()
            .target(brno)
            .zoom(12.5f)
            .tilt(30f)
            .build()
        googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition))
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMapBinding.inflate(inflater, container, false)

//        view?.findViewById<MapView>(R.id.map)?.getMapAsync(this)

        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)

        districtRepository.loadDistricts()

        binding.districtList.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        val districtListAdapter = DistrictListAdapter {
//            mMap.animateCamera(
//                CameraUpdateFactory.newCameraPosition(
//                    CameraPosition.Builder()
//                        .target(LatLng(it.lat, it.lng))
//                        .zoom(12.5f)
//                        .tilt(30f)
//                        .build()
//                )
//            )

        }

        binding.districtList.adapter = districtListAdapter

        val districtListObserver = Observer<List<District>> {
            districtListAdapter.submitList(it)
        }
        districtRepository.districtList.observe(requireActivity(), districtListObserver)

        binding.findLocationButton.setOnClickListener { showAlertDialog(requireContext()) }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun showAlertDialog(context : Context){
        val builder = AlertDialog.Builder(context)
        builder.setTitle("Are you sure?")
            .setMessage("You will share your GPS data!")

        builder.setPositiveButton(R.string.confirm) { dialogInterface: DialogInterface, i: Int ->
            dialogInterface.dismiss()
        }

        builder.setNegativeButton(R.string.cancel) { dialogInterface: DialogInterface, i: Int ->
            dialogInterface.cancel()
        }

        builder.create()
        builder.show()
    }
}