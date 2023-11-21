package com.bad.mifamilia.models


data class Multimedia (
    val id: Int,
    val idGallery: Int,
    val dateCreated: String,
    val name: String,
    val link: String,
    val oType: Type
    )

data class Type (val id: Int, val name: String)
