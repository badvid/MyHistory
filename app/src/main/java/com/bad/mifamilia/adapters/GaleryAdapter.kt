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
import com.bad.mifamilia.helpers.FamilyDiffUtil
import com.bad.mifamilia.helpers.GaleriaDiffUtil
import com.bad.mifamilia.models.Galeria
import com.bumptech.glide.Glide
import com.squareup.picasso.Picasso

class GaleryAdapter(var list : List<Galeria>, val onItemSelected:(Galeria)-> Unit, val onItemRemove:(Galeria)-> Unit) : RecyclerView.Adapter<GaleriaViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GaleriaViewHolder {
        return GaleriaViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_gallery,parent,false))
    }
    override fun getItemCount() = list.size
    /*override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }*/

    override fun onBindViewHolder(holder: GaleriaViewHolder, position: Int) {
        holder.render(list[position],onItemSelected,onItemRemove)
    }
    fun updateList(newList: List<Galeria>){
        val galeryDiff = GaleriaDiffUtil(list,newList)
        val result  = DiffUtil.calculateDiff(galeryDiff)
        list = newList
        result.dispatchUpdatesTo(this)
    }


}

class GaleriaViewHolder(view : View) : RecyclerView.ViewHolder(view){
    val ivFoto = view.findViewById<ImageView>(R.id.img_item)
    val tvTitle = view.findViewById<TextView>(R.id.tv_title)
    val context = view.context

    fun render(ev: Galeria, onItemSelected: (Galeria) -> Unit, onItemRemove: (Galeria) -> Unit){
        //ivFoto.setImageResource(R.drawable.foto1)
        //if(ev.photo != null) ivFoto.setImageBitmap(convertBase64ToBitmap(ev.photo))
        tvTitle.text = ev.name
        /*Picasso.get().load(ev.photo).fit()
            .placeholder(R.drawable.placeholder)
            .error(R.drawable.placeholder)
            .into(ivFoto)*/
        Glide
            .with(context)
            .load(ev.photo)
            .centerCrop()
            .placeholder(R.drawable.placeholder)
            .error(R.drawable.placeholder)
            .into(ivFoto);

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