package com.bad.mifamilia.helpers

import androidx.recyclerview.widget.DiffUtil
import com.bad.mifamilia.models.Family


class FamilyDiffUtil(
    private val oldList : List<Family>,
    private val newList : List<Family>
) : DiffUtil.Callback(){
    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].idPerson == newList[newItemPosition].idPerson
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition] == newList[newItemPosition]
        /*return when {
            oldList[oldItemPosition].evento != newList[newItemPosition].evento -> false
            (oldList[oldItemPosition].id_evento && oldList[oldItemPosition].evento) != newList[newItemPosition].evento -> false
        }*/
    }
}