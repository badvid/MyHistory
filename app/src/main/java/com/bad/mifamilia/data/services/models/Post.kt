package com.bad.mifamilia.data.services.models

data class UserPost(val id : Int, val lastName : String, val firstName : String, val nroDoc : String, val email: String,
                    val photo: String, val userName: String, val password: String)

data class StagePost(val id : Int, val idUser : Int, val name: String, val photo: String)

data class GalleryPost(
    val id: Int,
    val idStage: Int,
    val name: String,
    val location: String,
    val latitud: String,
    val longitud: String,
    val descrip: String,
    val photo: String
)
data class MultimediaPost (
    val id: Int,
    val idGallery: Int,
    val idType: Int,
    val name: String,
    val link: String
)
data class FamilyPost(
    val idPerson: Int,
    val idUser: Int,
    val idParent: Int,
    val firstName: String,
    val lastName: String,
    val nroDoc: String,
    val email: String,
    val descrip: String,
    var photo: String
)