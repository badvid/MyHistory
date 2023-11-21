package com.bad.mifamilia.data.services

import com.bad.mifamilia.data.*
import com.bad.mifamilia.models.*

class Repository {
    private val api = repoServices()

    suspend fun getAllStages(id: Int, id_user : Int, page: Int, perPage : Int): List<Etapa>{
        val response: List<Etapa> = api.getStages(id,id_user,page,perPage)
        StagesPovider.iStages = response
        return response
    }

    suspend fun getAllMultimedias(id: Int, id_gallery : Int, page: Int, perPage : Int): List<Multimedia>{
        val response: List<Multimedia> = api.getMultimedias(id,id_gallery,page,perPage)
        MultimediaPovider.iMultimedias = response
        return response
    }

    suspend fun getFamily(id: Int, id_user : Int, page: Int, perPage : Int): List<Family>{
        val response: List<Family> = api.getFamily(id,id_user,page,perPage)
        FamilyPovider.iFamilies = response
        return response
    }
}