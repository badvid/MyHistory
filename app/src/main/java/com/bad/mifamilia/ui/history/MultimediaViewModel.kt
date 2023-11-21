package com.bad.mifamilia.ui.history

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bad.mifamilia.databinding.FragmentHistoryBinding
import com.bad.mifamilia.domain.MultimediaServices
import com.bad.mifamilia.domain.StageSvc
import com.bad.mifamilia.helpers.GlobalClass
import com.bad.mifamilia.helpers.RetrofitHelper
import com.bad.mifamilia.models.Etapa
import com.bad.mifamilia.models.Multimedia
import kotlinx.coroutines.launch

class MultimediaViewModel : ViewModel() {
    // TODO: Implement the ViewModel
    private var _binding: FragmentHistoryBinding? = null
    private val binding get() = _binding!!
    private lateinit var g: GlobalClass
    private val retrofit = RetrofitHelper.getInstance()
    var lista: List<Multimedia> = emptyList()
    val isLoading = MutableLiveData<Boolean>()
    var svc = MultimediaServices()

    fun findSMultimedias(id: Int, id_gallery : Int, page: Int, perPage : Int): LiveData<List<Multimedia>> {
        val res = MutableLiveData<List<Multimedia>>()
        viewModelScope.launch {
            isLoading.postValue(true)
            try {
                val result :List<Multimedia>? = svc(id,id_gallery,page,perPage)
                if(!result.isNullOrEmpty()){
                    lista = result
                    val list = mutableListOf<Multimedia>() ?: arrayListOf()
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