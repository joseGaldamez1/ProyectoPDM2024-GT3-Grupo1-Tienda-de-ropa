package sv.edu.ues.proyectopdm2024gt3grupo1

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.Toolbar
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import sv.edu.ues.proyectopdm2024gt3grupo1.data.DataHelper

class AdministrarCategoriasActivity : AppCompatActivity(){

    private lateinit var dbHelper: DataHelper

    private lateinit var CatRecuperado: ArrayList<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_administrar_categorias)

        //toolbar
        val toolbar: Toolbar = findViewById(R.id.toolbarAdminCategorias)
        setSupportActionBar(toolbar)
        getSupportActionBar()?.setDisplayHomeAsUpEnabled(true)
        getSupportActionBar()?.setDisplayShowHomeEnabled(true)

        dbHelper= DataHelper(this)

        CatRecuperado = ArrayList()

        val abrir: ImageButton = findViewById(R.id.BotonAgregarCategorias)
        abrir.setOnClickListener(){
            InputCategoria(this, "Ingrese el nombre de la categoría"){ InsertCategoria ->
                GuardarCategoriaEnBD(InsertCategoria)
                LlenarDatosEnVista()
            }
        }
        LlenarDatosEnVista()

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_inventario, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.guardar->{

            }
            android.R.id.home-> {
                finish()
            }
            }


        return super.onOptionsItemSelected(item)
    }

    fun InputCategoria(context: Context, title: String, callback: (String) -> Unit) {
        val editText = EditText(context)
        val dialog = AlertDialog.Builder(context)
            .setTitle(title)
            .setView(editText)
            .setPositiveButton("Aceptar") { _, _ ->
                val inputText = editText.text.toString()
                callback(inputText)
            }
            .setNegativeButton("Cancelar") { dialog, _ ->
                dialog.cancel()
            }
            .create()

        dialog.show()
    }

    fun GuardarCategoriaEnBD(InsertCategoria: String){

        val idResultado = dbHelper.AddCategoria(InsertCategoria)
        if(idResultado==-1.toLong()){
            Toast.makeText(this, "Hubo un error", Toast.LENGTH_SHORT).show()
        }else{
            Toast.makeText(this, "Se guardó correctamente", Toast.LENGTH_SHORT).show()
        }
    }


    private fun LlenarDatosEnVista() {
        CatRecuperado.clear()
        val ListaCateg = dbHelper.verCategorias()
        if (ListaCateg.size== 0) {
            Toast.makeText(this, "La lista esta vacía", Toast.LENGTH_SHORT).show()
        } else {
            for (prod in ListaCateg) {
                //Accede a los atributos o metodos de cada objeto productos

                CatRecuperado.add(prod.nombre)

            }
            var recyclerView: RecyclerView = findViewById(R.id.RecycleCategorias)
            val adapter = CustomerAdapterCategorias(CatRecuperado)
            recyclerView.layoutManager = LinearLayoutManager(this)
            recyclerView.adapter = adapter

        }
    }


}