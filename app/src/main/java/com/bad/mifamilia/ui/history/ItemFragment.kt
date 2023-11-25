package com.bad.mifamilia.ui.history

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.bad.mifamilia.R
import com.bad.mifamilia.databinding.FragmentItemBinding
import com.bad.mifamilia.helpers.GlobalClass
import com.bumptech.glide.Glide

class ItemFragment : Fragment() {
    private var _binding: FragmentItemBinding? = null
    private val binding get() = _binding!!
    private lateinit var g: GlobalClass
    companion object {
        fun newInstance() = ItemFragment()
    }

    private lateinit var viewModel: ItemViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        setHasOptionsMenu(true)
        super.onCreate(savedInstanceState)
        // setHasOptionsMenu(true)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        //return inflater.inflate(R.layout.fragment_item, container, false)
        _binding = FragmentItemBinding.inflate(inflater)

        return binding.root

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(ItemViewModel::class.java)
        // TODO: Use the ViewModel

        g = activity!!.applicationContext as GlobalClass


        Glide.with(activity!!)
            .load(g.oMultimedia!!.link)
            .centerInside()
            .skipMemoryCache(false)
            .placeholder(R.drawable.placeholder)
            .error(R.drawable.placeholder)
            .into(binding.imgFoto)
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == android.R.id.home){
            findNavController().navigate(com.bad.mifamilia.R.id.action_itemFragment_to_multimediaFragment)
        }
        return true
    }
    override fun onPrepareOptionsMenu(menu: Menu) {
        menu.findItem(com.bad.mifamilia.R.id.mnu_add_stages).setVisible(false)
        menu.findItem(com.bad.mifamilia.R.id.mnu_add_family).setVisible(false)
        menu.findItem(com.bad.mifamilia.R.id.mnu_add_photo).setVisible(false)

        menu.findItem(com.bad.mifamilia.R.id.mnu_add_galery).setVisible(false)
        return super.onPrepareOptionsMenu(menu)
    }


}