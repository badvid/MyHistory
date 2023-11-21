package com.bad.mifamilia.helpers

import androidx.recyclerview.widget.DiffUtil
import com.bad.mifamilia.models.Etapa
import com.bad.mifamilia.models.Galeria

class GaleriaDiffUtil (
    private val oldList : List<Galeria>,
    private val newList : List<Galeria>
) : DiffUtil.Callback(){
    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].id == newList[newItemPosition].id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition] == newList[newItemPosition]
    }
}