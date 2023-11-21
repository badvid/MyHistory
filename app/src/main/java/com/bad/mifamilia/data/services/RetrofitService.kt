package com.bad.mifamilia.data.services

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitService {
    companion object {
        @JvmStatic
        fun getRetrofit():Retrofit{
            return Retrofit.Builder()
                .baseUrl("http://davidbad-001-site4.atempurl.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
    }
}