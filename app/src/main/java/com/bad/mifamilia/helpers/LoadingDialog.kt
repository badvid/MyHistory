package com.bad.mifamilia.helpers

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import com.bad.mifamilia.R

class LoadingDialog (){
    private var activity: Activity? = null
    private var context: Context? = null
    private var dialog: AlertDialog? = null
    private var status = false
    private var cancelable = false
    private var isContext = false

    fun isStatus(): Boolean {
        return status
    }


    constructor (myActivity: Activity?) : this() {
        activity = myActivity
        cancelable = false
        isContext = false
    }

    constructor(myActivity: Activity?, cancelable: Boolean) : this() {
        var cancelable = cancelable
        activity = myActivity
        cancelable = cancelable
        isContext = false
    }

    constructor(myContext: Context?) : this() {
        context = myContext
        cancelable = false
        isContext = true
    }

    constructor(myContext: Context?, cancelable: Boolean) : this() {
        var cancelable = cancelable
        context = myContext
        cancelable = cancelable
        isContext = false
    }

    fun startLoadingDialog() {
        val builder: AlertDialog.Builder
        val inflater: LayoutInflater
        if (isContext == false) {
            builder = AlertDialog.Builder(activity)
            inflater = activity!!.layoutInflater
        } else {
            builder = AlertDialog.Builder(context)
            inflater = LayoutInflater.from(context)
        }
        builder.setView(inflater.inflate(R.layout.custom_loading, null))
        builder.setCancelable(cancelable)
        dialog = builder.create()
        dialog?.show()
        status = true
    }

    fun dismissDialog() {
        dialog!!.dismiss()
        status = false
    }
}