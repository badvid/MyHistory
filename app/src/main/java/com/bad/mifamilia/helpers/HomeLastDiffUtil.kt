package com.bad.mifamilia.helpers

import androidx.recyclerview.widget.DiffUtil
import com.bad.mifamilia.models.HomeLast

class HomeLastDiffUtil (
    private val oldList : List<HomeLast>,
    private val newList : List<HomeLast>
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