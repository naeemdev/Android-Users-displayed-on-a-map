package com.appsolace.androidtask

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.appsolace.androidtask.model.UserResponseModel
import com.appsolace.androidtask.repo.ResponseListener
import com.appsolace.androidtask.utils.isNetworkAvailable
import com.appsolace.androidtask.viewmodel.UserViewModel

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import java.util.ArrayList

class MapsActivity : AppCompatActivity(), OnMapReadyCallback ,ResponseListener{

    private lateinit var mMap: GoogleMap
    var mUserResponseModellist: MutableList<UserResponseModel> = ArrayList<UserResponseModel>()
    var viewModel: UserViewModel?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //making this activity full screen
        window.requestFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.activity_maps)
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
        viewModel= ViewModelProvider(this).get(UserViewModel::class.java)
        if (isNetworkAvailable(this)){
            //calling ai from view and get response
            viewModel!!.getusers(this).observe(this, Observer { mlistuser->
                mUserResponseModellist.addAll(mlistuser as Array<out UserResponseModel>)
            })
        }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        // Add a marker in Sydney and move the camera
        val sydney = LatLng(-34.0, 151.0)
        mMap.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"))
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))
    }

    override fun onError(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}
