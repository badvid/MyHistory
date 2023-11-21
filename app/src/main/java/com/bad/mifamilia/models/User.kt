package com.bad.mifamilia.models

import com.google.gson.annotations.SerializedName

data class User (
    var id : Int,
    @SerializedName("user_name") var username: String,
    @SerializedName("date_created") var dateCreated: String,
    @SerializedName("last_name") var lastName: String,
    @SerializedName("first_name") var firstName: String,
    var dni: String,
    var email: String,
    var photo: String,
    var password: String,
    var token: String,
    @SerializedName("date_expire") var dateExpire: String
)