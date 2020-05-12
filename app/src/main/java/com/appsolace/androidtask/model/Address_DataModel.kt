package com.appsolace.androidtask.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Address_DataModel(
    @SerializedName("street")
    @Expose
    var street: String? = null,

    @SerializedName("suite")
    @Expose
    var suite: String? = null,

    @SerializedName("city")
    @Expose
    var city: String? = null,

    @SerializedName("zipcode")
    @Expose
    var zipcode: String? = null,

    @SerializedName("geo")
    @Expose
    var geo: Geo_DataModel? = null
) {


}