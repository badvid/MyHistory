package com.bad.mifamilia.domain

import com.bad.mifamilia.data.services.Repository
import com.bad.mifamilia.models.Multimedia

class MultimediaServices {
    private val repo = Repository()

    /*suspend operator fun invoke(id: Int, id_user : Int, page: Int, perPage : Int):List<Etapa>?{
        return repo.getAllStages(id,id_user,page,perPage)
    }*/
    suspend operator fun invoke(id: Int, id_gallery : Int, page: Int, perPage : Int):List<Multimedia>? = repo.getAllMultimedias(id,id_gallery,page,perPage)
}