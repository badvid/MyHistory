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
import com.bad.mifamilia.models.Family
import com.bumptech.glide.Glide
import java.text.SimpleDateFormat
import java.util.*


class FamilyAdapter(var list : List<Family>, val onItemSelected:(Family)-> Unit, val onItemRemove:(Family)-> Unit) : RecyclerView.Adapter<FamilyViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FamilyViewHolder {
        return FamilyViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_family,parent,false))
    }
    override fun getItemCount() = list.size
    /*override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }*/

    override fun onBindViewHolder(holder: FamilyViewHolder, position: Int) {
        holder.render(list[position],onItemSelected,onItemRemove)
    }
    fun updateList(newList: List<Family>){
        val familyDiff = FamilyDiffUtil(list,newList)
        val result  = DiffUtil.calculateDiff(familyDiff)
        list = newList
        result.dispatchUpdatesTo(this)
    }


}

class FamilyViewHolder(view : View) : RecyclerView.ViewHolder(view){
    val context = view.context
    val ivFoto = view.findViewById<ImageView>(R.id.img)
    val etParent = view.findViewById<TextView>(R.id.etParent)
    val etApenom = view.findViewById<TextView>(R.id.etApenom)
   /* val iEst = view.findViewById<ImageView>(R.id.imgestado)
    val tvImg = view.findViewById<ImageView>(R.id.img)
    val tvEvent = view.findViewById<TextView>(R.id.tvEvent)
    val tvFecha = view.findViewById<TextView>(R.id.tvFecha)
    val tvTime = view.findViewById<TextView>(R.id.tvTime)*/

    fun render(ev: Family, onItemSelected: (Family) -> Unit, onItemRemove: (Family) -> Unit){
        //ivFoto.setImageResource(R.drawable.foto1)
        //if(ev.photo != null) ivFoto.setImageBitmap(convertBase64ToBitmap(ev.photo))
        etParent.text = ev.oParent.descrip
        etApenom.text = "${ev.firstName} ${ev.lastName}"

        Glide
            .with(context)
            .load(ev.photo)
            .centerCrop()
            .placeholder(R.drawable.placeholder_avatar)
            .error(R.drawable.placeholder_avatar)
            .into(ivFoto)
    /* ev.fimg?.let { tvImg.setImageResource(it) }
        tvEvent.text = ev.evento

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
    private fun convertBase64ToBitmap(b64: String): Bitmap? {
        val imageAsBytes: ByteArray = Base64.decode(b64.toByteArray(), Base64.DEFAULT)
        return BitmapFactory.decodeByteArray(imageAsBytes, 0, imageAsBytes.size)
    }
}