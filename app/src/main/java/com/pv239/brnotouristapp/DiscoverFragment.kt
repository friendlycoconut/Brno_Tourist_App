package com.pv239.brnotouristapp

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.pv239.brnotouristapp.databinding.FragmentDiscoverBinding


class DiscoverFragment : Fragment() {

    private var _binding: FragmentDiscoverBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private val districtRepository = DistrictRepository()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentDiscoverBinding.inflate(inflater, container, false)

        districtRepository.loadDistricts()

        val districtList: RecyclerView = binding.districtList
        districtList.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        val districtListAdapter = DistrictListAdapter{district -> districtClicked(district)}
        districtList.adapter = districtListAdapter
        val districtListObserver = Observer<List<District>> {
            districtListAdapter.submitList(it)
        }
        districtRepository.districtList.observe(viewLifecycleOwner, districtListObserver)

        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonShowLocation.setOnClickListener { showAlertDialog(requireContext()) }
    }

    private fun districtClicked(district: District){
        //TODO what happens on district click
        Unit
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
            findNavController().navigate(R.id.action_DiscoverFragment_to_MapFragment)
            dialogInterface.dismiss()
        }

        builder.setNegativeButton(R.string.cancel) { dialogInterface: DialogInterface, i: Int ->
            dialogInterface.cancel()
        }

        builder.create()
        builder.show()
    }

}