package sv.edu.ues.proyectopdm2024gt3grupo1

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.view.Menu
import android.view.MenuItem
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.ImageView
import android.widget.Spinner
import android.widget.Toast
//import android.widget.Toolbar
import sv.edu.ues.proyectopdm2024gt3grupo1.data.DataHelper
import java.io.File
import java.io.InputStream
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import androidx.appcompat.widget.Toolbar

class InsertarProductoActivity : AppCompatActivity() {

    private lateinit var dbHelper: DataHelper

    private lateinit var SpinerCat: Spinner
    private lateinit var cat: String
    private lateinit var res: String

    //imagen
    private lateinit var imageView: ImageView
    private val PICK_IMAGE_REQUEST = 1
    private var imageUrl: String? = null
    private lateinit var outputFile: File

    //Controles del formulario
    private lateinit var txtId: EditText
    private lateinit var txtNombre: EditText
    private lateinit var txtCategoria: Spinner
    private lateinit var txtCantidadS: EditText
    private lateinit var txtCantidadM: EditText
    private lateinit var txtCantidadL: EditText
    private lateinit var txtPrecioCompra: EditText
    private lateinit var txtPrecioVenta: EditText
    private lateinit var txtDescripcion: EditText


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_insertar_producto)

        //menu
        //toolbar
        val toolbar: Toolbar = findViewById(R.id.toolbarInsert)
        setSupportActionBar(toolbar)
        getSupportActionBar()?.setDisplayHomeAsUpEnabled(true)
        getSupportActionBar()?.setDisplayShowHomeEnabled(true)

        dbHelper = DataHelper(this)
        SpinerCat = findViewById(R.id.txtSpinerCategorias)

        llenarSpinner()

        //imagen
        imageView = findViewById(R.id.image1)
        imageView.setOnClickListener() {
            selectImage(it)
        }
        cat = findViewById<Spinner?>(R.id.txtSpinerCategorias).selectedItem.toString()


    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.guardar -> {
                GuardarEnBD()

            }
            android.R.id.home->{
                val intent = Intent(this, InventarioActivity::class.java)
                startActivity(intent)}
        //finish()
        }
        return super.onOptionsItemSelected(item)
    }


    //imagen
    fun selectImage(view: android.view.View) {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(intent, PICK_IMAGE_REQUEST)
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK && data != null && data.data != null) {
            val uri: Uri = data.data!!

            // Generar un nombre de archivo único para la imagen
            val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
            val fileName = "image_$timeStamp.jpg"

            // Copiar la imagen al almacenamiento externo con el nombre único
            val inputStream: InputStream? = contentResolver.openInputStream(uri)
            outputFile = File(getExternalFilesDir(Environment.DIRECTORY_PICTURES), fileName)
            outputFile.outputStream().use { outputStream ->
                inputStream?.copyTo(outputStream)
            }

            // Guardar la ruta del archivo en las preferencias compartidas o en la base de datos
            val sharedPref = getSharedPreferences("MyAppPrefs", Context.MODE_PRIVATE)
            val editor = sharedPref.edit()
            editor.putString("imagePath", outputFile.absolutePath)
            editor.apply()

            // Establecer la imagen en el ImageView
            imageView.setImageURI(uri)
        }
    }


    fun GuardarEnBD(){
        txtId= findViewById(R.id.txtInsertId)
        txtNombre= findViewById(R.id.txtInsertNombre)
        //txtCategoria= findViewById(R.id.txtSpinerCategorias)
        //var txtCategoria = res.toInt()
        txtCantidadS= findViewById(R.id.txtInsertCantidadS)
        txtCantidadM= findViewById(R.id.txtInsertCantidadM)
        txtCantidadL= findViewById(R.id.txtInsertCantidadL)
        txtPrecioCompra= findViewById(R.id.txtInsertPrecioCompra)
        txtPrecioVenta= findViewById(R.id.txtInsertPrecioVenta)
        txtDescripcion= findViewById(R.id.txtInsertDescripcion)

        cat = findViewById<Spinner?>(R.id.txtSpinerCategorias).selectedItem.toString()
        res = dbHelper.ConsultarIdCategoria(cat).toString()

        val idResultado = dbHelper.AddProducto(txtId.text.toString().toInt(), txtNombre.text.toString(), res, txtCantidadS.text.toString().toInt(), txtCantidadM.text.toString().toInt(), txtCantidadL.text.toString().toInt(), txtPrecioCompra.text.toString().toDouble(), txtPrecioVenta.text.toString().toDouble(), txtDescripcion.text.toString(), outputFile.toString())
        if(idResultado==-1.toLong()){
            Toast.makeText(this, "Hubo un error", Toast.LENGTH_SHORT).show()
        }else{
            Toast.makeText(this, "Producto guardado!!", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, InventarioActivity::class.java)
            startActivity(intent)
        }
    }


    //FUNCION PARA LLENAR EL SPINNER CON LAS CATEGORIAS
    private fun llenarSpinner() {
        val nombreCategoria: ArrayList<Categorias> = dbHelper.verCategorias()
        val nombre: ArrayList<String> = ArrayList()
            for (categ in nombreCategoria) {
                nombre.add(categ.nombre)
            }
            val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, nombreCategoria)
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            SpinerCat.adapter = adapter

    }
}