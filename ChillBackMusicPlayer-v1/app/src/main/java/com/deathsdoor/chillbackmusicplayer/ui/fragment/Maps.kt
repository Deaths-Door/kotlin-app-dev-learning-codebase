package com.deathsdoor.chillbackmusicplayer.ui.fragment

import android.annotation.SuppressLint
import android.location.Geocoder
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.deathsdoor.chillbackmusicplayer.R
import com.deathsdoor.chillbackmusicplayer.data.extensions.Extensions.changeStatusBarColor
import com.deathsdoor.chillbackmusicplayer.data.extensions.Extensions.showToast
import com.deathsdoor.chillbackmusicplayer.data.extensions.UI.changeVisibility
import com.deathsdoor.chillbackmusicplayer.data.extensions.debugLog
import com.deathsdoor.chillbackmusicplayer.data.extensions.export.ClickListeners.onClick
import com.deathsdoor.chillbackmusicplayer.databinding.FragmentMapsBinding
import com.google.android.exoplayer2.ui.PlayerControlView
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.material.imageview.ShapeableImageView
import java.util.*

class Maps : Fragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        changeStatusBarColor(R.color.green)
    }

    private lateinit var _binding : FragmentMapsBinding
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentMapsBinding.inflate(inflater,container,false)
        return _binding.root
    }

    private lateinit var googleMap: GoogleMap
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var mainExoplayer:PlayerControlView
    private lateinit var mircophoneButton : ShapeableImageView
    @SuppressLint("MissingPermission")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mainExoplayer =  requireActivity().findViewById(R.id.exoplayer)
        mircophoneButton =  requireActivity().findViewById(R.id.regconizeSongMicroPhone)
        mainExoplayer.changeVisibility()
        mircophoneButton.changeVisibility()

        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync {
            googleMap = it
            googleMap.mapType = GoogleMap.MAP_TYPE_HYBRID
            googleMap.isMyLocationEnabled = true
            googleMap.setOnCameraIdleListener {
                _binding.currentLocation.text = viewingLocation()
            }

            _binding.buttonMyLocation.onClick {
                fusedLocationClient.lastLocation.addOnSuccessListener { location ->
                    if(location == null) context?.showToast("Current Location Unavailable")
                    else googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(LatLng(location.latitude,location.longitude),15f))
                }
            }
        }
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity())

        _binding.myConcertsImage.onClick { findNavController().navigate(R.id.actionNachMyConcerts) }
        _binding.myConcertsText.onClick { findNavController().navigate(R.id.actionNachMyConcerts) }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        mainExoplayer.changeVisibility()
        mircophoneButton.changeVisibility()
    }

    private fun viewingLocation(): String {
        val cameraPosition: CameraPosition = googleMap.cameraPosition
        val target: LatLng = cameraPosition.target
        return "" //locationName(target.latitude, target.longitude, cameraPosition.zoom)
    }

    private fun locationName(latitude: Double, longitude: Double, zoom: Float): String {
        val geocoder = Geocoder(requireContext(), Locale.getDefault())
        val maxResults = if (zoom > 10) 1 else 5
        val list = geocoder.getFromLocation(latitude, longitude, maxResults)
        val address = list?.first()
        "Google Maps".debugLog(latitude)
        "Google Maps".debugLog(longitude)
        "Google Maps".debugLog(list)
        return if (zoom > 10) "${address?.locality}, ${address?.countryName}"
        else "${address?.countryName}"
    }
}