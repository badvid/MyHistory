package com.bad.mifamilia.ui.family

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.bad.mifamilia.R
import com.bad.mifamilia.adapters.FamilyAdapter
import com.bad.mifamilia.databinding.FragmentFamilyBinding
import com.bad.mifamilia.models.Family

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [BlankFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class BlankFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var _binding: FragmentFamilyBinding? = null
    private val binding get() = _binding!!

    private  var lista = mutableListOf<Family>()
    private lateinit var familyAdapter : FamilyAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentFamilyBinding.inflate(inflater, container, false)

        try {
            Log.i(TAG, "Hello World")
           /* for(i in 1..10){
                //lista.add("Hermano $i")
                lista.add(Family(i,"Hermano $i"))
            }
            Log.i(TAG, "Lista ${lista.count()}")
            familyAdapter = FamilyAdapter(lista,
                onItemSelected = {f -> onItemSelected(f)},
                onItemRemove = {f -> onItemDelete(f)}
            )*/
            //LinearLayoutManager(this@MainActivity) LinearLayoutManager(context)
            //GridLayoutManager(requireContext(),2)
            binding.rvFamily.apply {
                layoutManager = GridLayoutManager(context,2)
                adapter = familyAdapter
            }

            Toast.makeText(context, "llego", Toast.LENGTH_SHORT).show()
        }
        catch (e:Exception)
        {
            Toast.makeText(context, "error: ${e.message}", Toast.LENGTH_SHORT).show()
        }

        return binding.root
    }

    private fun onItemDelete(f: Family) {

    }

    private fun onItemSelected(f: Family) {
        TODO("Not yet implemented")
    }


}