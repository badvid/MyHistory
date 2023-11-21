package com.bad.mifamilia.models

import com.google.gson.annotations.SerializedName

data class Parent (@SerializedName("idParent") val id : Int,
                   @SerializedName("idNivel")  val idNivel : Int,
                   @SerializedName("descrip") val descrip :String)

