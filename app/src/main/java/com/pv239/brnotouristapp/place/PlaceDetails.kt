package com.pv239.brnotouristapp.place

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import coil.load
import com.pv239.brnotouristapp.databinding.FragmentPlaceDetailsBinding

class PlaceDetails : Fragment() {

    private var _binding: FragmentPlaceDetailsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPlaceDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.placeNameTextView.text = PlaceDetailsArgs.fromBundle(requireArguments()).name
        binding.placeAddressTextView.text = PlaceDetailsArgs.fromBundle(requireArguments()).address
        binding.placeDescriptionTextView.text = PlaceDetailsArgs.fromBundle(requireArguments()).description
        binding.imageView.load(PlaceDetailsArgs.fromBundle(requireArguments()).imageUrl)
    }

}

