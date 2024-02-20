package com.bad.mifamilia.ui.history

import android.app.Dialog
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.text.Html
import android.util.Base64
import android.view.*
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.bad.mifamilia.R
import com.bad.mifamilia.adapters.*
import com.bad.mifamilia.databinding.FragmentMultimediaBinding
import com.bad.mifamilia.helpers.GlobalClass
import com.bad.mifamilia.models.*
import com.bumptech.glide.Glide
import com.squareup.picasso.Picasso

class MultimediaFragment : Fragment() {
    private var _binding: FragmentMultimediaBinding? = null
    private val binding get() = _binding!!
    private lateinit var g: GlobalClass

    private lateinit var popup : Dialog
    private lateinit var galeryAdapter: MultimediaAdapter
    lateinit var lista  : List<Multimedia>

    companion object {
        fun newInstance() = MultimediaFragment()
    }

    private lateinit var viewModel: MultimediaViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        setHasOptionsMenu(true)
        super.onCreate(savedInstanceState)
        // setHasOptionsMenu(true)

    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        //return inflater.inflate(R.layout.fragment_multimedia, container, false)
        _binding = FragmentMultimediaBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MultimediaViewModel::class.java)
        // TODO: Use the ViewModel
        g = activity!!.applicationContext as GlobalClass

        binding.tvGaleryTitle.text = g.oGallery!!.name.toString().toUpperCase()
        binding.tvGaleryLocation.text = g.oGallery!!.location
        binding.tvGaleryDesc.text = g.oGallery!!.descrip
        //binding.ivGaleryPhoto.setImageBitmap(convertBase64ToBitmap(g.oGallery!!.photo))
        /*Picasso.get().load(g.oGallery!!.photo).fit()
            .placeholder(R.drawable.placeholder)
            .error(R.drawable.placeholder)
            .into(binding.ivGaleryPhoto)*/
        Glide
            .with(this)
            .load(g.oGallery!!.photo)
            .centerCrop()
            .skipMemoryCache(false)
            .placeholder(R.drawable.placeholder)
            .error(R.drawable.placeholder)
            .into(binding.ivGaleryPhoto)

        lista = emptyList()

        g.multimediaAdapter = MultimediaAdapter(g.iMultimedias,
            onItemSelected = {et -> onItemSelected(et)},
            onItemRemove = {et -> onItemDelete(et)}
        )
        viewModel.findSMultimedias(0,g.oGallery!!.id,0,10).observe(viewLifecycleOwner, Observer {
            g.iMultimedias = it
            g.multimediaAdapter = MultimediaAdapter(it,
                onItemSelected = {et -> onItemSelected(et)},
                onItemRemove = {et -> onItemDelete(et)}
            )
            binding.rvItemMultimedia.apply {
                layoutManager = GridLayoutManager(context,3)
                adapter = g.multimediaAdapter
            }
        })
        viewModel.isLoading.observe(this, Observer {
            binding.progress.isVisible = it
        })

    }

    private fun onItemDelete(et: Multimedia) {
        try {
            popup = Dialog(activity!!)
            popup.setContentView(R.layout.pop_generico_delete)
            val btnCerrar = popup.findViewById<Button>(R.id.btn_pop_delete_cancelar)
            val btnAceptar = popup.findViewById<Button>(R.id.btn_pop_delete_aceptar)

            popup.findViewById<TextView>(R.id.txt_pop_delete_title).text = Html.fromHtml("Eliminar Foto")
            popup.findViewById<TextView>(R.id.txt_pop_delete_mensaje).text = Html.fromHtml("¿Esta seguro de que deseas eliminar el registro?")

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

    private fun onItemSelected(et: Multimedia) {
        g.oMultimedia = et
        findNavController().navigate(com.bad.mifamilia.R.id.action_multimediaFragment_to_itemFragment)
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        menu.findItem(com.bad.mifamilia.R.id.mnu_add_stages).setVisible(false)
        menu.findItem(com.bad.mifamilia.R.id.mnu_add_family).setVisible(false)
        menu.findItem(com.bad.mifamilia.R.id.mnu_add_galery).setVisible(false)

        menu.findItem(com.bad.mifamilia.R.id.mnu_add_photo).setVisible(true)
        return super.onPrepareOptionsMenu(menu)
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // return super.onOptionsItemSelected(item)popup_add_generico.xml
        if(item.itemId == android.R.id.home){
            //getSupportFragmentManager().beginTransaction().remove(this).commit();
            //Toast.makeText(activity, "fragment onOptionsItemSelected", Toast.LENGTH_SHORT).show()
            //getFragmentManager()!!.beginTransaction()!!.remove(this).commit();
            findNavController().navigate(com.bad.mifamilia.R.id.action_multimediaFragment_to_galleryFragment)
        }
        return true
    }
    private fun convertBase64ToBitmap(b64: String): Bitmap {
        val imageAsBytes: ByteArray = Base64.decode(b64.toByteArray(), Base64.DEFAULT)
        return BitmapFactory.decodeByteArray(imageAsBytes, 0, imageAsBytes.size)
    }
}