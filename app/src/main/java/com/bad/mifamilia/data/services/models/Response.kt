package com.bad.mifamilia.data.services.models

import com.bad.mifamilia.models.*
import com.google.gson.annotations.SerializedName

data class UserResponse (
    var success : Boolean,
    var code : Int,
    var message : String,
    var source : String,
    var data : User
)

data class UserPostResponse (
    var success : Boolean,
    var code : Int,
    var message : String,
    var source : String,
    var data : UserPost
)
data class ParentGetResponse (var success : Boolean, var code : Int, var message : String, var source : String, var data : List<Parent>)

data class FilePostResponse (var success : Boolean, var code : Int, var message : String, var source : String, var data : List<Archivo>)

data class StageGetResponse (@SerializedName("success") var success : Boolean,
                            @SerializedName("code") var code : Int,
                            @SerializedName("message") var message : String,
                            @SerializedName("source") var source : String,
                            @SerializedName("page") var page: Int,
                            @SerializedName("perPage") var perPage : Int,
                            @SerializedName("total_pages") var total_pages: Int,
                            @SerializedName("total_reg") var total_reg : Int,
                            @SerializedName("data") var data: List<Etapa>)

data class StagePostResponse (var success : Boolean, var code : Int, var message : String, var source : String, var data : Etapa)

data class GalleryGetResponse (var success : Boolean, var code : Int, var message : String, var source : String, var data : List<Galeria>)
data class GalleryPostResponse (var success : Boolean, var code : Int, var message : String, var source : String, var data : Galeria)

data class MultimediaGetResponse (var success : Boolean, var code : Int, var message : String, var source : String, var data : List<Multimedia>)
data class MultimediaPostResponse (var success : Boolean, var code : Int, var message : String, var source : String, var data : Multimedia)

data class FamilyGetResponse (var success : Boolean, var code : Int, var message : String, var source : String, var data : List<Family>)
data class FamilyPostResponse (var success : Boolean, var code : Int, var message : String, var source : String, var data : Family)

