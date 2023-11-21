package com.bad.mifamilia.helpers

import androidx.recyclerview.widget.DiffUtil
import com.bad.mifamilia.models.Multimedia

class MultimediaDiffUtil (
    private val oldList : List<Multimedia>,
    private val newList : List<Multimedia>
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