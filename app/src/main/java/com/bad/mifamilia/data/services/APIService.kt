package com.bad.mifamilia.data.services

import com.bad.mifamilia.data.services.models.*
import com.bad.mifamilia.models.Parent
import com.bad.mifamilia.data.services.models.*
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*

interface APIService {
    @POST("Authenticate")
    suspend fun Authenticate(@Field("user") user:String, @Field("pass") pass:String) : Call<UserResponse>
    @POST("Authenticate")
    fun log(@Body body: UserLogin) : Response<UserResponse>
    @POST("Authenticate")
    fun login(@Body body: UserLogin) : Call<UserResponse>
    //@POST("path/to/endpoint")
    //fun postRequest(@Body body: Map<String, Any>): Call<ResponseBody>
    @GET
    fun getUser(@Url url : String) : Response<UserResponse>

    @POST("User")
    fun RegisterUser(@Body body: UserPost) : Call<UserPostResponse>

    //Parent
    @GET("Parent")
    suspend fun getParents() : Response<ParentGetResponse>

    //Stages
    @GET("Stages")
    suspend fun getStages(@Query("id") id: Int =0,@Query("id_user") id_user : Int = 0,@Query("page") page: Int=0,@Query("perPage") perPage : Int=10 ) : Response<StageGetResponse>
    @POST("Stages")
    fun saveStage(@Body body: StagePost) : Call<StagePostResponse>

    //Galery
    @GET("Gallery")
    suspend fun getGalery(@Query("id") id: Int =0,@Query("id_user") id_user : Int = 0,@Query("page") page: Int=0,@Query("perPage") perPage : Int=10 ) : Response<GalleryGetResponse>
    @POST("Gallery")
    fun saveGallery(@Body body: GalleryPost) : Call<GalleryPostResponse>

    //Multimedia
    @GET("Multimedia")
    suspend fun getMultimedias(@Query("id") id: Int =0,@Query("id_gallery") id_gallery : Int = 0,@Query("page") page: Int=0,@Query("perPage") perPage : Int=10 ) : Response<MultimediaGetResponse>
    @POST("Multimedia")
    fun saveMultimedia(@Body body: MultimediaPost) : Call<MultimediaPostResponse>

    //Family
    @GET("Family")
    suspend fun getFamily(@Query("id") id: Int =0,@Query("id_user") id_user : Int = 0,@Query("page") page: Int=0,@Query("perPage") perPage : Int=10 ) : Response<FamilyGetResponse>
    @POST("Family")
    fun saveFamily(@Body body: FamilyPost) : Call<FamilyPostResponse>
    //Files
    @Multipart
    @POST("api/Files")
    fun uploadImage (
        @Part image : MultipartBody.Part,
        @Query("userId") UserId : Int,
        @Query("sectorId") SectorId : Int
    ): Call<FilePostResponse>
    /*@Multipart
    @POST("Files")
    suspend fun uploadImage (
        @Part image : MultipartBody.Part,
        @Part("userId") UserId : RequestBody,
        @Part("sectorId") SectorId : RequestBody
    ): Call<FilePostResponse>*/
}