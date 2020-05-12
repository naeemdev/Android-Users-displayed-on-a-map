package com.appsolace.androidtask.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class UserResponseModel(
    @SerializedName("id")
    @Expose
    var id: Int? = null,

    @SerializedName("name")
    @Expose
    var name: String? = null,

    @SerializedName("username")
    @Expose
    var username: String? = null,

    @SerializedName("email")
    @Expose
    var email: String? = null,

    @SerializedName("address")
    @Expose
    var address: Address_DataModel? = null,

    @SerializedName("phone")
    @Expose
    var phone: String? = null,

    @SerializedName("website")
    @Expose
    var website: String? = null,

    @SerializedName("company")
    @Expose
    var company: Company_DataModel? = null

) {


}