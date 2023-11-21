package com.bad.mifamilia.models

import android.widget.Gallery
import com.google.gson.annotations.SerializedName

data class Etapa(@SerializedName("id") val id_etapa : Int,
                 @SerializedName("idUser")  val id_user : Int,
                 @SerializedName("name") val etapa:String,
                 @SerializedName("photo") val photo:String,
                 @SerializedName("iGallery") val iGallery: List<Galeria>?)