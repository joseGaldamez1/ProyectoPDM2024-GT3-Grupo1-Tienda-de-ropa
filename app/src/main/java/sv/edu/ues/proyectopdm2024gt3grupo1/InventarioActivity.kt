package sv.edu.ues.proyectopdm2024gt3grupo1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView
import sv.edu.ues.proyectopdm2024gt3grupo1.data.DataHelper

class InventarioActivity : AppCompatActivity() {

    private lateinit var dbHelper: DataHelper


    private lateinit var IdRecuperado: ArrayList<Int>
    private lateinit var NombreRecuperado: ArrayList<String>
    private lateinit var CategoriaRecuperado: ArrayList<String>
    private lateinit var CantidadRecuperado: ArrayList<Int>
    private lateinit var PrecioVentaRecuperado: ArrayList<Double>
    private lateinit var ImagenRecuperado1: ArrayList<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_inventario)

        //toolbar
        val toolbar: Toolbar = findViewById(R.id.toolbarModificar)
        setSupportActionBar(toolbar)
        getSupportActionBar()?.setDisplayHomeAsUpEnabled(true)
        getSupportActionBar()?.setDisplayShowHomeEnabled(true)


        dbHelper = DataHelper(this)
        IdRecuperado = ArrayList()
        NombreRecuperado = ArrayList()
        CategoriaRecuperado = ArrayList()
        CantidadRecuperado = ArrayList()
        PrecioVentaRecuperado = ArrayList()
        ImagenRecuperado1 = ArrayList()
        LlenarDatosEnVista()



        val abrir: ImageButton = findViewById(R.id.BotonAgregar)

        abrir.setOnClickListener(){
            val intent = Intent(this, InsertarProductoActivity::class.java)
            startActivity(intent)
        }


    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_inventario, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.editar -> {
                val intent = Intent(this, ModificarProductoActivity::class.java)
                startActivity(intent)
            }
            android.R.id.home->finish()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun LlenarDatosEnVista() {
        val ListaProductos = dbHelper.verProductos()


        if (ListaProductos.size== 0) {
            Toast.makeText(this, "La lista esta vacía", Toast.LENGTH_SHORT).show()
        } else {
            for (prod in ListaProductos) {
                //Accede a los atributos o metodos de cada objeto productos
                IdRecuperado.add(prod.id)
                NombreRecuperado.add(prod.nombre)
                CategoriaRecuperado.add(prod.categoria)
                CantidadRecuperado.add(prod.cantidadS + prod.cantidadM + prod.cantidadL)
                PrecioVentaRecuperado.add(prod.precioVenta)
                ImagenRecuperado1.add(prod.imagen1)
            }

            var recyclerView: RecyclerView = findViewById(R.id.RecycleInventario)
            val adapter = CustomerAdapter(IdRecuperado, NombreRecuperado, CategoriaRecuperado, CantidadRecuperado, PrecioVentaRecuperado, ImagenRecuperado1)
            recyclerView.layoutManager = LinearLayoutManager(this)
            recyclerView.adapter = adapter
        }
    }
}