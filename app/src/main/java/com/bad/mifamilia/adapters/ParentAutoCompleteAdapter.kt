package com.bad.mifamilia.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Filter
import android.widget.ImageView
import android.widget.TextView
import com.bad.mifamilia.R
import com.bad.mifamilia.models.Parent

class ParentAutoCompleteAdapter (context: Context, parentList: List<Parent>) :
    ArrayAdapter<Parent>(context, 0, parentList) {

    private var parentListFull: List<Parent> = ArrayList(parentList)
    override fun getFilter(): Filter {
        return itemFilter
    }
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var convertView = convertView
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(
                R.layout.autocomplete_row_parent, parent, false
            )
        }
        val textViewName: TextView? = convertView?.findViewById(R.id.text_view_name)
        //val imageViewFlag: ImageView? = convertView?.findViewById(R.id.image_view_flag)
        val _item: Parent? = getItem(position)
         if (_item != null) {
            textViewName?.text = _item.descrip
            //imageViewFlag?.setImageResource(_item.fImg)
        }
        return convertView!!
    }


    private val itemFilter: Filter = object : Filter() {
        override fun performFiltering(constraint: CharSequence?): FilterResults {
            val results = FilterResults()
            val suggestions: MutableList<Parent> = ArrayList()
            if (constraint == null || constraint.length == 0) {
                suggestions.addAll(parentListFull)
            } else {
                val filterPattern = constraint.toString().toLowerCase().trim()
                for (item in parentListFull) {
                    if (item.descrip.toLowerCase().contains(filterPattern)) {
                        suggestions.add(item)
                    }
                }
            }
            results.values = suggestions
            results.count = suggestions.size
            return results
        }

        override fun publishResults(constraint: CharSequence?, results: FilterResults) {
            clear()
            addAll(results.values as List<Parent>)
            notifyDataSetChanged()
        }

        override fun convertResultToString(resultValue: Any?): CharSequence {
            return (resultValue as Parent).descrip
        }
    }
}