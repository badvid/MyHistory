package com.bad.mifamilia.ui.family

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bad.mifamilia.databinding.FragmentFamilyBinding
import com.bad.mifamilia.domain.FamilyServices
import com.bad.mifamilia.helpers.GlobalClass
import com.bad.mifamilia.helpers.RetrofitHelper
import com.bad.mifamilia.models.Family
import kotlinx.coroutines.launch

class FamilyViewModel : ViewModel() {
    private var _binding: FragmentFamilyBinding? = null
    private val binding get() = _binding!!
    private lateinit var g: GlobalClass
    private val retrofit = RetrofitHelper.getInstance()
    var lista: List<Family> = emptyList()
    val isLoading = MutableLiveData<Boolean>()
    var svc = FamilyServices()

    fun findFamilies(id: Int, id_user : Int, page: Int, perPage : Int): LiveData<List<Family>> {
        val res = MutableLiveData<List<Family>>()
        viewModelScope.launch {
            isLoading.postValue(true)
            try {
                val result :List<Family>? = svc(id,id_user,page,perPage)
                if(!result.isNullOrEmpty()){
                    lista = result
                    val list = mutableListOf<Family>() ?: arrayListOf()
                    list.addAll(result)
                    res.value = list
                }
                isLoading.postValue(false)
            }catch (x:Exception){
                isLoading.postValue(false)
            }

        }
        return res
    }
}