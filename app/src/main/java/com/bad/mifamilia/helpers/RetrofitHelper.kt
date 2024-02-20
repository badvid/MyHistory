package com.bad.mifamilia.helpers

import com.bad.mifamilia.data.services.APIService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object  RetrofitHelper {
    private const val URL = "http://davidbad-001-site4.atempurl.com/" //"http://davidbad-001-site4.atempurl.com/"   //"http://192.168.0.205/api/" //"http://192.180.0.106:5168/" "http://davidbad-001-site4.atempurl.com/"
    private val retrofit =
         Retrofit.Builder()
            .baseUrl(URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    fun getInstance(): APIService {
        return retrofit.create(APIService::class.java)
    }
}