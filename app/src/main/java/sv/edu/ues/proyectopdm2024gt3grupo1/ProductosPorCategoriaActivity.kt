package sv.edu.ues.proyectopdm2024gt3grupo1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import sv.edu.ues.proyectopdm2024gt3grupo1.data.DataHelper

class ProductosPorCategoriaActivity : AppCompatActivity() {

    var categoria: String = ""
    private lateinit var dbHelper: DataHelper
    private val imagenRecuperado = ArrayList<String>()
    private val nombreRecuperado = ArrayList<String>()
    private val precioRecuperado = ArrayList<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_productos_por_categoria)

        //toolbar
        val toolbar: Toolbar = findViewById(R.id.toolbarProdPorCategorias)
        setSupportActionBar(toolbar)
        getSupportActionBar()?.setDisplayHomeAsUpEnabled(true)
        getSupportActionBar()?.setDisplayShowHomeEnabled(true)

        dbHelper = DataHelper(this)

        //obtener el nombre de la categoria
         categoria = intent.getStringExtra("nombreCategoria")?: ""
        LlenarDatosEnVista(categoria)

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        //menuInflater.inflate(R.menu.menu, menu)
        supportActionBar?.title = categoria
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){

            android.R.id.home->finish()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun LlenarDatosEnVista(categoria: String) {
        val ListaProductos = dbHelper.ProductosPorCategoria(categoria)

        imagenRecuperado.clear()
        nombreRecuperado.clear()
        precioRecuperado.clear()

        for (prod in ListaProductos) {
            //Accede a los atributos o metodos de cada objeto productos
            imagenRecuperado.add(prod.imagen1)
            nombreRecuperado.add(prod.nombre)
            precioRecuperado.add("$ "+ prod.precioVenta.toString())
        }

        var recyclerView: RecyclerView = findViewById(R.id.RecycleProdPorCategoria)
        val adapter = CustomAdapterHome(imagenRecuperado, nombreRecuperado, precioRecuperado)
        recyclerView.layoutManager = GridLayoutManager(this,2)
        recyclerView.adapter = adapter

    }
}