package com.bad.mifamilia.domain

import com.bad.mifamilia.data.services.Repository
import com.bad.mifamilia.models.*

class FamilyServices {
    private val repo = Repository()

    suspend operator fun invoke(id: Int, id_user : Int, page: Int, perPage : Int):List<Family>? = repo.getFamily(id,id_user,page,perPage)
}