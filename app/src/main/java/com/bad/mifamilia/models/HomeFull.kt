package com.bad.mifamilia.models

data class HomeFull(
    val id: Int,
    val idUser: Int,
    val idStage: Int,
    val idGalery: Int,
    val stage: String,
    val galery: String,
    var link: String,
    val location: String,
    val descrip: String
)