package com.bad.mifamilia.ui.login

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.lifecycleScope
import com.bad.mifamilia.databinding.ActivityRegisterBinding
import com.bad.mifamilia.helpers.LoadingDialog
import com.bad.mifamilia.helpers.RetrofitHelper
import com.bad.mifamilia.data.services.models.UserPost
import com.bad.mifamilia.data.services.models.UserPostResponse
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding
    private var isFullscreen: Boolean = false
    var loadingDialog: LoadingDialog? = null
    private val retrofit = RetrofitHelper.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //supportActionBar!!.hide()

        //setContentView(R.layout.activity_register)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        loadingDialog = LoadingDialog(this@RegisterActivity)

        binding.btnVolver.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }

        binding.btnRegistrarse.setOnClickListener {
            if(validarForm(binding)){
                loadingDialog!!.startLoadingDialog()
                lifecycleScope.launch {
                    var u : UserPost = UserPost(0,binding.etApellido.text.toString(), binding.etNombres.text.toString(),binding.etDoc.text.toString()
                    ,binding.etCorreo.text.toString(),"",binding.etCorreo.text.toString(),binding.etClave.text.toString())

                    val call: Call<UserPostResponse> = retrofit.RegisterUser(u)
                    call.enqueue(object : Callback<UserPostResponse> {
                        override fun onResponse(call: Call<UserPostResponse>, response: Response<UserPostResponse>) {
                           // copyToClip(response.toString())
                            if (response.isSuccessful) {
                                val post = response.body()
                                // Handle the retrieved post data
                                //goHome()

                                loadingDialog!!.dismissDialog()
                                if(post!!.success)
                                {
                                    startActivity(Intent(this@RegisterActivity, LoginActivity::class.java))
                                }else{
                                    Toast.makeText(this@RegisterActivity,"Por favor intentelo de nuevo!",Toast.LENGTH_SHORT).show();
                                }

                                //                                                                                                                                                                                                                                                                         Toast.makeText(this@LoginActivity,"Menu imgExit: " + post,Toast.LENGTH_SHORT).show();
                            } else {
                                // Handle error
                            }
                        }

                        override fun onFailure(call: Call<UserPostResponse>, t: Throwable) {
                            // Handle failure
                            loadingDialog!!.dismissDialog()
                            Toast.makeText(this@RegisterActivity,"Verifique Usuario o Clave",Toast.LENGTH_SHORT).show();
                            //Toast.makeText(this@LoginActivity,"Error onFailure",Toast.LENGTH_SHORT).show();
                        }
                    })

                }
            }
        }

    }
    fun copyToClip(_text : String){
        val clipboard = this.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clip: ClipData = ClipData.newPlainText("simple text", _text)
        clipboard.setPrimaryClip(clip)
    }
    private fun validarForm(b : ActivityRegisterBinding) : Boolean{
        var isValid : Boolean = true

        if (b.etApellido.text.toString().trim().isBlank())
        {
            isValid=false
            b.lyApellido.error = "* Campo Requerido"
        }else{b.lyApellido.error = null}
        if (b.etNombres.text.toString().trim().isBlank())
        {
            isValid=false
            b.lyNombres.error = "* Campo Requerido"
        }else{b.lyNombres.error = null}
        if (b.etDoc.text.toString().trim().isBlank())
        {
            isValid=false
            b.lyDoc.error = "* Campo Requerido"
        }else{ b.lyDoc.error = null}
        if (b.etCorreo.text.toString().trim().isBlank())
        {
            isValid=false
            b.lyCorreo.error = "* Campo Requerido"
        }else{b.lyCorreo.error = null}
        if(!android.util.Patterns.EMAIL_ADDRESS.matcher(b.etCorreo.text.toString()).matches())
        {
            isValid=false
            b.lyCorreo.error = "* Por favor ingrese una direccion de correo valida!"
        }

        if (b.etClave.text.toString().trim().isBlank())
        {
            isValid=false
            b.lyClave.error = "* Campo Requerido"
        }else{b.lyClave.error = null}
        if (b.etReClave.text.toString().trim().isBlank())
        {
            isValid=false
            b.lyReClave.error = "* Campo Requerido"
        }else{b.lyReClave.error = null}

        if(b.etClave.text.toString().trim() != b.etReClave.text.toString().trim()){
            isValid=false
            Toast.makeText(this@RegisterActivity,"Verifique que las claves sean iguales!", Toast.LENGTH_SHORT).show();
            b.lyClave.error = "* Las claves NO son iguales"
            b.lyReClave.error = "* Las claves NO son iguales"
        }


        return isValid

    }
}