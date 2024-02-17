package com.bad.mifamilia.adapters

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bad.mifamilia.R
import com.bad.mifamilia.helpers.*
import com.bad.mifamilia.models.Multimedia
import com.bumptech.glide.Glide

class MultimediaAdapter (var list : List<Multimedia>, val onItemSelected:(Multimedia)-> Unit, val onItemRemove:(Multimedia)-> Unit) : RecyclerView.Adapter<MultimediaViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MultimediaViewHolder {
        return MultimediaViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_multimedia,parent,false))
    }
    override fun getItemCount() = list.size
    /*override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }*/

    override fun onBindViewHolder(holder: MultimediaViewHolder, position: Int) {
        holder.render(list[position],onItemSelected,onItemRemove)
    }
    fun updateList(newList: List<Multimedia>){
        val objDiff = MultimediaDiffUtil(list,newList)
        val result  = DiffUtil.calculateDiff(objDiff)
        list = newList
        result.dispatchUpdatesTo(this)
    }


}

class MultimediaViewHolder(view : View) : RecyclerView.ViewHolder(view){
    val context = view.context
    val ivFoto = view.findViewById<ImageView>(R.id.img_home_item)
    //val tvTitle = view.findViewById<TextView>(R.id.tv_title)

    fun render(ev: Multimedia, onItemSelected: (Multimedia) -> Unit, onItemRemove: (Multimedia) -> Unit){
        //ivFoto.setImageResource(R.drawable.foto1)
        //if(ev.photo != null) ivFoto.setImageBitmap(convertBase64ToBitmap(ev.photo))
        //tvTitle.text = ev.name
        /*Picasso.get().load(ev.link)
            .placeholder(R.drawable.placeholder)
            .error(R.drawable.placeholder)
            .into(ivFoto)*/

        Glide
            .with(context)
            .load(ev.link)
            .centerCrop()
            .skipMemoryCache(false)
            .placeholder(R.drawable.placeholder)
            .error(R.drawable.placeholder)
            .into(ivFoto)

        ivFoto.setOnClickListener {
            onItemSelected(ev)
        }
        ivFoto.setOnLongClickListener {
            onItemRemove(ev)
            true
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