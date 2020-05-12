package com.appsolace.androidtask

import android.content.Context
import android.content.res.Resources.NotFoundException
import android.graphics.Bitmap
import android.graphics.Canvas
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View

import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.appsolace.androidtask.databinding.BottomsheetUserinfoBinding
import com.appsolace.androidtask.model.UserResponseModel
import com.appsolace.androidtask.repo.ResponseListener
import com.appsolace.androidtask.utils.isNetworkAvailable
import com.appsolace.androidtask.viewmodel.UserViewModel
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import com.google.android.material.bottomsheet.BottomSheetDialog
import kotlinx.android.synthetic.main.activity_maps.*



class MapsActivity : AppCompatActivity(), OnMapReadyCallback ,ResponseListener{

    private lateinit var mMap: GoogleMap
    var viewModel: UserViewModel?=null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_maps)
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
        viewModel= ViewModelProvider(this).get(UserViewModel::class.java)
        rl_loading.visibility=View.GONE
        calltoserver()
    }

    fun calltoserver(){
        if (isNetworkAvailable(this)){
            //calling ai from view and get response
            rl_loading.visibility=View.VISIBLE
            viewModel!!.getusers(this).observe(this, Observer { mlistuser->
                // mUserResponseModellist.addAll(mlistuser as Array<out UserResponseModel>)
                Log.e("size",mlistuser.size.toString())
                rl_loading.visibility=View.GONE
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
                                .icon(bitmapDescriptorFromVector(this, R.drawable.ic_username))
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
                        showuser_DetailDialog(mlistuser.get(position))

                        true
                    })
                }

            })
        }else{
            Toast.makeText(this, resources.getString(R.string.nointernet), Toast.LENGTH_SHORT).show()
        }
    }
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        mMap.getUiSettings().setZoomControlsEnabled(true)
        try {
            // Customise the styling of the base map using a JSON object defined
            // in a raw resource file.
            val success = mMap.setMapStyle(
                MapStyleOptions.loadRawResourceStyle(
                    this, R.raw.style_json
                )
            )
            if (!success) {
               //Log.e(TAG, "Style parsing failed.");
            }
        } catch (e: NotFoundException) {
            // Log.e(TAG, "Can't find style. Error: ", e);
        }

    }

    override fun onError(message: String) {
        rl_loading.visibility=View.GONE
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    fun showuser_DetailDialog(mUserResponseModel:UserResponseModel) {

        val dialogbinding: BottomsheetUserinfoBinding=   DataBindingUtil.inflate(
            LayoutInflater.from(this),
            R.layout.bottomsheet_userinfo,
            null, false)
        val dialog = BottomSheetDialog(this)
        dialog.setContentView(dialogbinding.root)
        dialog.setCancelable(false)
        dialogbinding.mUser=mUserResponseModel
        dialogbinding.btnClose.setOnClickListener(View.OnClickListener {

            dialog.dismiss()
        })

        dialog.show()

    }
    private fun bitmapDescriptorFromVector(context: Context, vectorResId: Int): BitmapDescriptor? {
        return ContextCompat.getDrawable(context, vectorResId)?.run {
            setBounds(0, 0, intrinsicWidth, intrinsicHeight)
            val bitmap =
                Bitmap.createBitmap(intrinsicWidth, intrinsicHeight, Bitmap.Config.ARGB_8888)
            draw(Canvas(bitmap))
            BitmapDescriptorFactory.fromBitmap(bitmap)
        }
    }
}
