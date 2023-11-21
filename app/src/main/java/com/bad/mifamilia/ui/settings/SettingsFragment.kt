package com.bad.mifamilia.ui.settings

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import com.bad.mifamilia.R

class SettingsFragment : Fragment() {

    companion object {
        fun newInstance() = SettingsFragment()
    }

    private lateinit var viewModel: SettingsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        setHasOptionsMenu(true)
        super.onCreate(savedInstanceState)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_settings, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(SettingsViewModel::class.java)
        // TODO: Use the ViewModel
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        menu.findItem(com.bad.mifamilia.R.id.mnu_add_stages).setVisible(false)
        menu.findItem(com.bad.mifamilia.R.id.mnu_add_galery).setVisible(false)
        menu.findItem(com.bad.mifamilia.R.id.mnu_add_photo).setVisible(false)
        menu.findItem(com.bad.mifamilia.R.id.mnu_add_family).setVisible(false)
        return super.onPrepareOptionsMenu(menu)
    }

}