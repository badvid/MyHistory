package com.bad.mifamilia.domain

import com.bad.mifamilia.data.services.Repository
import com.bad.mifamilia.models.Etapa
import com.bad.mifamilia.models.HomeLast

class HomeServices {
    private val repo = Repository()

    suspend operator fun invoke(id_user : Int):List<HomeLast>? = repo.getHomeInit(id_user)
}