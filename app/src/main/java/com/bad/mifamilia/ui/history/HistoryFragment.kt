package com.bad.mifamilia.ui.history

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.text.Html
import android.view.*
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bad.mifamilia.R
import com.bad.mifamilia.adapters.EtapaAdapter
import com.bad.mifamilia.data.StagesPovider
import com.bad.mifamilia.databinding.FragmentHistoryBinding
import com.bad.mifamilia.helpers.GlobalClass
import com.bad.mifamilia.helpers.RetrofitHelper
import com.bad.mifamilia.models.Etapa


class HistoryFragment : Fragment() {
    private var _binding: FragmentHistoryBinding? = null
    private val binding get() = _binding!!
    private lateinit var g: GlobalClass
    private val retrofit = RetrofitHelper.getInstance()
    private lateinit var popup : Dialog
    //private historyViewModel : HistoryViewModel by viewModels()
    //private lateinit var etapaAdapter : EtapaAdapter
    //private  var lista = mutableListOf<Etapa>()
    lateinit var lista  : List<Etapa>
    companion object {
        fun newInstance() = HistoryFragment()
    }

    private lateinit var viewModel: HistoryViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        setHasOptionsMenu(true)
        super.onCreate(savedInstanceState)
       // setHasOptionsMenu(true)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentHistoryBinding.inflate(inflater, container, false)

        //return inflater.inflate(R.layout.fragment_history, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
       // (activity as AppCompatActivity).setSupportActionBar(binding.)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        setHasOptionsMenu(true)
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(HistoryViewModel::class.java)
        g = activity!!.applicationContext as GlobalClass
        /*for(i in 1..10){
            //lista.add("Hermano $i")
            lista.add(Etapa(i,0,"Etapa $i",""))
        }*/
        //viewModel.stages.
        /*CoroutineScope(Dispatchers.Main + Job()).launch(Dispatchers.IO) {

            withContext(Dispatchers.Main){
                val response: Response<StageGetResponse> = retrofit.getStages(0,1,0,10)
                if(response.isSuccessful)
                {
                    //if(response.body()!!.success) stages = response.body()!!.data

                }
            }
        }*/
        /*viewLifecycleOwner.lifecycleScope.launch {
           val response: Response<StageGetResponse> = retrofit.getStages(0,g.oUsu!!.id,0,10)
            if(response.isSuccessful)
            {
                if(response.body()!!.success) lista = response.body()!!.data

            }
            lista = viewModel.getStages(0,g.oUsu!!.id,0,10)
        }*/

        //lista = viewModel.getStages(0,g.oUsu!!.id,0,10)

        //lista = g.iStages

        lista = emptyList()
        //viewModel.onCreate(0,g.oUsu!!.id,0,10)

        //lista = viewModel.stages

        /*
        viewModel.etapas.observe(this, Observer{

        })*/
        //lista = viewModel.findStages(0,g.oUsu!!.id,0,10)

        g.etapaAdapter = EtapaAdapter(g.iStages,
            onItemSelected = {et -> onItemSelected(et)},
            onItemRemove = {et -> onItemDelete(et)}
        )
        viewModel.findStages(0,g.oUsu!!.id,0,10).observe(viewLifecycleOwner, Observer {
            g.iStages = it
            g.etapaAdapter = EtapaAdapter(it,
                onItemSelected = {et -> onItemSelected(et)},
                onItemRemove = {et -> onItemDelete(et)}
            )
            binding.rvEtapas.apply {
                layoutManager = LinearLayoutManager(context)
                adapter = g.etapaAdapter
            }
        })
        viewModel.isLoading.observe(this, Observer {
            binding.progress.isVisible = it
        })
       /* .observer(viewLifeCycleOwner, Observer { returnedrepo ->
             logic to set the textview
        })
        viewModel.stages.apply {
            lista = this
            g.etapaAdapter = EtapaAdapter(lista,
                onItemSelected = {et -> onItemSelected(et)},
                onItemRemove = {et -> onItemDelete(et)}
            )
            binding.rvEtapas.apply {
                layoutManager = LinearLayoutManager(context)
                adapter = g.etapaAdapter
            }
        }*/



       // lista = StagesPovider.iStages
       /* viewModel.etapas.observe(this, Observer{
            lista = viewModel.stages
        })*/


        //val navView: Navigation = R.id.
        //navView = findViewById(R.id.navigationView)

        //val item: MenuItem = navView.getMenu().findItem(R.id.item_hosting)
        //item.setVisible(false)
    }

    private fun onItemDelete(et: Etapa) {
        try {
            popup = Dialog(activity!!)
            popup.setContentView(R.layout.pop_generico_delete)
            val btnCerrar = popup.findViewById<Button>(R.id.btn_pop_delete_cancelar)
            val btnAceptar = popup.findViewById<Button>(R.id.btn_pop_delete_aceptar)

            popup.findViewById<TextView>(R.id.txt_pop_delete_title).text = Html.fromHtml("Eliminar: <b>${et.etapa}</b>")
            popup.findViewById<TextView>(R.id.txt_pop_delete_mensaje).text = Html.fromHtml("Tenga en cuenta que si la <b>ETAPA</b> contiene <b>Galerías</b> y <b>Fotos</b> las mismas también serán eliminadas.<br /><br />¿Esta seguro de que deseas eliminar la etapa?")

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

    private fun onItemSelected(et: Etapa) {
        //Toast.makeText(activity, "fragment onItemSelected", Toast.LENGTH_SHORT).show()
        //sf!!.sendFragment(GalleryFragment())
       // findNavController().navigate(GalleryFragment())
        g.oStage = et
       findNavController().navigate(com.bad.mifamilia.R.id.action_navigation_history_to_galleryFragment)
    }

    /*override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(com.bad.mifamilia.R.menu.menu_app, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }*/
    override fun onPrepareOptionsMenu(menu: Menu) {
        menu.findItem(com.bad.mifamilia.R.id.mnu_add_family).setVisible(false)
        menu.findItem(com.bad.mifamilia.R.id.mnu_add_galery).setVisible(false)
        menu.findItem(com.bad.mifamilia.R.id.mnu_add_photo).setVisible(false)
        return super.onPrepareOptionsMenu(menu)
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        Toast.makeText(activity, "onOptionsItemSelected", Toast.LENGTH_SHORT).show()
        // return super.onOptionsItemSelected(item)
        return  when (item.itemId){
            com.bad.mifamilia.R.id.mnu_add_stages ->{
                Toast.makeText(activity, "Agregar Etapas", Toast.LENGTH_SHORT).show()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

/* override fun onPrepOptionsMenu(menu: Menu) {
   super.onPrepareOptionsMenu(menu)
   //Toast.makeText(activity, "fragment home", Toast.LENGTH_SHORT).show()
  // if (menu.findItem(R.id.mn) != null)
   //    menu.findItem(R.id.action_messages).isVisible = false
}
override fun onPrepOptionsMenu(menu: Menu) {
   super.onPrepareOptionsMenu(menu)
   //menu.clear() //remove all items
  // activity!!.menuInflater.inflate(R.menu., menu)
}
*/
override fun onResume() {
   activity?.invalidateOptionsMenu()
   super.onResume()
}

}