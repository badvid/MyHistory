package com.bad.mifamilia.data.services

import com.bad.mifamilia.helpers.RetrofitHelper
import com.bad.mifamilia.data.services.models.*
import com.bad.mifamilia.models.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response

class repoServices {

    private val retrofit = RetrofitHelper.getInstance()
    suspend fun  getHomeInit(id_user : Int): List<HomeLast> {
        return withContext(Dispatchers.IO){
            val response : Response<HomeGetResponse> = retrofit.getHomeInit(id_user)
            response.body()!!.data ?: emptyList()
        }
    }
    suspend fun  getStages(id: Int, id_user : Int, page: Int, perPage : Int): List<Etapa> {
        return withContext(Dispatchers.IO){
            val response : Response<StageGetResponse> = retrofit.getStages(id,id_user,page,perPage)
            response.body()!!.data ?: emptyList()
           /* if(response.isSuccessful)
            {
                if(response.body()!!.success) g.iStages = response.body()!!.data

            }*/
        }
    }

    suspend fun  getMultimedias(id: Int, id_gallery : Int, page: Int, perPage : Int): List<Multimedia> {
        return withContext(Dispatchers.IO){
            val response : Response<MultimediaGetResponse> = retrofit.getMultimedias(id,id_gallery,page,perPage)
            response.body()!!.data ?: emptyList()
        }
    }

    suspend fun  getFamily(id: Int, id_user : Int, page: Int, perPage : Int): List<Family> {
        return withContext(Dispatchers.IO){
            val response : Response<FamilyGetResponse> = retrofit.getFamily(id,id_user,page,perPage)
            response.body()!!.data ?: emptyList()
        }
    }
}