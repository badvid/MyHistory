package com.bad.mifamilia.adapters

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
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
        return HomeViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_multimedia,parent,false))
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


    fun render(ev: HomeLast, onItemSelected: (HomeLast) -> Unit, onItemRemove: (HomeLast) -> Unit){

        tvTitle.text = "${ucFirst(ev.stage)} - ${ucFirst(ev.name)}"

        var i : Int = 1
        var iv: ImageView = ivFoto1

        ev.iMultimedias?.forEach {
            when(i){
                1 -> iv = ivFoto1
                2 -> iv = ivFoto2
                3 -> iv = ivFoto3
            }
            setearInfo(it, iv, onItemRemove(ev))

            val _d : Date = SimpleDateFormat("yyyy-MM-dd HH:mm").parse(it.dateCreated)
            tvSubTitle.text = "Último registro * ${SimpleDateFormat("dd/MM/yyy HH:mm").format(_d)}"
            //tvFecha.text = SimpleDateFormat("dd ").format(_d) + ucFirst(SimpleDateFormat("MMMM").format(_d))
            //tvTime.text = SimpleDateFormat("HH:mm").format(_d)
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
            r = str.substring(0,1).toUpperCase() + str.substring(1,3)
        }
        return r
    }
    private fun convertBase64ToBitmap(b64: String): Bitmap {
        val imageAsBytes: ByteArray = Base64.decode(b64.toByteArray(), Base64.DEFAULT)
        return BitmapFactory.decodeByteArray(imageAsBytes, 0, imageAsBytes.size)
    }
}