package com.bad.mifamilia.ui.history

import android.R
import android.content.ContentValues
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.lifecycle.*
import androidx.recyclerview.widget.GridLayoutManager
import com.bad.mifamilia.adapters.FamilyAdapter
import com.bad.mifamilia.databinding.FragmentFamilyBinding
import com.bad.mifamilia.databinding.FragmentHistoryBinding
import com.bad.mifamilia.helpers.GlobalClass
import com.bad.mifamilia.helpers.RetrofitHelper
import com.bad.mifamilia.models.Etapa
import com.bad.mifamilia.models.Family
import com.bad.mifamilia.data.services.models.StageGetResponse
import com.bad.mifamilia.domain.StageSvc
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Response

class HistoryViewModel : ViewModel() {
    // TODO: Implement the ViewModel
    private var _binding: FragmentHistoryBinding? = null
    private val binding get() = _binding!!
    private lateinit var g: GlobalClass
    private val retrofit = RetrofitHelper.getInstance()
    var stages: List<Etapa> = emptyList()
    var etapas = MutableLiveData<List<Etapa>>()
    //private  var stages = mutableListOf<Family>()
    val isLoading = MutableLiveData<Boolean>()
    var stagesvc = StageSvc()
/*
    fun onCreate(id: Int, id_user : Int, page: Int, perPage : Int) {
        viewModelScope.launch {
            val result :List<Etapa>? = stagesvc(id,id_user,page,perPage)
            if(!result.isNullOrEmpty()){
                //etapas.value = result
                /*val list = mutableListOf<Etapa>() ?: arrayListOf()
                list.addAll(result)
                etapas.value = list*/
                stages = result
            }
        }
    }*/
    fun findStages(id: Int, id_user : Int, page: Int, perPage : Int): LiveData<List<Etapa>> {
        val res = MutableLiveData<List<Etapa>>()
        viewModelScope.launch {
            isLoading.postValue(true)
            try {
                val result :List<Etapa>? = stagesvc(id,id_user,page,perPage)
                if(!result.isNullOrEmpty()){
                    stages = result
                    val list = mutableListOf<Etapa>() ?: arrayListOf()
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
    suspend fun listar(id: Int, id_user : Int, page: Int, perPage : Int){
        viewModelScope.launch {
            val result :List<Etapa>? = stagesvc(id,id_user,page,perPage)
            if(!result.isNullOrEmpty()){
                //etapas.value = result
                /*val list = mutableListOf<Etapa>() ?: arrayListOf()
                list.addAll(result)
                etapas.value = list*/
                stages = result
            }
        }
    }
    suspend fun getStages(id: Int, id_user : Int, page: Int, perPage : Int): List<Etapa> {
        val response: Response<StageGetResponse> = retrofit.getStages(id,id_user,page,perPage)
        if(response.isSuccessful)
        {
            if(response.body()!!.success) stages = response.body()!!.data

        }
            /*val response: Response<StageGetResponse> = retrofit.getStages(id,id_user,page,perPage)
            if(response.isSuccessful)
            {
                if(response.body()!!.success) stages = response.body()!!.data

            }*/

       /* viewModelScope.launch {
            val result = withContext(Dispatchers.IO){

            }

            println(result)


        }*/
        return stages
    }


}