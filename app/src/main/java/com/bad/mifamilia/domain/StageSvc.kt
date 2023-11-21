package com.bad.mifamilia.domain

import com.bad.mifamilia.data.services.Repository
import com.bad.mifamilia.models.Etapa

class StageSvc {
    private val repo = Repository()

    suspend operator fun invoke(id: Int, id_user : Int, page: Int, perPage : Int):List<Etapa>? = repo.getAllStages(id,id_user,page,perPage)
}