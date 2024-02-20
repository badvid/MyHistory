package com.bad.mifamilia.adapters

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bad.mifamilia.R
import com.bad.mifamilia.helpers.HomeLastDiffUtil
import com.bad.mifamilia.models.HomeLast
import com.bad.mifamilia.models.Multimedia
import com.bumptech.glide.Glide
import java.text.SimpleDateFormat
import java.util.*

class HomeAdapter (var list : List<HomeLast>, val onItemSelected:(HomeLast)-> Unit, val onItemRemove:(HomeLast)-> Unit) : RecyclerView.Adapter<HomeViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewHolder {
        return HomeViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_home,parent,false))
    }
    override fun getItemCount() = list.size
    /*override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }*/

    override fun onBindViewHolder(holder: HomeViewHolder, position: Int) {
        holder.render(list[position],onItemSelected,onItemRemove)
    }
    fun updateList(newList: List<HomeLast>){
        val objDiff = HomeLastDiffUtil(list,newList)
        val result  = DiffUtil.calculateDiff(objDiff)
        list = newList
        result.dispatchUpdatesTo(this)
    }


}

class HomeViewHolder(view : View) : RecyclerView.ViewHolder(view){
    val context = view.context

    val tvTitle = view.findViewById<TextView>(R.id.tv_home_title)
    val tvSubTitle = view.findViewById<TextView>(R.id.tv_home_subtitle)
    val ivFoto1 = view.findViewById<ImageView>(R.id.img_home_item1)
    val ivFoto2 = view.findViewById<ImageView>(R.id.img_home_item2)
    val ivFoto3 = view.findViewById<ImageView>(R.id.img_home_item3)
    val cv_home1 = view.findViewById<CardView>(R.id.cv_home1)
    val cv_home2 = view.findViewById<CardView>(R.id.cv_home2)
    val cv_home3 = view.findViewById<CardView>(R.id.cv_home3)


    fun render(ev: HomeLast, onItemSelected: (HomeLast) -> Unit, onItemRemove: (HomeLast) -> Unit){

        tvTitle.text = "${ucFirst(ev.stage)} - ${ucFirst(ev.name)}"

        var i : Int = 1
        var cv: CardView = cv_home1
        var iv: ImageView = ivFoto1
       // iv.isVisible=false
        //cv.visibility =View.GONE

        ev.iMultimedias?.forEach {
            when(i){
                1 -> {iv = ivFoto1
                    cv = cv_home1}
                2 -> {iv = ivFoto2
                    cv = cv_home2}
                3 -> {iv = ivFoto3
                    cv = cv_home3}
            }
            setearInfo(it, iv, onItemRemove(ev))
            cv.visibility =View.VISIBLE

            try {
                val _d : Date = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").parse(it.dateCreated)
                tvSubTitle.text = "Último registro * ${SimpleDateFormat("dd/MM/yyyy HH:mm").format(_d)}"
                //tvFecha.text = SimpleDateFormat("dd ").format(_d) + ucFirst(SimpleDateFormat("MMMM").format(_d))
                //tvTime.text = SimpleDateFormat("HH:mm").format(_d)
            }catch (e: Exception){
                tvSubTitle.text = "Último registro"
            }

            i += 1
        }
        /*
        Glide
            .with(context)
            .load(ev.link)
            .centerCrop()
            .skipMemoryCache(false)
            .placeholder(R.drawable.placeholder)
            .error(R.drawable.placeholder)
            .into(ivFoto)

        ivFoto1.setOnClickListener {
            onItemSelected(ev)
        }
        ivFoto.setOnLongClickListener {
            onItemRemove(ev)
            true
        }*/

    }

    fun setearInfo(ev: Multimedia, img: ImageView, onItemSelected: Unit)
    {
        //img.isVisible=true
        //img.visibility =View.VISIBLE
        Glide
            .with(context)
            .load(ev.link)
            .centerCrop()
            .skipMemoryCache(false)
            .placeholder(R.drawable.placeholder)
            .error(R.drawable.placeholder)
            .into(img)

        img.setOnClickListener {
            onItemSelected
        }
    }

    fun ucFirst(str : String) : String{
        var r : String = ""
        if(str.isEmpty()){
            r = ""
        }else{
            r = str.substring(0,1).toUpperCase() + str.substring(1,str.length)
        }
        return r
    }
    private fun convertBase64ToBitmap(b64: String): Bitmap {
        val imageAsBytes: ByteArray = Base64.decode(b64.toByteArray(), Base64.DEFAULT)
        return BitmapFactory.decodeByteArray(imageAsBytes, 0, imageAsBytes.size)
    }
}