package com.appsolace.androidtask

import android.os.Bundle
import android.util.Log
import android.view.Window
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.appsolace.androidtask.model.UserResponseModel
import com.appsolace.androidtask.repo.ResponseListener
import com.appsolace.androidtask.utils.isNetworkAvailable
import com.appsolace.androidtask.viewmodel.UserViewModel
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import java.util.*


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
               // mUserResponseModellist.addAll(mlistuser as Array<out UserResponseModel>)
                Log.e("size",mlistuser.size.toString())

                if (mlistuser.size>0) {
                    mMap.clear()
                        // adding marker
                    for (i in mlistuser.indices) {

                        mMap.addMarker(
                            MarkerOptions()
                                .position(
                                    LatLng(
                                        mlistuser.get(i).address!!.geo!!.lat!!,
                                        mlistuser.get(i).address!!.geo!!.lng!!
                                    )).title("")
                        ).tag=i
                    }


                    val cameraPosition = CameraPosition.Builder()
                        .target(
                            LatLng(
                                mlistuser.get(0).address!!.geo!!.lat!!,
                                mlistuser.get(0).address!!.geo!!.lng!!
                            )
                        )
                        .zoom(0f) // Sets the zoom
                        .bearing(0f) // Sets the orientation of the camera to east
                        .tilt(0f) // Sets the tilt of the camera to 0 degrees
                        .build() // Creates a CameraPosition from the builder

                    mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition))

                    mMap.setOnMarkerClickListener(OnMarkerClickListener { marker ->
                      val position = marker.tag as Int

                       Toast.makeText(this, mlistuser.get(position).name, Toast.LENGTH_SHORT).show()
                        true
                    })
                }

            })
        }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        mMap.getUiSettings().setZoomControlsEnabled(true)
        // Add a marker in Sydney and move the camera
       // val sydney = LatLng(-34.0, 151.0)
       // mMap.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"))
        //mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))
    }

    override fun onError(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}
