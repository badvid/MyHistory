package com.bad.mifamilia.helpers

import android.app.Application
import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.text.Html
import android.view.View
import android.widget.Button
import android.widget.TextView
import com.bad.mifamilia.R
import com.bad.mifamilia.adapters.EtapaAdapter
import com.bad.mifamilia.adapters.FamilyAdapter
import com.bad.mifamilia.adapters.GaleryAdapter
import com.bad.mifamilia.adapters.MultimediaAdapter
import com.bad.mifamilia.models.*
import java.util.*

public final class GlobalClass : Application {
    private lateinit var popup : Dialog
    private var Url: String? = null
    private val apiKey = "U0lTTE9HQlM7UzEzTDA1OVIwRA=="
    public var oUsu: User? = null
    public var iStages: List<Etapa> = listOf<Etapa>()
    public var iGalleries: List<Galeria> = listOf<Galeria>()
    public var iMultimedias: List<Multimedia> = listOf<Multimedia>()
    public var oStage : Etapa? = null
    public var oGallery : Galeria? = null
    public var oMultimedia : Multimedia? = null
    var isAddScan = false
    public var iFamily: List<Family> = listOf<Family>()
    public var iParents: List<Parent> = listOf<Parent>()


    public lateinit var etapaAdapter : EtapaAdapter
    public lateinit var galeryAdapter : GaleryAdapter
    public lateinit var multimediaAdapter : MultimediaAdapter
    public lateinit var familyAdapter : FamilyAdapter
    constructor() { //oPedBuscar = new PedidoSearch();
    }

    /*constructor(IP: String?) {
        iP = IP
        //Apellido = apellido;
        //Nombre = nombre;
        //Foto = foto;
        //oPedBuscar = new PedidoSearch();
    }

    fun decodeDrawable(context: Context, base64: String): Drawable? {
        var ret: Drawable? = null
        if (base64 != "") {
            val bais = ByteArrayInputStream(
                Base64.decode(base64.toByteArray(), Base64.DEFAULT)
            )
            ret = Drawable.createFromResourceStream(
                context.getResources(),
                null, bais, null, null
            )
            try {
                bais.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
        return ret
    }*/
    public fun showPopUp(_context :Context, titulo: String, mje: String) {
        popup = Dialog(_context)
        popup.setContentView(R.layout.popup_add_generico)
        popup.setContentView(R.layout.pop_generico)
        popup.findViewById<TextView>(R.id.txtPopTitle).text = titulo
        popup.findViewById<TextView>(R.id.txtPopTexto).text = Html.fromHtml(mje)
        popup.findViewById<Button>(R.id.btnAceptar).setOnClickListener { popup.dismiss() }
        popup.getWindow()!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        popup.show()
    }
}