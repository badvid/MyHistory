package com.bad.mifamilia.ui.home

import android.app.Dialog
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.io.File

class MainViewModel : ViewModel() {
     var file : MutableLiveData<File> = MutableLiveData()
    var popup : MutableLiveData<Dialog> = MutableLiveData()
    fun setearFile(_file:File){
        file.value = _file
    }
    fun setearPopUp(_popup:Dialog){
        popup.value = _popup
    }
}