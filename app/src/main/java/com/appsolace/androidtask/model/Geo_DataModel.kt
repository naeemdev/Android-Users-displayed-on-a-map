package com.appsolace.androidtask.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Geo_DataModel(

    @SerializedName("lat")
    @Expose
    var lat: String? = null,

    @SerializedName("lng")
    @Expose
    var lng: String? = null
) {


}