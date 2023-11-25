package com.bad.mifamilia.ui.family

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.text.Html
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.bad.mifamilia.*
import com.bad.mifamilia.adapters.FamilyAdapter
import com.bad.mifamilia.adapters.MultimediaAdapter
import com.bad.mifamilia.databinding.FragmentFamilyBinding
import com.bad.mifamilia.helpers.GlobalClass
import com.bad.mifamilia.models.Family

class FamilyFragment : Fragment() {
    private var _binding: FragmentFamilyBinding? = null
    private val binding get() = _binding!!
    private lateinit var g: GlobalClass

    private lateinit var popup : Dialog
    //private  var lista : List<Family>  = listOf()
    private lateinit var familyAdapter : FamilyAdapter
    private  var lista = mutableListOf<Family>()
    companion object {
        fun newInstance() = FamilyFragment()
    }

    private lateinit var viewModel: FamilyViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        setHasOptionsMenu(true)
        super.onCreate(savedInstanceState)
        // setHasOptionsMenu(true)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFamilyBinding.inflate(inflater, container, false)

        //val lista: ArrayList<String> = ArrayList()
       // lista = ArrayList<Family>()
        //lista = listOf()


        //return inflater.inflate(R.layout.fragment_family, container, false)
        return binding.root
    }

    private fun onItemDelete(f: Family) {
        try {
            popup = Dialog(activity!!)
            popup.setContentView(R.layout.pop_generico_delete)
            val btnCerrar = popup.findViewById<Button>(R.id.btn_pop_delete_cancelar)
            val btnAceptar = popup.findViewById<Button>(R.id.btn_pop_delete_aceptar)

            popup.findViewById<TextView>(R.id.txt_pop_delete_title).text = Html.fromHtml("Eliminar: ${f.firstName} ${f.lastName}")
            popup.findViewById<TextView>(R.id.txt_pop_delete_mensaje).text = Html.fromHtml("¿Esta seguro de que deseas eliminar al familiar?")

            popup.getWindow()!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            popup.show()

            btnAceptar.setOnClickListener {
                Toast.makeText(activity,"Registro Eliminado", Toast.LENGTH_SHORT).show()
                popup.dismiss()
            }
            btnCerrar.setOnClickListener {
                popup.dismiss()
            }
        }catch (ex:Exception){
            //Toast.makeText(activity,ex.message.toString(),Toast.LENGTH_SHORT).show()
        }
    }

    private fun onItemSelected(f: Family) {

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(FamilyViewModel::class.java)
        // TODO: Use the ViewModel
        g = activity!!.applicationContext as GlobalClass
        /*for(i in 1..10){
            //lista.add("Hermano $i")
            lista.add(Family(i,"Hermano $i"))
        }*/

        g.familyAdapter = FamilyAdapter(g.iFamily,
            onItemSelected = {et -> onItemSelected(et)},
            onItemRemove = {et -> onItemDelete(et)}
        )
        viewModel.findFamilies(0,g.oUsu!!.id,0,10).observe(viewLifecycleOwner, Observer {
            g.iFamily = it
            g.familyAdapter = FamilyAdapter(it,
                onItemSelected = {et -> onItemSelected(et)},
                onItemRemove = {et -> onItemDelete(et)}
            )
            binding.rvFamily.apply {
                layoutManager = GridLayoutManager(context,2)
                adapter = g.familyAdapter
            }

        })
        viewModel.isLoading.observe(this, Observer {
            binding.progress.isVisible = it
        })



        //LinearLayoutManager(this@MainActivity)
        //GridLayoutManager(requireContext(),2)

    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        menu.findItem(R.id.mnu_add_stages).setVisible(false)
        menu.findItem(R.id.mnu_add_galery).setVisible(false)
        menu.findItem(R.id.mnu_add_photo).setVisible(false)

        menu.findItem(R.id.mnu_add_family).setVisible(true)
        return super.onPrepareOptionsMenu(menu)
    }

}