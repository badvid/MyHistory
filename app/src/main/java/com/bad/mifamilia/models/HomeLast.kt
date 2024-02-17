package com.bad.mifamilia.models

import com.google.gson.annotations.SerializedName

data class HomeLast ( val id: Int,
                      val idStage: Int,
                      val name: String,
                      val stage: String,
                      val dateCreated: String,
                      val dateModified: String,
                      val location: String,
                      val latitud: String,
                      val longitud: String,
                      val descrip: String,
                      val photo: String,
                      val iMultimedias: List<Multimedia>?
)