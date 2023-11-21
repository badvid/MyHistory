package com.bad.mifamilia.helpers

import androidx.recyclerview.widget.DiffUtil
import com.bad.mifamilia.models.Etapa

class EtapaDiffUtil(
    private val oldList : List<Etapa>,
    private val newList : List<Etapa>
) : DiffUtil.Callback(){
    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].id_etapa == newList[newItemPosition].id_etapa
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition] == newList[newItemPosition]
    }
}