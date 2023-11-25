package com.bad.mifamilia.ui.home

import android.annotation.SuppressLint
import android.app.Activity
import android.app.Dialog
import android.content.*
import android.graphics.*
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.media.ExifInterface
import android.media.MediaScannerConnection
import android.net.Uri
import android.opengl.Visibility
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.text.Html
import android.util.Base64
import android.view.*
import android.widget.*
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.FileProvider
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.bad.mifamilia.BuildConfig
import com.bad.mifamilia.R
import com.bad.mifamilia.adapters.ParentAutoCompleteAdapter
import com.bad.mifamilia.data.services.models.*
import com.bad.mifamilia.databinding.ActivityMainBinding
import com.bad.mifamilia.helpers.GlobalClass
import com.bad.mifamilia.helpers.LoadingDialog
import com.bad.mifamilia.helpers.RetrofitHelper
import com.bad.mifamilia.models.*
import com.google.android.material.badge.BadgeDrawable
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.textfield.TextInputLayout
import kotlinx.coroutines.launch
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.*


class MainActivity : AppCompatActivity()  {
    private lateinit var binding: ActivityMainBinding
    private lateinit var g: GlobalClass // = applicationContext as GlobalClass
    public lateinit var _menutoolbar : Menu
    var loadingDialog: LoadingDialog? = null
    private val retrofit = RetrofitHelper.getInstance()
    private lateinit var popup : Dialog
    private lateinit var  badge : BadgeDrawable
    private var contador = 0
    private var thumbnail: Bitmap? = null
    private var values: ContentValues? = null
    private var imageUri: Uri? = null
    private lateinit var file : File
    private var _fileName : String=""
    private lateinit var _file64string : String
    private lateinit var viewModel: MainViewModel

    private var oEtapa : Etapa? = null
    private var oGaleria : Galeria? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_main)
        //supportActionBar!!.hide()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        badge = BadgeDrawable.create(this)
        try {
            loadingDialog = LoadingDialog(this@MainActivity)
        }catch (e:Exception) {}

        try {
            g = applicationContext as GlobalClass
            setupToolbar()
            //hideOption(R.id.mnu_new_photo)
            //R.id.action_search
            //binding.myToolbar.menu.get(1).setEnabled(false)
            //setSupportActionBar(binding.myToolbar)
            //supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }catch (e:Exception)
        {
            //binding
            Toast.makeText(this, "error: ${e.message}", Toast.LENGTH_SHORT).show()
            val clipboard = this.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            val clip: ClipData = ClipData.newPlainText("simple text", e.message.toString())
            clipboard.setPrimaryClip(clip)
        }


        // android:paddingTop="?attr/actionBarSize"
        //keep the app in light mode
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        //var w : Window
       //w.statusBarColor(Color.parseColor("#FFFFFF"))

        val navView: BottomNavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        navView.menu.getItem(2).setEnabled(false)

        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home,
                R.id.navigation_history,
                R.id.navigation_hide,
                R.id.navigation_family,
                R.id.navigation_settings
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        //binding.btnAdd.visibility =View.GONE
        binding.btnAdd.setOnClickListener {
            //takePhoto()
            //getAction.launch(Intent(MediaStore.ACTION_IMAGE_CAPTURE))
            val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE).also{
                it.resolveActivity(packageManager).also{ component ->
                    file = createPhotoFile()
                    val photoFile = file
                    val photoUri : Uri =
                        FileProvider.getUriForFile(
                            this,
                            BuildConfig.APPLICATION_ID + ".fileprovider",
                            photoFile
                        )

                    it.putExtra(MediaStore.EXTRA_OUTPUT, photoUri)
                    // Toast.makeText(this, "resolveActivity ", Toast.LENGTH_SHORT).show()
                }
            }
            takePhotoAdd.launch(intent)
        }

        //popup
       // Toast.makeText(this, "error: ${navView.menu.}", Toast.LENGTH_SHORT).show()
       /* if(savedInstanceState == null){
            lifecycleScope.launch {
                val response: Response<StageGetResponse> = retrofit.getStages(0,g.oUsu!!.id,0,10)
                //  val response = retrofit.getStages()
                if(response.isSuccessful)
                {
                    if(response.body()!!.success) g.iStages = response.body()!!.data

                }
            }
        }*/
        /*
        lifecycleScope.launch {
            val response: Response<StageGetResponse> = retrofit.getStages(0,g.oUsu!!.id,0,10)
            //  val response = retrofit.getStages()
            if(response.isSuccessful)
            {
                if(response.body()!!.success) g.iStages = response.body()!!.data

            }

            val resp : Response<ParentGetResponse> = retrofit.getParents()
            if(resp.isSuccessful)
                if(resp.body()!!.success) g.iParents = resp.body()!!.data

            val respFamily : Response<FamilyGetResponse> = retrofit.getFamily(0,g.oUsu!!.id,0,30)
            if(respFamily.isSuccessful)
                if(respFamily.body()!!.success) g.iFamily = respFamily.body()!!.data

        }
        */
        popup = Dialog(this)
        viewModel.setearPopUp(popup)
        viewModel.popup.observe(this, Observer {
            popup = it
        })
        viewModel.file.observe(this, Observer {
            file = it
        })

        lifecycleScope.launch {

            val resp : Response<ParentGetResponse> = retrofit.getParents()
            if(resp.isSuccessful)
                if(resp.body()!!.success) g.iParents = resp.body()!!.data

            val response: Response<StageGetResponse> = retrofit.getStages(0,g.oUsu!!.id,0,10)
            if(response.isSuccessful)
                if(response.body()!!.success) g.iStages = response.body()!!.data


        }

    }

    fun setupToolbar(){
        setSupportActionBar(binding.myToolbar)
        //showHomeUpIcon()
    }

    private fun showHomeUpIcon() {
       supportActionBar?.let {
           supportActionBar?.setDisplayHomeAsUpEnabled(true)
           //imageView.setColorFilter(getResources().getColor(R.color.blue)
       }
    }

    fun SetUpHomeUpIconAndColor(drawable : Int, color:Int){
        supportActionBar?.let {
            val icon : Drawable = resources.getDrawable(drawable)
            //icon.colorFilter (resources.getColor(color),PorterDuff.Mode.SRC_ATOP)
            //icon.colorFilter = resources.getColor(color), PorterDuff.Mode.SRC_ATOP
            icon.setColorFilter(getResources().getColor(color),PorterDuff.Mode.SRC_ATOP)
            supportActionBar?.setHomeAsUpIndicator(icon)
        }
    }

    @SuppressLint("UnsafeOptInUsageError")
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_app, menu)
        //BadgeUtils.attachBadgeDrawable(badge, binding.myToolbar,R.id.mnu_add)
       /// badge.number = contador
        //Toast.makeText(this, "onCreateOptionsMenu", Toast.LENGTH_SHORT).show()
        return super.onCreateOptionsMenu(menu)
    }
    override fun onPrepareOptionsMenu(menu: Menu): Boolean {
        _menutoolbar = menu
        //var item: MenuItem = _menu.findItem(R.id.mnu_new_galery)
        //item.setEnabled(false)
        //item.setVisible(false)
        //toggleItem()
        //Toast.makeText(this, "onPrepareOptionsMenu", Toast.LENGTH_SHORT).show()

       // menu.findItem(com.bad.mifamilia.R.id.mnu_add_stages).setVisible(false)
        menu.findItem(R.id.mnu_add_galery).setVisible(false)
        menu.findItem(R.id.mnu_add_photo).setVisible(false)
        menu.findItem(R.id.mnu_add_family).setVisible(false)
        return super.onPrepareOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
       // return super.onOptionsItemSelected(item)

        return when (item.itemId){
            R.id.mnu_add_stages -> {
                //badge.number = contador++
               /* val btnCerrar = popup.findViewById<View>(R.id.img_close)
                val btnAceptar = popup.findViewById<View>(R.id.btnAceptar)
               // popup.findViewById<View>(R.id.txtPopTitle).text = "Detalle del Scaneo"
                popup.findViewById<TextView>(R.id.txt_pop_text).text =
                    Html.fromHtml("¡Ingrese un nombre para la <b>Etapa</b> de su vida que desee registrar!.")

                popup.findViewById<TextInputLayout>(R.id.ly_dato).hint = "Etapa de mi vida"
                val etDato = popup.findViewById<EditText>(R.id.et_dato)

                popup.getWindow()!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                popup.show()

                btnAceptar.setOnClickListener {
                    popup.dismiss()
                    Toast.makeText(this, "Agregar Etapas", Toast.LENGTH_SHORT).show()
                }
                btnCerrar.setOnClickListener {
                    popup.dismiss()
                }*/
                settingPopUp("¡Ingrese un nombre para la <b>Etapa</b> de su vida que desee registrar!.","Nombre de la Etapa",false, ::guardarEtapas)
                true
            }
            R.id.mnu_add_galery ->{
                settingPopUp("¡Ingrese un nombre para la <b>Galeria</b> que desee registrars!.","Nombre de la Galeria",true, ::guardarGaleria)
               // Toast.makeText(this, "Agregar Galeria", Toast.LENGTH_SHORT).show()
                true
            }
            R.id.mnu_add_photo ->{
                //Toast.makeText(this, "Agregar Foto", Toast.LENGTH_SHORT).show()
                val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE).also{
                    it.resolveActivity(packageManager).also{ component ->
                        val photoFile = createPhotoFile()
                        val photoUri : Uri =
                            FileProvider.getUriForFile(
                                this,
                                BuildConfig.APPLICATION_ID + ".fileprovider",
                                photoFile
                            )

                        it.putExtra(MediaStore.EXTRA_OUTPUT, photoUri)

                        // Toast.makeText(this, "resolveActivity ", Toast.LENGTH_SHORT).show()
                    }
                }
                takePhoto.launch(intent)




                true
            }
            R.id.mnu_add_family ->{
               // settingPopUp("¡Ingrese un nombre para la <b>Galeria</b> que desee registrars!.","Nombre de la Galeria", ::guardarGaleria)
                settingPopUpFamily()
                //settingPopUp("¡Ingrese un nombre para la <b>Galeria</b> que desee registrars!.","Nombre de la Galeria",
                //    { guardarGaleria() })
                //Toast.makeText(this, "Agregar Familia", Toast.LENGTH_SHORT).show()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
       // return super.onOptionsItemSelected(item)
    }

    private fun settingPopUp(_mje : String, _hint : String, _show: Boolean, _funtion: (_b : MutableList<String>, _file :File)->Unit){
        try {
            popup = Dialog(this)
            popup.setContentView(R.layout.popup_add_generico)

            val btnCerrar = popup.findViewById<ImageView>(R.id.img_close)
            val btnPhoto = popup.findViewById<ConstraintLayout>(R.id.img_foto_bg)
            val btnAceptar = popup.findViewById<Button>(R.id.btnAceptar)

            // popup.findViewById<View>(R.id.txtPopTitle).text = "Detalle del Scaneo"
            popup.findViewById<TextView>(R.id.txt_pop_g_text).text =
                Html.fromHtml(_mje)

            popup.findViewById<TextInputLayout>(R.id.ly_dato).hint = _hint
            val etDato = popup.findViewById<EditText>(R.id.et_dato)
            val etLocation = popup.findViewById<EditText>(R.id.et_location)
            val etObs = popup.findViewById<EditText>(R.id.et_obs)

            if(_show)
            {
                etLocation.isVisible = true
                etObs.isVisible = true
            }
            popup.getWindow()!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            popup.show()

            btnPhoto.setOnClickListener {
                //if(txtApenom.getText().toString().trim().length()>0 && txtDni.getText().toString().trim().length()>0){
                /* values = ContentValues()
                 values!!.put(MediaStore.Images.Media.TITLE, "MyPicture")
                 values!!.put(
                     MediaStore.Images.Media.DESCRIPTION,
                     "Photo taken on " + System.currentTimeMillis()
                 )*/

                //String dir = "Pictures/Fore/" + fileName:
                // String fileName = "temp"; //  System.currentTimeMillis() + "image_example";
                //  values.put(MediaStore.Images.Media.DISPLAY_NAME,fileName);
                // values.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg");
                // values.put(MediaStore.Images.Media.RELATIVE_PATH, "Pictures/Fore");
              /*  imageUri = contentResolver.insert(
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values
                )*/
                //takeFoto.launch(Intent(MediaStore.ACTION_IMAGE_CAPTURE))

                val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE).also{
                    it.resolveActivity(packageManager).also{ component ->
                        val photoFile = createPhotoFile()
                        val photoUri : Uri =
                            FileProvider.getUriForFile(
                                this,
                                BuildConfig.APPLICATION_ID + ".fileprovider",
                                photoFile
                            )

                        it.putExtra(MediaStore.EXTRA_OUTPUT, photoUri)
                       // Toast.makeText(this, "resolveActivity ", Toast.LENGTH_SHORT).show()
                    }
                }
                takeFoto.launch(intent)

            }
            btnAceptar.setOnClickListener {
                //popup.dismiss()
               // Toast.makeText(this, "Agregar Familia", Toast.LENGTH_SHORT).show()
                //guardarFoto()
                var list: MutableList<String> = mutableListOf()
                //_b.toMutableList().add()
                list.add(etDato.text.trim().toString())
                if(_show)
                {
                    list.add(etLocation.text.trim().toString())
                    list.add(etObs.text.trim().toString())
                    _funtion(list, file)
                }else{
                    _funtion(list, file)
                }

                saveToGallery()
                /*val imageBitmap : Bitmap = BitmapFactory.decodeFile(file.toString())
                val _img:Drawable = BitmapDrawable(imageBitmap)
                popup.findViewById<ConstraintLayout>(R.id.img_foto_bg).background = _img*/
            }
            btnCerrar.setOnClickListener {
                popup.dismiss()
            }
        }catch (e :Exception)
        {}
    }
    private fun settingPopUpFamily(){
        try {
            popup = Dialog(this)
            popup.setContentView(R.layout.popup_add_family)

            val _parent =  popup.findViewById<AutoCompleteTextView>(R.id.sp_parent)
            val _apellido =  popup.findViewById<EditText>(R.id.et_g_apellido)
            val _nombres =  popup.findViewById<EditText>(R.id.et_g_nombres)
            val _doc =  popup.findViewById<EditText>(R.id.et_nro_doc)
            val _correo =  popup.findViewById<EditText>(R.id.et_correo)

            val btnCerrar = popup.findViewById<View>(R.id.img_close)
            val btnPhoto = popup.findViewById<View>(R.id.img_foto)
            val btnAceptar = popup.findViewById<View>(R.id.btnAceptar)

            val aParent = ParentAutoCompleteAdapter(this, g.iParents)
            _parent.setAdapter(aParent)


            popup.findViewById<TextView>(R.id.txt_pop_g_text).text =
                Html.fromHtml("¡Ingrese un familiar para la seccion <b>Mi Familia</b> que desee registrars!.")

            //popup.findViewById<TextInputLayout>(R.id.ly_dato).hint = _hint
            //val etDato = popup.findViewById<EditText>(R.id.et_dato)

            popup.getWindow()!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            popup.show()

            btnPhoto.setOnClickListener {
                val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE).also{
                    it.resolveActivity(packageManager).also{ component ->
                        val photoFile = createPhotoFile()
                        val photoUri : Uri =
                            FileProvider.getUriForFile(
                                this,
                                BuildConfig.APPLICATION_ID + ".fileprovider",
                                photoFile
                            )

                        it.putExtra(MediaStore.EXTRA_OUTPUT, photoUri)
                        Toast.makeText(this, "resolveActivity ", Toast.LENGTH_SHORT).show()
                    }
                }
                takeFoto.launch(intent)

            }
            btnAceptar.setOnClickListener {
                //popup.dismiss()
                //Toast.makeText(this, "Agregar Familia", Toast.LENGTH_SHORT).show()
                //guardarFoto()

                if(validarFamily(popup)){
                    val parent : Parent? = g.iParents.find { p -> _parent.text.contains(p.descrip) }
                    var obj : FamilyPost = FamilyPost(0,g.oUsu!!.id,  parent!!.id,
                        _apellido.text.toString(),_nombres.text.toString(),_doc.text.toString(),_correo.text.toString(),"",_file64string)

                    guardarFamilia(obj)
                }


            }
            btnCerrar.setOnClickListener {
                popup.dismiss()
            }
        }catch (e :Exception)
        {}
    }

    private fun createPhotoFile(): File{
        var dir = getExternalFilesDir(Environment.DIRECTORY_PICTURES)!!
        file = File.createTempFile("IMG_${System.currentTimeMillis()}_",".jpg",dir)
        _fileName = file.absolutePath
        viewModel.setearFile(file)
        return file
    }
    private fun getBitmap(): Bitmap{
        return BitmapFactory.decodeFile(file.toString())
    }
    private fun saveToGallery(){
        //CREAR UN CONTENEDOR
        val content = createContent()
        //GUARDAR LA IMAGEN
        val uri = save(content)
        //LIMPIAR EL CONTENEDOR
        clearContents(content, uri)
    }
    private fun createContent(): ContentValues{
        val fileName = file.name
        val fileType = "image/jpg"
        //Toast.makeText(this, "createContent ", Toast.LENGTH_SHORT).show()
        return ContentValues().apply {

            put(MediaStore.MediaColumns.DISPLAY_NAME, fileName)
            put(MediaStore.Files.FileColumns.MIME_TYPE, fileType)
            //put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_PICTURES)
            put(MediaStore.MediaColumns.RELATIVE_PATH, "${Environment.DIRECTORY_PICTURES}/MyHistory")
            put(MediaStore.MediaColumns.IS_PENDING,1)
        }
    }
    private fun save(content: ContentValues): Uri{
        var outputStream : OutputStream?
        var uri : Uri?
        application.contentResolver.also{resolver ->
            uri = resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, content)
            outputStream = resolver.openOutputStream(uri!!)
        }
        outputStream.use { output ->
            getBitmap().compress(Bitmap.CompressFormat.JPEG,100,output!!)
        }
        return uri!!
    }
    private fun clearContents(content: ContentValues, uri: Uri){
        content.clear()
        content.put(MediaStore.MediaColumns.IS_PENDING,0)
        contentResolver.update(uri,content,null,null)
    }
    private fun encodeImage(bm: Bitmap): String? {
        val baos = ByteArrayOutputStream()
        bm.compress(Bitmap.CompressFormat.JPEG, 100, baos)
        val b = baos.toByteArray()
        return Base64.encodeToString(b, Base64.DEFAULT)
    }
    private fun guardarEtapas(list : MutableList<String>, _file :File){
        loadingDialog!!.startLoadingDialog()
        //var _file64string : String = Base64.encodeToString(_file.readBytes(), Base64.DEFAULT)
        try {
            lifecycleScope.launch {
                var _filename:String=""
                //val fileBody = RequestBody.create(MediaType.parse("image/jpeg"), _file)
                val fileBody = RequestBody.create(MediaType.parse("image/*"), _file)
                val _uerId = RequestBody.create(MediaType.parse("text/plain"), g.oUsu!!.id.toString())
                val _sectorId = RequestBody.create(MediaType.parse("text/plain"), 2.toString())
                var image = MultipartBody.Part.createFormData("files",_file.name,fileBody)

                var callFile: Call<FilePostResponse> = retrofit.uploadImage(image,g.oUsu!!.id ,2)
                callFile.enqueue(object : Callback<FilePostResponse> {
                    override fun onResponse(
                        call: Call<FilePostResponse>,
                        response: Response<FilePostResponse>,
                    ) {
                        if (response.isSuccessful) {
                            val post = response.body()
                            var _archivos : List<Archivo> = post!!.data
                            _filename = _archivos.first().ubicacion



                            var obj : StagePost = StagePost(0, g.oUsu!!.id, list[0],_filename)
                            val call: Call<StagePostResponse> = retrofit.saveStage(obj)
                            call.enqueue(object : Callback<StagePostResponse> {
                                override fun onResponse(call: Call<StagePostResponse>, response: Response<StagePostResponse>) {
                                    // copyToClip(response.toString())
                                    if (response.isSuccessful) {
                                        val post = response.body()
                                        // Handle the retrieved post data
                                        //goHome()


                                        loadingDialog!!.dismissDialog()
                                        if(post!!.success)
                                        {
                                            //var stages =  g.iStages.plus(post!!.data) as List<Etapa>
                                            g.iStages =  g.iStages.plus(post!!.data)
                                            g.etapaAdapter.updateList(g.iStages)
                                            g.showPopUp(this@MainActivity,"Etapas","Se guardo correctamente!")
                                            //Toast.makeText(this@MainActivity,"Se guardo correctamente!",Toast.LENGTH_SHORT).show();
                                            //startActivity(Intent(this@MainActivity, LoginActivity::class.java))
                                        }else{
                                            g.showPopUp(this@MainActivity,"Etapas","Por favor intentelo de nuevo!")
                                            //Toast.makeText(this@MainActivity,"Por favor intentelo de nuevo!",Toast.LENGTH_SHORT).show();
                                        }

                                        //                                                                                                                                                                                                                                                                         Toast.makeText(this@LoginActivity,"Menu imgExit: " + post,Toast.LENGTH_SHORT).show();
                                    } else {
                                        // Handle error
                                    }
                                }

                                override fun onFailure(call: Call<StagePostResponse>, t: Throwable) {
                                    // Handle failure
                                    loadingDialog!!.dismissDialog()
                                    g.showPopUp(this@MainActivity,"Etapas","Se produo un error!")
                                    //Toast.makeText(this@MainActivity,"Se produo un error!",Toast.LENGTH_SHORT).show();
                                    //Toast.makeText(this@LoginActivity,"Error onFailure",Toast.LENGTH_SHORT).show();
                                }

                            })





                        }
                    }

                    override fun onFailure(call: Call<FilePostResponse>, t: Throwable) {
                        loadingDialog!!.dismissDialog()
                        g.showPopUp(this@MainActivity,"Etapas - File","Se produo un error!")
                        //Toast.makeText(this@MainActivity,"Se produjo un error!",Toast.LENGTH_SHORT).show()
                    }



                })
                /*
                var obj : StagePost = StagePost(0, g.oUsu!!.id, list[0],_file64string)
                val call: Call<StagePostResponse> = retrofit.saveStage(obj)
                call.enqueue(object : Callback<StagePostResponse> {
                    override fun onResponse(call: Call<StagePostResponse>, response: Response<StagePostResponse>) {
                        // copyToClip(response.toString())
                        if (response.isSuccessful) {
                            val post = response.body()
                            // Handle the retrieved post data
                            //goHome()


                            loadingDialog!!.dismissDialog()
                            if(post!!.success)
                            {
                                var stages =  g.iStages.plus(post!!.data) as List<Etapa>
                                g.iStages =  g.iStages.plus(post!!.data)
                                g.etapaAdapter.updateList(g.iStages)
                                Toast.makeText(this@MainActivity,"Se guardo correctamente!",Toast.LENGTH_SHORT).show();
                                //startActivity(Intent(this@MainActivity, LoginActivity::class.java))
                            }else{
                                Toast.makeText(this@MainActivity,"Por favor intentelo de nuevo!",Toast.LENGTH_SHORT).show();
                            }

                            //                                                                                                                                                                                                                                                                         Toast.makeText(this@LoginActivity,"Menu imgExit: " + post,Toast.LENGTH_SHORT).show();
                        } else {
                            // Handle error
                        }
                    }

                    override fun onFailure(call: Call<StagePostResponse>, t: Throwable) {
                        // Handle failure
                        loadingDialog!!.dismissDialog()
                        Toast.makeText(this@MainActivity,"Verifique Usuario o Clave",Toast.LENGTH_SHORT).show();
                        //Toast.makeText(this@LoginActivity,"Error onFailure",Toast.LENGTH_SHORT).show();
                    }

                })
                */
            }
        }catch (ex: Exception)
        {loadingDialog!!.dismissDialog()}
        popup.dismiss()
        //Toast.makeText(this, "Guardar Etapas", Toast.LENGTH_SHORT).show()
    }
    private fun guardarGaleria(list : MutableList<String>, _file :File){
        loadingDialog!!.startLoadingDialog()
        try {
            lifecycleScope.launch {

                var _filename:String=""
                //val fileBody = RequestBody.create(MediaType.parse("image/jpeg"), _file)
                val fileBody = RequestBody.create(MediaType.parse("image/*"), _file)
                var image = MultipartBody.Part.createFormData("files",_file.name,fileBody)

                var callFile: Call<FilePostResponse> = retrofit.uploadImage(image,g.oUsu!!.id ,3)
                callFile.enqueue(object : Callback<FilePostResponse> {
                    override fun onResponse(
                        call: Call<FilePostResponse>,
                        response: Response<FilePostResponse>,
                    ) {
                        if (response.isSuccessful) {
                            val post = response.body()
                            var _archivos : List<Archivo> = post!!.data
                            _filename = _archivos.first().ubicacion



                            var _file64string : String = Base64.encodeToString(_file.readBytes(), Base64.DEFAULT)
                            var obj : GalleryPost = GalleryPost(0,g.oStage!!.id_etapa, list[0],
                                list[1],0.toString(),0.toString(), list[2],_filename)
                            val call: Call<GalleryPostResponse> = retrofit.saveGallery(obj)
                            call.enqueue(object : Callback<GalleryPostResponse> {
                                override fun onResponse(call: Call<GalleryPostResponse>, response: Response<GalleryPostResponse>) {
                                    // copyToClip(response.toString())
                                    if (response.isSuccessful) {
                                        val post = response.body()
                                        // Handle the retrieved post data
                                        //goHome()

                                        loadingDialog!!.dismissDialog()
                                        if(post!!.success)
                                        {
                                            g.iGalleries =  g.iGalleries.plus(post!!.data)
                                            g.galeryAdapter.updateList(g.iGalleries)
                                            //g.iStages.find { ev -> ev.id_etapa == }
                                            g.showPopUp(this@MainActivity,"Galeria","e guardo correctamente!")
                                            //Toast.makeText(this@MainActivity,"Se guardo correctamente!",Toast.LENGTH_SHORT).show();
                                            //startActivity(Intent(this@MainActivity, LoginActivity::class.java))
                                        }else{
                                            g.showPopUp(this@MainActivity,"Galeria","Por favor intentelo de nuevo!")
                                            //Toast.makeText(this@MainActivity,"Por favor intentelo de nuevo!",Toast.LENGTH_SHORT).show();
                                        }

                                        //                                                                                                                                                                                                                                                                         Toast.makeText(this@LoginActivity,"Menu imgExit: " + post,Toast.LENGTH_SHORT).show();
                                    } else {
                                        // Handle error
                                        loadingDialog!!.dismissDialog()
                                        g.showPopUp(this@MainActivity,"Galeria","Por favor intentelo de nuevo!")
                                        //Toast.makeText(this@MainActivity,"Por favor intentelo de nuevo!",Toast.LENGTH_SHORT).show();
                                    }
                                }

                                override fun onFailure(call: Call<GalleryPostResponse>, t: Throwable) {
                                    // Handle failure
                                    loadingDialog!!.dismissDialog()
                                    g.showPopUp(this@MainActivity,"Galeria","Se produjo un error!")
                                    //Toast.makeText(this@MainActivity,"Se produjo un error!",Toast.LENGTH_SHORT).show();
                                    //Toast.makeText(this@LoginActivity,"Error onFailure",Toast.LENGTH_SHORT).show();
                                }

                            })





                        }
                    }

                    override fun onFailure(call: Call<FilePostResponse>, t: Throwable) {
                        loadingDialog!!.dismissDialog()
                        g.showPopUp(this@MainActivity,"Galeria - File","Se produjo un error!")
                        //Toast.makeText(this@MainActivity,"Se produjo un error!!",Toast.LENGTH_SHORT).show()
                    }



                })


            }
        }catch (ex: Exception)
        {loadingDialog!!.dismissDialog()}
        popup.dismiss()
        //Toast.makeText(this, "Guardar Galeria", Toast.LENGTH_SHORT).show()
    }
    private fun guardarMultimedia(_file :File){
        loadingDialog!!.startLoadingDialog()
        try {
            lifecycleScope.launch {

                var _filename:String=""
                var _link:String=""
                //val fileBody = RequestBody.create(MediaType.parse("image/jpeg"), _file)
                val fileBody = RequestBody.create(MediaType.parse("image/*"), _file)
                var image = MultipartBody.Part.createFormData("files",_file.name,fileBody)

                var callFile: Call<FilePostResponse> = retrofit.uploadImage(image,g.oUsu!!.id ,4)
                callFile.enqueue(object : Callback<FilePostResponse> {
                    override fun onResponse(
                        call: Call<FilePostResponse>,
                        response: Response<FilePostResponse>,
                    ) {
                        if (response.isSuccessful) {
                            val post = response.body()
                            var _archivos : List<Archivo> = post!!.data
                            _filename = _archivos.first().nombre
                            _link = _archivos.first().ubicacion



                            //var _file64string : String = Base64.encodeToString(_file.readBytes(), Base64.DEFAULT)
                            var obj : MultimediaPost = MultimediaPost(0,g.oGallery!!.id,1, _filename, _link)
                            val call: Call<MultimediaPostResponse> = retrofit.saveMultimedia(obj)
                            call.enqueue(object : Callback<MultimediaPostResponse> {
                                override fun onResponse(call: Call<MultimediaPostResponse>, response: Response<MultimediaPostResponse>) {
                                    // copyToClip(response.toString())
                                    if (response.isSuccessful) {
                                        val post = response.body()
                                        // Handle the retrieved post data
                                        //goHome()

                                        loadingDialog!!.dismissDialog()
                                        if(post!!.success)
                                        {
                                            g.iMultimedias =  g.iMultimedias.plus(post!!.data)
                                            g.multimediaAdapter.updateList(g.iMultimedias)
                                            //g.iStages.find { ev -> ev.id_etapa == }
                                            g.showPopUp(this@MainActivity,"Multimedia","Se guardo correctamente!")
                                            //Toast.makeText(this@MainActivity,"Se guardo correctamente!",Toast.LENGTH_SHORT).show();
                                            //startActivity(Intent(this@MainActivity, LoginActivity::class.java))
                                        }else{
                                            g.showPopUp(this@MainActivity,"Multimedia","Por favor intentelo de nuevo!")
                                            //Toast.makeText(this@MainActivity,"Por favor intentelo de nuevo!",Toast.LENGTH_SHORT).show();
                                        }

                                        //                                                                                                                                                                                                                                                                         Toast.makeText(this@LoginActivity,"Menu imgExit: " + post,Toast.LENGTH_SHORT).show();
                                    } else {
                                        // Handle error
                                        loadingDialog!!.dismissDialog()
                                        g.showPopUp(this@MainActivity,"Multimedia","Por favor intentelo de nuevo!")
                                        //Toast.makeText(this@MainActivity,"Por favor intentelo de nuevo!",Toast.LENGTH_SHORT).show();
                                    }
                                }

                                override fun onFailure(call: Call<MultimediaPostResponse>, t: Throwable) {
                                    // Handle failure
                                    loadingDialog!!.dismissDialog()
                                    g.showPopUp(this@MainActivity,"Multimedia","Se produjo un error!")
                                    //Toast.makeText(this@MainActivity,"Se produjo un error!",Toast.LENGTH_SHORT).show();
                                    //Toast.makeText(this@LoginActivity,"Error onFailure",Toast.LENGTH_SHORT).show();
                                }

                            })


                        }
                    }

                    override fun onFailure(call: Call<FilePostResponse>, t: Throwable) {
                        loadingDialog!!.dismissDialog()
                        g.showPopUp(this@MainActivity,"Multimedia - File","Se produjo un error!")
                        //Toast.makeText(this@MainActivity,"Se produjo un error!!",Toast.LENGTH_SHORT).show()
                    }



                })


            }
        }catch (ex: Exception)
        {loadingDialog!!.dismissDialog()}

    }
    private fun guardarFamilia(obj : FamilyPost){
        loadingDialog!!.startLoadingDialog()
        try {
            lifecycleScope.launch {

                var _filename:String=""
                var _link:String=""
                val fileBody = RequestBody.create(MediaType.parse("image/*"), file)
                var image = MultipartBody.Part.createFormData("files",file.name,fileBody)


                var callFile: Call<FilePostResponse> = retrofit.uploadImage(image,g.oUsu!!.id ,4)
                callFile.enqueue(object : Callback<FilePostResponse> {
                    override fun onResponse(
                        call: Call<FilePostResponse>,
                        response: Response<FilePostResponse>,
                    ) {
                        if (response.isSuccessful) {
                            val post = response.body()
                            var _archivos : List<Archivo> = post!!.data
                            _filename = _archivos.first().nombre
                            _link = _archivos.first().ubicacion

                            obj.photo = _link
                            val call: Call<FamilyPostResponse> = retrofit.saveFamily(obj)
                            call.enqueue(object : Callback<FamilyPostResponse> {
                                override fun onResponse(call: Call<FamilyPostResponse>, response: Response<FamilyPostResponse>) {
                                    // copyToClip(response.toString())
                                    if (response.isSuccessful) {
                                        val post = response.body()
                                        // Handle the retrieved post data
                                        //goHome()

                                        loadingDialog!!.dismissDialog()
                                        if(post!!.success)
                                        {
                                            g.iFamily =  g.iFamily.plus(post!!.data)
                                            g.familyAdapter.updateList(g.iFamily)
                                            g.showPopUp(this@MainActivity,"Familia","Se guardo correctamente!")
                                            //Toast.makeText(this@MainActivity,"Se guardo correctamente!",Toast.LENGTH_SHORT).show();
                                            //startActivity(Intent(this@MainActivity, LoginActivity::class.java))
                                        }else{
                                            g.showPopUp(this@MainActivity,"Familia","Por favor intentelo de nuevo!")
                                            //Toast.makeText(this@MainActivity,"Por favor intentelo de nuevo!",Toast.LENGTH_SHORT).show();
                                        }

                                        //                                                                                                                                                                                                                                                                         Toast.makeText(this@LoginActivity,"Menu imgExit: " + post,Toast.LENGTH_SHORT).show();
                                    } else {
                                        // Handle error
                                    }
                                }

                                override fun onFailure(call: Call<FamilyPostResponse>, t: Throwable) {
                                    // Handle failure
                                    loadingDialog!!.dismissDialog()
                                    g.showPopUp(this@MainActivity,"Familia","Por favor intentelo de nuevo!")
                                    //Toast.makeText(this@MainActivity,"Verifique Usuario o Clave",Toast.LENGTH_SHORT).show();
                                    //Toast.makeText(this@LoginActivity,"Error onFailure",Toast.LENGTH_SHORT).show();
                                }

                            })



                        }
                    }

                    override fun onFailure(call: Call<FilePostResponse>, t: Throwable) {
                        loadingDialog!!.dismissDialog()
                        g.showPopUp(this@MainActivity,"Familia - File","Se produjo un error!")
                        //Toast.makeText(this@MainActivity,"Se produjo un error!!",Toast.LENGTH_SHORT).show()
                    }



                })



            }
        }catch (ex: Exception)
        {}
        popup.dismiss()
    }
    private fun validarFamily(b: Dialog): Boolean{
        var isValid : Boolean = true

        val _parent =  b.findViewById<AutoCompleteTextView>(R.id.sp_parent)
        val _apellido =  b.findViewById<EditText>(R.id.et_g_galeria)
        val _nombres =  b.findViewById<EditText>(R.id.et_g_etapa)
        val _lyparent =  b.findViewById<TextInputLayout>(R.id.ly_g_etapa_cbo)
        val _lyapellido =  b.findViewById<TextInputLayout>(R.id.ly_g_galeria)
        val _lynombres =  b.findViewById<TextInputLayout>(R.id.ly_g_etapa)

        if (_parent.text.toString().trim().isBlank())
        {
            isValid=false
            _lyparent.error = "* Campo Requerido"
        }else{_lyparent.error = null}

        if (_apellido.text.toString().trim().isBlank())
        {
            isValid=false
            _lyapellido.error = "* Campo Requerido"
        }else{_lyapellido.error = null}

        if (_nombres.text.toString().trim().isBlank())
        {
            isValid=false
            _lynombres.error = "* Campo Requerido"
        }else{_lynombres.error = null}

        return isValid
    }
    val takeFoto = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
        //val bitmap = it.data?.extras?.get("data") as Bitmap
            result : ActivityResult ->
        //Toast.makeText(this, "RESULT ${result.resultCode}", Toast.LENGTH_SHORT).show()

        //_file64string = Base64.encodeToString(file.readBytes(), Base64.NO_WRAP)
        _file64string = Base64.encodeToString(file.readBytes(), Base64.DEFAULT)
        //Log.d("b64", _file64string)
        //copyToClip(_file64string)
        var angle:Float = 0F
        try{
            if(result.resultCode == Activity.RESULT_OK){

                val exif = ExifInterface(file.path)
                val orientation: Int = exif.getAttributeInt(
                    ExifInterface.TAG_ORIENTATION,
                    ExifInterface.ORIENTATION_NORMAL
                )



                if (orientation == ExifInterface.ORIENTATION_ROTATE_90) {
                    angle = 90F
                } else if (orientation == ExifInterface.ORIENTATION_ROTATE_180) {
                    angle = 180F
                } else if (orientation == ExifInterface.ORIENTATION_ROTATE_270) {
                    angle = 270F
                }


                 //Toast.makeText(this, "angulo  ${angle}", Toast.LENGTH_SHORT).show()
                // Toast.makeText(this, "Activity.RESULT_OK ", Toast.LENGTH_SHORT).show()
                // val f = File(SD_CARD_IMAGE_PATH)



                //val bmp = BitmapFactory.decodeStream(FileInputStream(file), null, null)
                //val correctBmp = Bitmap.createBitmap(bmp!!, 0, 0, bmp.width, bmp.height, mat, true)


                //val intent = result.data
                //val imageBitmap = intent?.extras?.get("data") as Bitmap

                //seteamos el target
                //img_foto_bg
                //drawable = applicationContext.resources.getDrawable(R.drawable.image)
                //bitmap = (drawable as BitmapDrawable).bitmap
                //val d = BitmapDrawable(bitmap)
                //val mIcon: Bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.icon_resource)
                val imageBitmap : Bitmap
                if (::file.isInitialized) {
                    imageBitmap = BitmapFactory.decodeFile(file.toString())
                }
                else {
                    imageBitmap = result.data?.extras?.get("data") as Bitmap
                }

                val matrix = Matrix()
                matrix.postRotate(angle.toFloat())

                val imageBitmapNew = Bitmap.createBitmap(
                    imageBitmap,
                    0,
                    0,
                    imageBitmap.width,
                    imageBitmap.height,
                    matrix,
                    true
                )

                //popup.findViewById<ImageView>(R.id.img_foto).setImageBitmap(imageBitmapNew)
               // val imageBitmap = BitmapFactory.decodeFile(file.toString())
                //val background: BitmapDrawable
                //background = BitmapDrawable(BitmapFactory.decodeResource(resources, R.drawable.back))
                val _img:Drawable = BitmapDrawable(imageBitmap)
                popup.findViewById<ConstraintLayout>(R.id.img_foto_bg).background = _img

                //Toast.makeText(this, "imagen seteada", Toast.LENGTH_SHORT).show()


            }
        }catch (ex:Exception){
            val clipboard = this.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            val clip: ClipData = ClipData.newPlainText("simple text", ex.message.toString() + " angulo: ${angle} file: ${file}")
            clipboard.setPrimaryClip(clip)
            Toast.makeText(this, "Exception ${ex.message}", Toast.LENGTH_SHORT).show()
        }

    }
    val takePhoto = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
        //val bitmap = it.data?.extras?.get("data") as Bitmap
            result : ActivityResult ->
        //Toast.makeText(this, "RESULT ${result.resultCode}", Toast.LENGTH_SHORT).show()

        //_file64string = Base64.encodeToString(file.readBytes(), Base64.NO_WRAP)
        _file64string = Base64.encodeToString(file.readBytes(), Base64.DEFAULT)

        var angle = 0
        try{
            if(result.resultCode == Activity.RESULT_OK){

                val exif = ExifInterface(file.path)
                val orientation: Int = exif.getAttributeInt(
                    ExifInterface.TAG_ORIENTATION,
                    ExifInterface.ORIENTATION_NORMAL
                )



                if (orientation == ExifInterface.ORIENTATION_ROTATE_90) {
                    angle = 90
                } else if (orientation == ExifInterface.ORIENTATION_ROTATE_180) {
                    angle = 180
                } else if (orientation == ExifInterface.ORIENTATION_ROTATE_270) {
                    angle = 270
                }


                val imageBitmap : Bitmap
                if (::file.isInitialized) {
                    imageBitmap = BitmapFactory.decodeFile(file.toString())
                }
                else {
                    imageBitmap = result.data?.extras?.get("data") as Bitmap
                }

                //val _img:Drawable = BitmapDrawable(imageBitmap)
                //popup.findViewById<ConstraintLayout>(R.id.img_foto_bg).background = _img
                //Toast.makeText(this, "imagen seteada", Toast.LENGTH_SHORT).show()

                //guardar Foto multimedia
                var list: MutableList<String> = mutableListOf()
                //_b.toMutableList().add()
                list.add("")
                guardarMultimedia(file)


            }
        }catch (ex:Exception){
            val clipboard = this.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            val clip: ClipData = ClipData.newPlainText("simple text", ex.message.toString() + " angulo: ${angle} file: ${file}")
            clipboard.setPrimaryClip(clip)
            Toast.makeText(this, "Exception ${ex.message}", Toast.LENGTH_SHORT).show()
        }

    }
    private fun showResultPopUp(bm : Bitmap){
        //g.iStages = emptyList()
        popup.setContentView(R.layout.popup_add_photo)
        val btnCerrar = popup.findViewById<ImageView>(R.id.img_close)
        val btnPhoto = popup.findViewById<ConstraintLayout>(R.id.img_foto_bg)
        val btnAceptar = popup.findViewById<Button>(R.id.btnAceptar)

        val cboEtapa = popup.findViewById<AutoCompleteTextView>(R.id.sp_g_etapa)
        val cboGaleria = popup.findViewById<AutoCompleteTextView>(R.id.sp_g_galeria)
        val txtEtapa = popup.findViewById<TextView>(R.id.et_g_etapa)
        val txtGaleria = popup.findViewById<TextView>(R.id.et_g_galeria)

        val txt = popup.findViewById<TextView>(R.id.txt_pop_g_text)
        //val img = popup.findViewById<ImageView>(R.id.img_g_bg)
        //val _img:Drawable = BitmapDrawable(imageBitmapNew)
        popup.findViewById<ImageView>(R.id.img_g_bg).setImageBitmap(bm)
        //txt.text = getString(R.string.mensaje_g_primera_vez)
        var titulo = "Como no tiene ni <b>Etapas</b>,ni <b>Galerías</b>, las mismas se crearán automaticamente, por favor ingrese un nombre para cada sección"
        //titulo ="Por favor eleccione la Etapa y Galeria para asociar la foto correctaente."
        //txt.text = Html.fromHtml(getString(R.string.mensaje_g_primera_vez))

        popup.findViewById<TextInputLayout>(R.id.ly_g_etapa_cbo).visibility = View.VISIBLE
        popup.findViewById<TextInputLayout>(R.id.ly_g_galeria_cbo).visibility = View.VISIBLE

       /* popup.findViewById<TextInputLayout>(R.id.ly_g_etapa_cbo).visibility = View.GONE
        popup.findViewById<TextInputLayout>(R.id.ly_g_galeria_cbo).visibility = View.GONE

        popup.findViewById<TextInputLayout>(R.id.ly_g_etapa).visibility = View.VISIBLE
        popup.findViewById<TextInputLayout>(R.id.ly_g_galeria).visibility = View.VISIBLE*/
        //cboGaleria.visibility = View.VISIBLE
        //cboGaleria.visibility = View.VISIBLE
        //val spTipoMov = v.findViewById<View>(R.id.spTipoMov) as Spinner
        val etapas : List<String>? = g.iStages.map { et: Etapa -> et.etapa.toString().toUpperCase() }
        try {
            val adapter = ArrayAdapter(this,
                android.R.layout.simple_spinner_dropdown_item, etapas!!)
            cboEtapa.setAdapter(adapter)
            titulo ="Por favor seleccione la <b>Etapa</b> y <b>Galería</b> para asociar la foto correctamente."
        }catch (Ex : Exception){
            //cboEtapa.visibility = View.GONE
            popup.findViewById<TextInputLayout>(R.id.ly_g_galeria_cbo).visibility = View.GONE
            popup.findViewById<TextInputLayout>(R.id.ly_g_etapa).visibility = View.VISIBLE
            popup.findViewById<TextInputLayout>(R.id.ly_g_galeria_cbo).visibility = View.GONE
            popup.findViewById<TextInputLayout>(R.id.ly_g_galeria).visibility = View.VISIBLE
        }
        txt.text = Html.fromHtml(titulo)


        cboEtapa.setOnItemClickListener { adapterView, view, i, l ->
            val item : String = adapterView.getItemAtPosition(i).toString()
            //Toast.makeText(this@MainActivity,item,Toast.LENGTH_SHORT).show()
            //cboGaleria.visibility = View.VISIBLE
            popup.findViewById<TextInputLayout>(R.id.ly_g_galeria_cbo).visibility = View.VISIBLE
            popup.findViewById<TextInputLayout>(R.id.ly_g_etapa).visibility = View.GONE
            try {
                //val oEtapa : Etapa? = g.iStages.find { et: Etapa -> et.etapa.toUpperCase() == item }
                oEtapa = g.iStages.find { et: Etapa -> et.etapa.toUpperCase() == item }
                var galerias : List<String>? = null
                if (oEtapa != null) galerias = oEtapa!!.iGallery!!.map { gly: Galeria -> gly.name.toString().toUpperCase() }

                val adapter = ArrayAdapter(this,
                    android.R.layout.simple_spinner_dropdown_item, galerias!!)
                cboGaleria.setAdapter(adapter)
            }catch (Ex : Exception){
                cboGaleria.setAdapter(null)
                //cboGaleria.visibility = View.GONE
                popup.findViewById<TextInputLayout>(R.id.ly_g_galeria_cbo).visibility = View.GONE
                popup.findViewById<TextInputLayout>(R.id.ly_g_galeria).visibility = View.VISIBLE
            }

        }
        cboGaleria.setOnItemClickListener { adapterView, view, i, l ->
            val item : String = adapterView.getItemAtPosition(i).toString()
            oGaleria = oEtapa!!.iGallery!!.find { gly: Galeria -> gly.name.toString().toUpperCase() == item }

        }

        if(!isFinishing()){
            popup.getWindow()!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            popup.show()
        }



        btnPhoto.setOnClickListener {
            /* val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE).also{
                 it.resolveActivity(packageManager).also{ component ->
                     val photoFile = createPhotoFile()
                     val photoUri : Uri =
                         FileProvider.getUriForFile(
                             this,
                             BuildConfig.APPLICATION_ID + ".fileprovider",
                             photoFile
                         )

                     it.putExtra(MediaStore.EXTRA_OUTPUT, photoUri)
                     Toast.makeText(this, "resolveActivity ", Toast.LENGTH_SHORT).show()
                 }
             }
             takePhotoAdd.launch(intent)*/

        }
        btnAceptar.setOnClickListener {
            var idstage: Int = if(oEtapa != null) oEtapa!!.id_etapa else 0
            var idgalery: Int = if(oGaleria != null) oGaleria!!.id else 0
            var obj : HomeFull = HomeFull(0,g.oUsu!!.id,idstage,idgalery,txtEtapa.text.toString(),txtGaleria.text.toString(),"","","")
            guardarTodo(obj)
            popup.dismiss()
        }
        btnCerrar.setOnClickListener {
            popup.dismiss()
        }
    }
    private fun guardarTodo(obj : HomeFull){
        loadingDialog!!.startLoadingDialog()
        try {
            lifecycleScope.launch {

                var _filename:String=""
                var _link:String=""
                val fileBody = RequestBody.create(MediaType.parse("image/*"), file)
                var image = MultipartBody.Part.createFormData("files",file.name,fileBody)


                var callFile: Call<FilePostResponse> = retrofit.uploadImageAll(image,g.oUsu!!.id ,10,0,0)
                callFile.enqueue(object : Callback<FilePostResponse> {
                    override fun onResponse(
                        call: Call<FilePostResponse>,
                        response: Response<FilePostResponse>,
                    ) {
                        if (response.isSuccessful) {
                            val post = response.body()
                            var _archivos : List<Archivo> = post!!.data
                            _filename = _archivos.first().nombre
                            _link = _archivos.first().ubicacion

                            obj.link = _link
                            val call: Call<HomePostResponse> = retrofit.saveHomeFull(obj)
                            call.enqueue(object : Callback<HomePostResponse> {
                                override fun onResponse(call: Call<HomePostResponse>, response: Response<HomePostResponse>) {
                                    // copyToClip(response.toString())
                                    if (response.isSuccessful) {
                                        val post = response.body()
                                        // Handle the retrieved post data
                                        //goHome()

                                        loadingDialog!!.dismissDialog()
                                        if(post!!.success)
                                        {
                                            //g.iFamily =  g.iFamily.plus(post!!.data)
                                            //g.familyAdapter.updateList(g.iFamily)
                                            g.showPopUp(this@MainActivity,"Home","Se guardo correctamente!")
                                            //Toast.makeText(this@MainActivity,"Se guardo correctamente!",Toast.LENGTH_SHORT).show();
                                            //startActivity(Intent(this@MainActivity, LoginActivity::class.java))
                                        }else{
                                            g.showPopUp(this@MainActivity,"Home","Por favor intentelo de nuevo!")
                                            //Toast.makeText(this@MainActivity,"Por favor intentelo de nuevo!",Toast.LENGTH_SHORT).show();
                                        }

                                        //                                                                                                                                                                                                                                                                         Toast.makeText(this@LoginActivity,"Menu imgExit: " + post,Toast.LENGTH_SHORT).show();
                                    } else {
                                        // Handle error
                                    }
                                }

                                override fun onFailure(call: Call<HomePostResponse>, t: Throwable) {
                                    // Handle failure
                                    loadingDialog!!.dismissDialog()
                                    g.showPopUp(this@MainActivity,"Home","Por favor intentelo de nuevo!")
                                    //Toast.makeText(this@MainActivity,"Verifique Usuario o Clave",Toast.LENGTH_SHORT).show();
                                    //Toast.makeText(this@LoginActivity,"Error onFailure",Toast.LENGTH_SHORT).show();
                                }

                            })



                        }
                    }

                    override fun onFailure(call: Call<FilePostResponse>, t: Throwable) {
                        loadingDialog!!.dismissDialog()
                        g.showPopUp(this@MainActivity,"Home - File","Se produjo un error!")
                        //Toast.makeText(this@MainActivity,"Se produjo un error!!",Toast.LENGTH_SHORT).show()
                    }



                })



            }
        }catch (ex: Exception)
        {}
        popup.dismiss()
    }
    val takePhotoAdd = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
            result : ActivityResult ->
        //val bitmap = result.data?.extras?.get("data") as Bitmap
        //Toast.makeText(this, "RESULT ${result.resultCode}", Toast.LENGTH_SHORT).show()

        //_file64string = Base64.encodeToString(file.readBytes(), Base64.NO_WRAP)
        //_file64string = Base64.encodeToString(file.readBytes(), Base64.DEFAULT)
        var f = _fileName
        var angle = 0
        try{
            if(result.resultCode == Activity.RESULT_OK){

                try {
                    if (::file.isInitialized) {
                        val ff=file
                        val exif = ExifInterface(file)
                        val orientation: Int = exif.getAttributeInt(
                            ExifInterface.TAG_ORIENTATION,
                            ExifInterface.ORIENTATION_NORMAL
                        )
                        if (orientation == ExifInterface.ORIENTATION_ROTATE_90) {
                            angle = 90
                        } else if (orientation == ExifInterface.ORIENTATION_ROTATE_180) {
                            angle = 180
                        } else if (orientation == ExifInterface.ORIENTATION_ROTATE_270) {
                            angle = 270
                        }
                    }

                }catch (ex: IOException){
                    Toast.makeText(this, "Exception ${ex.message}", Toast.LENGTH_SHORT).show()
                }catch (ex: Exception){
                    Toast.makeText(this, "Exception ${ex.message}", Toast.LENGTH_SHORT).show()
                }







                val imageBitmap : Bitmap
                if (::file.isInitialized) {
                    imageBitmap = BitmapFactory.decodeFile(file.toString())
                }
                else {
                    imageBitmap = result.data?.extras?.get("data") as Bitmap
                }


                showResultPopUp(imageBitmap)



            }
        }catch (ex:Exception){
            val clipboard = this.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            val clip: ClipData = ClipData.newPlainText("simple text", ex.message.toString() + " angulo: ${angle} file: ${file}")
            clipboard.setPrimaryClip(clip)
            Toast.makeText(this, "Exception ${ex.message}", Toast.LENGTH_SHORT).show()
        }

    }
   /* @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable  Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // check condition
        if (requestCode==100 && resultCode==RESULT_OK && data!=null)
        {
            // when result is ok
            // initialize uri
            Uri uri=data.getData();
            // Initialize bitmap
            try {
                Bitmap bitmap= MediaStore.Images.Media.getBitmap(getContentResolver(),uri);
                // initialize byte stream
                ByteArrayOutputStream stream=new ByteArrayOutputStream();
                // compress Bitmap
                bitmap.compress(Bitmap.CompressFormat.JPEG,100,stream);
                // Initialize byte array
                byte[] bytes=stream.toByteArray();
                // get base64 encoded string
                sImage= Base64.encodeToString(bytes,Base64.DEFAULT);
                // set encoded text on textview
                textView.setText(sImage);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }*/
    val getAction = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
        //val bitmap = it.data?.extras?.get("data") as Bitmap
        result : ActivityResult ->
        if(result.resultCode == Activity.RESULT_OK){
            val intent = result.data
            val imageBitmap = intent?.extras?.get("data") as Bitmap
            //seteamos el target
        }
    }
     fun takePhoto(){
         val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)

         if(intent.resolveActivity(packageManager) != null){
            getAction
         }
     }
/*  override fun onContextItemSelected(item: MenuItem): Boolean {
  val id: Int = item.itemId
  if(id == R.id. )
  return super.onContextItemSelected(item)
}
overrideun onOptionsItemSelected(item: MenuItem): Boolean {
  val id: Int = item.getItemId()
  if (id == R.id.mnu_new_photo) {
      val intent = Intent(this, ExampleActivity::class.java)
      intent.putExtra(BUNDLE_KEY, mConnection)
      startActivityForResult(intent, PICK_CHANGE_REQUEST)
      return true
  } else if (id == R.id.delete) {
      showDialog(this)
      return true
  }
  return super.onOptionsItemSelected(item)
}*/

    fun copyToClip(_text : String){
        val clipboard = this.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clip: ClipData = ClipData.newPlainText("simple text", _text)
        clipboard.setPrimaryClip(clip)
    }
    private fun hideOption(id: Int) {
        this._menutoolbar.findItem(id).setVisible(false)
    }

    private fun guardarFoto() {
        var fos: OutputStream? = null
        var file: File? = null
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            val resolver = contentResolver
            val values = ContentValues()
            val fileName = System.currentTimeMillis().toString() + "image_example"
            values.put(MediaStore.Images.Media.DISPLAY_NAME, fileName)
            values.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg")
            values.put(MediaStore.Images.Media.RELATIVE_PATH, "Pictures/Family")
            values.put(MediaStore.Images.Media.IS_PENDING, 1)
            var imageUridelete: Uri? = null
            try {
                val collection =
                    MediaStore.Images.Media.getContentUri(MediaStore.VOLUME_EXTERNAL_PRIMARY)
                imageUridelete = resolver.insert(collection, values)
            } catch (e: java.lang.Exception) {
                e.printStackTrace()
            }
            try {
                fos = resolver.openOutputStream(imageUridelete!!)
            } catch (e: FileNotFoundException) {
                e.printStackTrace()
            }
            values.clear()
            values.put(MediaStore.Images.Media.IS_PENDING, 0)
            resolver.update(imageUridelete!!, values, null, null)
        } else {
            //sentencias para las apis mas antiguas
            var imageDir =
                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
                    .toString()
            imageDir =
                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
                    .toString()
            val fileName = System.currentTimeMillis().toString() + ".jpg"
            file = File(imageDir, fileName)
            try {
                fos = FileOutputStream(file)
            } catch (e: FileNotFoundException) {
                e.printStackTrace()
            }
        }
        val saved: Boolean = thumbnail!!.compress(Bitmap.CompressFormat.JPEG, 100, fos!!)
        if (saved) {
            //btnGuardarFoto.setEnabled(false)
           // limpiar()
            thumbnail = null
           // imgPic.setImageBitmap(thumbnail)
            //Toast.makeText(this,"La Imagen se guardo Correctamente!",Toast.LENGTH_SHORT).show();
            val toast =
                Toast.makeText(this, "La Imagen se guardo Correctamente!", Toast.LENGTH_LONG)
            toast.setGravity(Gravity.CENTER, 0, 0)
            toast.show()
        }
        if (fos != null) {
            try {
                fos.flush()
                fos.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
        if (file != null) {
            MediaScannerConnection.scanFile(this, arrayOf(file.toString()), null, null)
        }
    }

    fun sendFragment(fragment: Fragment?) {
        showSelectedFragment(fragment)
    }
    private fun showSelectedFragment(fragment: Fragment?): Boolean {
        if (fragment != null) {
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.nav_host_fragment_activity_main, fragment)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .commit()
            return true
        }
        return false
    }
private fun salirAPP() {
    val builder = AlertDialog.Builder(this)
    builder.setMessage("¿Estás seguro que querés cerrar la app?")
        .setPositiveButton("CONFIRMAR") { dialog, which ->
            finish()
            System.exit(0)
        }
        .setNegativeButton(
            "CANCELAR"
        ) { dialog, which -> dialog.dismiss() }
    builder.show()
}
override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
    if (keyCode == KeyEvent.KEYCODE_BACK) {
        salirAPP()
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

