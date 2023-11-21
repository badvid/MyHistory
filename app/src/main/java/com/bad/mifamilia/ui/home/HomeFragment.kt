package com.bad.mifamilia.ui.home

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.bad.mifamilia.R
import com.bad.mifamilia.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

   /* companion object {
        fun newInstance() = HomeFragment()
    }

    private lateinit var viewModel: HomeViewModel*/
    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        setHasOptionsMenu(true)
        super.onCreate(savedInstanceState)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //return inflater.inflate(R.layout.fragment_home, container, false)
        val homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textHome
        homeViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }
        return root
    }

  /*  override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
        // TODO: Use the ViewModel
    }*/
    override fun onPrepareOptionsMenu(menu: Menu) {
      menu.findItem(com.bad.mifamilia.R.id.mnu_add_stages).setVisible(false)
      menu.findItem(com.bad.mifamilia.R.id.mnu_add_galery).setVisible(false)
      menu.findItem(com.bad.mifamilia.R.id.mnu_add_photo).setVisible(false)
      menu.findItem(com.bad.mifamilia.R.id.mnu_add_family).setVisible(false)
      return super.onPrepareOptionsMenu(menu)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}