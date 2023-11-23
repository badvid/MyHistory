package com.bad.mifamilia.ui.login

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.KeyEvent
import android.view.WindowManager
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.lifecycleScope
import com.bad.mifamilia.ui.home.MainActivity
import com.bad.mifamilia.databinding.ActivityLoginBinding
import com.bad.mifamilia.helpers.GlobalClass
import com.bad.mifamilia.helpers.LoadingDialog
import com.bad.mifamilia.helpers.RetrofitHelper
import com.bad.mifamilia.data.services.APIService
import com.bad.mifamilia.data.services.RetrofitService
import com.bad.mifamilia.data.services.models.UserLogin
import com.bad.mifamilia.data.services.models.UserResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private var isFullscreen: Boolean = false
    private val retrofit = RetrofitHelper.getInstance()
    var loadingDialog: LoadingDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_login)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        //getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //supportActionBar!!.hide()
        //getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);//  set status text dark
        //getWindow().setStatusBarColor(ContextCompat.getColor(BookReaderActivity.this,R.color.white));// set status background white
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        loadingDialog = LoadingDialog(this@LoginActivity)

        isFullscreen = true



        // Set up the user interaction to manually show or hide the system UI.
        //fullscreenContent = binding.fullscreenContent
       // fullscreenContent.setOnClickListener { toggle() }

       // fullscreenContentControls = binding.fullscreenContentControls

        // Upon interacting with UI controls, delay any scheduled hide()
        // operations to prevent the jarring behavior of controls going away
        // while interacting with the UI.
       // binding.dummyButton.setOnTouchListener(delayHideTouchListener)

        /*lifecycleScope.launch {
            val response: Response<UserResponse> =  retrofit.Authenticate("davidadb@gmail.com","david123")
            if(response.isSuccessful)
            {
                goHome()
            }
        }*/


        binding.btnLogin.setOnClickListener {
            loadingDialog!!.startLoadingDialog()
            lifecycleScope.launch {
                var a = UserLogin(binding.etUser.text.toString(),binding.etPass.text.toString())
                //val call: Call<UserResponse> =  retrofit.Authenticate("davidadb@gmail.com","david123")
               // val call: Call<UserResponse> = RetrofitService.getRetrofit().create(APIService::class.java).login(a)
                val call: Call<UserResponse> = retrofit.login(a)

                call.enqueue(object : Callback<UserResponse> {
                    val g: GlobalClass = applicationContext as GlobalClass
                    override fun onResponse(call: Call<UserResponse>, response: Response<UserResponse>) {
                        if (response.isSuccessful) {
                            val post = response.body()
                            // Handle the retrieved post data
                            //goHome()

                            loadingDialog!!.dismissDialog()
                            if(post!!.success)
                            {                        //u.User =txtUser.getText().toString().trim();
                                //u.Pass=txtPass.getText().toString().trim();

                                g.oUsu = post!!.data
                                binding.etUser.text?.clear()
                                binding.etPass.text?.clear()
                                //binding.tvLoginTitle.requestFocus()
                                binding.etPass.clearFocus()
                                startActivity(Intent(this@LoginActivity, MainActivity::class.java))
                            }else{
                                g.showPopUp(this@LoginActivity,"Login","Verifique Usuario o Clave")
                                //Toast.makeText(this@LoginActivity,"Verifique Usuario o Clave",Toast.LENGTH_SHORT).show();
                            }

                            //                                                                                                                                                                                                                                                                         Toast.makeText(this@LoginActivity,"Menu imgExit: " + post,Toast.LENGTH_SHORT).show();
                        } else {
                            // Handle error
                            loadingDialog!!.dismissDialog()
                        }
                    }

                    override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                        // Handle failure
                        loadingDialog!!.dismissDialog()
                        g.showPopUp(this@LoginActivity,"Login","Verifique Usuario o Clave")
                        //Toast.makeText(this@LoginActivity,"Verifique Usuario o Clave",Toast.LENGTH_SHORT).show();
                        //Toast.makeText(this@LoginActivity,"Error onFailure",Toast.LENGTH_SHORT).show();
                    }
                })
                //val call: Call<UserResponse> = RetrofitService.getRetrofit().create(APIService::class.java).login(a)

            }
            /*CoroutineScope(Dispatchers.IO).launch {

            }

            if(response.isSuccessful)
            {
                startActivity(Intent(this,MainActivity::class.java))
            }*/
            //
           // val response: Response<UserResponse> =  retrofit.log(a)
            //startActivity(Intent(this,MainActivity::class.java))
        }
        binding.btnRegister.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))

        }
    }

    fun goHome(){
        startActivity(Intent(this, MainActivity::class.java))
    }

    private fun login(){
        CoroutineScope(Dispatchers.IO).launch {
            val a = UserLogin("davidadb@gmail.com","david123")
            val call: Call<UserResponse> = RetrofitService.getRetrofit().create(APIService::class.java).login(a)
            /*val _user : UserResponse? = call.enqueue()
            val call: Response<UserResponse> = RetrofitService.getRetrofit().create(APIService::class.java).login(a)
            val resp : UserResponse? = call.body()*/
        }
    }

    /*public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.top_nav_menu,menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();
        if(id == R.id.navigation_salir){
            //finish();
            Toast.makeText(this,"Menu Salir",Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(this,"Sin item",Toast.LENGTH_SHORT).show();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.imgExit:
                Toast.makeText(this,"Menu imgExit",Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }
    }*/
    private fun salirAPP() {
        val builder = AlertDialog.Builder(this)
        builder.setMessage("¿Estás seguro que querés cerrar la app?")
            .setPositiveButton("CONFIRMAR") { dialog, which ->
                //finish();
                finish()
                System.exit(0)

                /*Intent i = new Intent(Intent.ACTION_MAIN);
                        i.addCategory(Intent.CATEGORY_HOME);
                        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(i);*/
                //finish();
            }
            .setNegativeButton(
                "CANCELAR"
            ) { dialog, which -> dialog.dismiss() }
        builder.show()
    }
    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            //finish();
            salirAPP()
            //return false;
        }
        //return super.onKeyDown(keyCode, event);
        return false
    }

    override fun onDestroy() {
        super.onDestroy()
        finish()
        // Log.d("onDestroy");
    }
}