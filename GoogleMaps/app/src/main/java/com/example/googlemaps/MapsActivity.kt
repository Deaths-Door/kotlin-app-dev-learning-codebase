package com.example.googlemaps

import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import android.content.Context
import android.location.Location
import android.location.LocationManager
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.example.googlemaps.databinding.ActivityMapsBinding
import com.google.android.gms.maps.model.PolylineOptions

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityMapsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.\
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
      //  (map as SupportMapFragment).getMapAsync(this)
    }

    override fun onStart() {
        requestAccess()
        super.onStart()
        if(checkSelfPermission(android.Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED && checkSelfPermission(android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED)
            setUpLocationListener()
        else requestAccess()
    }
    private val REQUEST_CODE = 69
    private fun requestAccess() {
        this.requestPermissions(arrayOf(android.Manifest.permission.ACCESS_COARSE_LOCATION,android.Manifest.permission.ACCESS_FINE_LOCATION),REQUEST_CODE)
    }
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if(requestCode == REQUEST_CODE){
            if(grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //location enabled
                if()
            }
            else Toast.makeText(this,"Permission Denied",Toast.LENGTH_SHORT).show()
        }
    }

    private fun setUpLocationListener() {
        (getSystemService(Context.LOCATION_SERVICE) as LocationManager).also {
            var location :Location?= null
            val providers = it.getProviders(true)
            for(x in providers.indices.reversed()){
                location = it.getLastKnownLocation(providers[x])
                if(location != null) break
            }

            location?.let {
                if(::mMap.isInitialized){
                    val current = LatLng(it.latitude, it.longitude)
                    mMap.addMarker(MarkerOptions().position(current).title("Marker for current location"))
                    mMap.moveCamera(CameraUpdateFactory.newLatLng(current))
                }
            }
        }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        mMap.uiSettings.apply {
            isZoomControlsEnabled = true
            isZoomGesturesEnabled = true
            isMyLocationButtonEnabled = true
            isCompassEnabled = true
        }

        mMap.setMaxZoomPreference(23f)
        mMap.setMinZoomPreference(30f)

        // Add a marker in Sydney and move the camera
        val sydney = LatLng(-34.0, 151.0)
        mMap.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"))
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))

        mMap.addPolyline(
            PolylineOptions()
                .add(sydney,LatLng(20.59,78.39))
                .color(R.color.teal_200)
        ).width = 2.5f
    }
}