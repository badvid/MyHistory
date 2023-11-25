package com.bad.mifamilia.ui.history

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.text.Html
import android.view.*
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.bad.mifamilia.R
import com.bad.mifamilia.adapters.FamilyAdapter
import com.bad.mifamilia.adapters.GaleryAdapter
import com.bad.mifamilia.databinding.FragmentFamilyBinding
import com.bad.mifamilia.databinding.FragmentGalleryBinding
import com.bad.mifamilia.helpers.GlobalClass
import com.bad.mifamilia.models.Family
import com.bad.mifamilia.models.Galeria

class GalleryFragment : Fragment() {
    private var _binding: FragmentGalleryBinding? = null
    private val binding get() = _binding!!
    private lateinit var g: GlobalClass

    private lateinit var popup : Dialog

    private lateinit var galeryAdapter: GaleryAdapter

    companion object {
        fun newInstance() = GalleryFragment()
    }

    private lateinit var viewModel: GalleryViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        setHasOptionsMenu(true)
        super.onCreate(savedInstanceState)
        // setHasOptionsMenu(true)

    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        //return inflater.inflate(R.layout.fragment_gallery, container, false)
        _binding = FragmentGalleryBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(GalleryViewModel::class.java)
        // TODO: Use the ViewModel

        g = activity!!.applicationContext as GlobalClass

        g.oStage!!.iGallery?.let {
            g.iGalleries = it
            g.galeryAdapter = GaleryAdapter(it,
                onItemSelected = {obj -> onItemSelected(obj)},
                onItemRemove = {obj -> onItemDelete(obj)}
            )
            binding.rvGalery.apply {
                layoutManager = GridLayoutManager(context,3)
                adapter = g.galeryAdapter
            }
        }

        /*galeryAdapter = g.oStage!!.iGallery?.let {
            GaleryAdapter(it,
                onItemSelected = {f -> onItemSelected(f)},
                onItemRemove = {f -> onItemDelete(f)}
            )
        }!!*/
        //LinearLayoutManager(this@MainActivity)
        //GridLayoutManager(requireContext(),2)


        GalleryFragment.apply {

        }
    }
    private fun onItemDelete(obj: Galeria) {
        try {
            popup = Dialog(activity!!)
            popup.setContentView(R.layout.pop_generico_delete)
            val btnCerrar = popup.findViewById<Button>(R.id.btn_pop_delete_cancelar)
            val btnAceptar = popup.findViewById<Button>(R.id.btn_pop_delete_aceptar)

            popup.findViewById<TextView>(R.id.txt_pop_delete_title).text = Html.fromHtml("Eliminar: <b>${obj.name}</b>")
            popup.findViewById<TextView>(R.id.txt_pop_delete_mensaje).text = Html.fromHtml("Tenga en cuenta que si la <b>GALERIA</b> contiene <b>Fotos</b> las mismas también serán eliminadas.<br /><br />¿Esta seguro de que deseas eliminar la Galería?")

            popup.getWindow()!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            popup.show()

            btnAceptar.setOnClickListener {
                Toast.makeText(activity,"Registro Eliminado",Toast.LENGTH_SHORT).show()
                popup.dismiss()
            }
            btnCerrar.setOnClickListener {
                popup.dismiss()
            }
        }catch (ex:Exception){
            //Toast.makeText(activity,ex.message.toString(),Toast.LENGTH_SHORT).show()
        }
    }

    private fun onItemSelected(obj: Galeria) {
        //Toast.makeText(activity, "fragment onItemSelected", Toast.LENGTH_SHORT).show()
        g.oGallery = obj
        findNavController().navigate(com.bad.mifamilia.R.id.action_galleryFragment_to_multimediaFragment)
    }
    override fun onPrepareOptionsMenu(menu: Menu) {
        menu.findItem(com.bad.mifamilia.R.id.mnu_add_stages).setVisible(false)
        menu.findItem(com.bad.mifamilia.R.id.mnu_add_family).setVisible(false)
        menu.findItem(com.bad.mifamilia.R.id.mnu_add_photo).setVisible(false)

        menu.findItem(com.bad.mifamilia.R.id.mnu_add_galery).setVisible(true)
        return super.onPrepareOptionsMenu(menu)
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // return super.onOptionsItemSelected(item)popup_add_generico.xml
        if(item.itemId == android.R.id.home){
            //getSupportFragmentManager().beginTransaction().remove(this).commit();
            //Toast.makeText(activity, "fragment onOptionsItemSelected", Toast.LENGTH_SHORT).show()
            //getFragmentManager()!!.beginTransaction()!!.remove(this).commit();
            findNavController().navigate(com.bad.mifamilia.R.id.action_galleryFragment_to_navigation_history)
        }
        return true
    }

}