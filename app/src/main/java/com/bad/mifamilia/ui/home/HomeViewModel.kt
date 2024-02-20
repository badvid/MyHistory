package com.bad.mifamilia.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bad.mifamilia.databinding.FragmentHistoryBinding
import com.bad.mifamilia.domain.HomeServices
import com.bad.mifamilia.domain.StageSvc
import com.bad.mifamilia.helpers.GlobalClass
import com.bad.mifamilia.helpers.RetrofitHelper
import com.bad.mifamilia.models.Etapa
import com.bad.mifamilia.models.HomeLast
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {

    private var _binding: FragmentHistoryBinding? = null
    private val binding get() = _binding!!
    private lateinit var g: GlobalClass
    private val retrofit = RetrofitHelper.getInstance()
    val isLoading = MutableLiveData<Boolean>()
    var homesvc = HomeServices()
    var lista: List<HomeLast> = emptyList()

    private val _text = MutableLiveData<String>().apply {
        value = "" //""This is home Fragment"
    }
    val text: LiveData<String> = _text

    fun homeInit(id_user : Int): LiveData<List<HomeLast>> {
        val res = MutableLiveData<List<HomeLast>>()
        viewModelScope.launch {
            isLoading.postValue(true)
            try {
                val result :List<HomeLast>? = homesvc.invoke(id_user)
                if(!result.isNullOrEmpty()){
                    lista = result
                    val list = mutableListOf<HomeLast>() ?: arrayListOf()
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