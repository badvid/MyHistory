package com.bad.mifamilia.adapters

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.util.Base64
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.graphics.drawable.toDrawable
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bad.mifamilia.R
import com.bad.mifamilia.helpers.EtapaDiffUtil
import com.bad.mifamilia.models.Etapa
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import java.lang.Exception


class EtapaAdapter(var list : List<Etapa>, val onItemSelected:(Etapa)-> Unit, val onItemRemove:(Etapa)-> Unit) : RecyclerView.Adapter<EtapaViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EtapaViewHolder {
        return EtapaViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_etapas,parent,false))
    }
    override fun getItemCount() = list.size
    /*override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }*/

    override fun onBindViewHolder(holder: EtapaViewHolder, position: Int) {
        holder.render(list[position],onItemSelected,onItemRemove)
    }
    fun updateList(newList: List<Etapa>){
        val eventoDiff = EtapaDiffUtil(list,newList)
        val result  = DiffUtil.calculateDiff(eventoDiff)
        list = newList
        result.dispatchUpdatesTo(this)
    }


}

class EtapaViewHolder(view : View) : RecyclerView.ViewHolder(view){
    //val cvEtapas = view.findViewById<CardView>(R.id.cvEtapas)
    //val lyEtapas = view.findViewById<FrameLayout>(R.id.lyEtapas)
    val lyEtapas = view.findViewById<FrameLayout>(R.id.lyEtapas)
    val imgEtapa = view.findViewById<LinearLayout>(R.id.imgEtapa)
    val etEtapa = view.findViewById<TextView>(R.id.etEtapa)
    val etCount = view.findViewById<TextView>(R.id.etCount)
    val context = view.context

    fun render(ev: Etapa, onItemSelected: (Etapa) -> Unit, onItemRemove: (Etapa) -> Unit){
        // cvEtapas.setBackgroundResource(R.drawable.f_etapa)
        lyEtapas.setBackgroundResource(R.drawable.f_etapa)
        //imgEtapa.background.alpha = 51
        //imgEtapa.alpha = 0.8f
        //imgEtapa.setBackgroundColor(getColorWithAlpha(Color.BLACK, 0.2f))
        //imgEtapa.setBackgroundColor(Color.HSVToColor(50,))#CCFF0000

        etEtapa.text = ev.etapa
        etCount.text = "${(if(ev.iGallery != null) ev.iGallery!!.count().toString() else 0)} Registradas"

        var img : ImageView = ImageView(context)
        Picasso.get().load(ev.photo).into(img, object : Callback {
            override fun onSuccess() {
                lyEtapas.setBackgroundDrawable(img.drawable)
            }
            override fun onError(e: Exception?) {
                lyEtapas.background = R.drawable.placeholder.toDrawable()
            }

        })
        /*
        Picasso.with(this)
            .load(imageUri)
            .fit()
            .centerCrop()
            .into(img, object : Callback {
                override fun onSuccess() {
                    lyEtapas.setBackgroundDrawable(img.drawable)
                }

                override fun onError(e: Exception?) {
                    TODO("Not yet implemented")
                }

            })

        Picasso.get().load(ev.photo)
            .placeholder(R.drawable.placeholder)
            .error(R.drawable.placeholder)
            .into(object : Target {
                fun onBitmapLoaded(bitmap: Bitmap?, from: LoadedFrom?) {
                    lyEtapas.setBackground(BitmapDrawable(bitmap))
                }

                fun onBitmapFailed(errorDrawable: Drawable?) {}
                fun onPrepareLoad(placeHolderDrawable: Drawable?) {}
            })


        Picasso.with(this).load("http://imageUrl").into(object : Target() {
            fun onBitmapLoaded(bitmap: Bitmap?, from: LoadedFrom?) {
                mYourLayout.setBackground(BitmapDrawable(bitmap))
            }

            fun onBitmapFailed(errorDrawable: Drawable?) {}
            fun onPrepareLoad(placeHolderDrawable: Drawable?) {}
        })*/
        //if(ev.photo != null) lyEtapas.background = convertB64StringToDrawable( ev.photo)
        /* ev.fimg?.let { tvImg.setImageResource(it) }
            tvEvent.text = ev.evento
cardView.setBackgroundResource(R.drawable.card_view_bg);
            val _d : Date = SimpleDateFormat("yyyy-MM-dd HH:mm").parse(ev.fecha)
            tvFecha.text = SimpleDateFormat("dd ").format(_d) + ucFirst(SimpleDateFormat("MMMM").format(_d))
            tvTime.text = SimpleDateFormat("HH:mm").format(_d)

            when(ev.modulo){
                1 ->  iEst.setImageResource(R.drawable.modulo_circle_m1)
                2 ->  iEst.setImageResource(R.drawable.modulo_circle_m2)
                3 ->  iEst.setImageResource(R.drawable.modulo_circle_m3)
            }

            tvImg.setOnClickListener {
                onItemRemove(ev)
            }
            tvEvent.setOnClickListener {
                onItemSelected(ev)
            }*/
        lyEtapas.setOnClickListener {
            onItemSelected(ev)
         }
     }

    fun ucFirst(str : String) : String{
        var r : String = ""
        if(str.isEmpty()){
            r = ""
        }else{
            r = str.substring(0,1).toUpperCase() + str.substring(1,3)
        }
        return r
    }
    private fun getColorWithAlpha(color: Int, ratio: Float): Int {
        return Color.argb(Math.round(Color.alpha(color) * ratio), Color.red(color), Color.green(color), Color.blue(color))
    }

    private fun convertBase64ToBitmap(b64: String): Bitmap? {
        val imageAsBytes: ByteArray = Base64.decode(b64.toByteArray(), Base64.DEFAULT)
        return BitmapFactory.decodeByteArray(imageAsBytes, 0, imageAsBytes.size)
    }
    private fun convertToDrawable(b64: String): Drawable?
    {
        val imageAsBytes: ByteArray = Base64.decode(b64.toByteArray(), Base64.DEFAULT)
        val bitmap = BitmapFactory.decodeByteArray(imageAsBytes, 0, imageAsBytes.size)
        val d: Drawable = BitmapDrawable(bitmap)
        return  d
    }
    private fun convertB64StringToDrawable(b64: String): Drawable?
    {
        val decodedByte = Base64.decode(b64, Base64.DEFAULT)
        val bitmap = BitmapFactory.decodeByteArray(decodedByte, 0, decodedByte.size)
        val d: Drawable = BitmapDrawable(bitmap)
        return  d
    }
    //fun String.base64ToByteCode() = Base64.decode(this.substring(this.indexOf(",")  + 1), Base64.DEFAULT)
   /*fun decodeDrawable( base64: String): Drawable? {
        var ret: Drawable? = null
        if (base64 != "") {
            val bais = ByteArrayInputStream(
                Base64.decode(base64.toByteArray(), Base64.DEFAULT)
            )
            ret = Drawable.createFromResourceStream(
                context.getResources(),
                null, bais, null, null
            )
            try {
                bais.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
        return ret
    }*/
}




