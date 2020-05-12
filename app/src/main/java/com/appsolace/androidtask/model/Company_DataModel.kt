package com.appsolace.androidtask.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Company_DataModel(
    @SerializedName("name")
    @Expose
    var name: String? = null,

    @SerializedName("catchPhrase")
    @Expose
    var catchPhrase: String? = null,

    @SerializedName("bs")
    @Expose
    var bs: String? = null


) {


}