package com.bad.mifamilia.helpers

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.widget.FrameLayout
import android.widget.LinearLayout
import com.squareup.picasso.Picasso


class CustomLayout : FrameLayout {
    constructor(context: Context?) : super(context!!) {}
    constructor(context: Context?, attrs: AttributeSet?) : super(context!!, attrs) {}
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context!!,
        attrs,
        defStyleAttr
    ) {
    }

    fun onBitmapLoaded(bitmap: Bitmap?, from: Picasso.LoadedFrom?) {
        setBackground(BitmapDrawable(getResources(), bitmap))
    }

    fun onBitmapFailed(errorDrawable: Drawable?) {
        //Set your error drawable
    }

    fun onPrepareLoad(placeHolderDrawable: Drawable?) {
        //Set your placeholder
    }
}