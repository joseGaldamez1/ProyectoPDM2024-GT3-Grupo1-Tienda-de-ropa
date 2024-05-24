package sv.edu.ues.proyectopdm2024gt3grupo1

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.Toolbar
import com.bumptech.glide.Glide
import sv.edu.ues.proyectopdm2024gt3grupo1.data.DataHelper

class ModificarProductoActivity : AppCompatActivity() {

    //Conexion a la base de datos
    private lateinit var dbHelper: DataHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_modificar_producto)

        //toolbar
        val toolbar: Toolbar = findViewById(R.id.toolbarModificarEdit)
        setSupportActionBar(toolbar)
        getSupportActionBar()?.setDisplayHomeAsUpEnabled(true)
        getSupportActionBar()?.setDisplayShowHomeEnabled(true)

        dbHelper= DataHelper(this)
        val btnBuscar: Button = findViewById(R.id.btnBuscarId)
        btnBuscar.setOnClickListener(){
            BuscarProducto()
        }
    }



    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_eliminar, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            //Acciones para modificar el producto
            R.id.btnEditarTol->{
                ActualizarProducto()

            }
            //Acciones para eliminar el producto
            R.id.btnEliminarTol ->{
                val builder = AlertDialog.Builder(this)
                builder.setMessage("¿Esta seguro que desea eliminar el producto?")
                builder.setPositiveButton("Si") { dialogInterface: DialogInterface, i: Int->
                    EliminarProducto()
                }
                builder.setNegativeButton("No") { dialogInterface: DialogInterface, i: Int->

                }
                builder.show()
                }
           android.R.id.home->finish()
        }
        return super.onOptionsItemSelected(item)
    }



    //Funcion para desplegar los datos
    private fun BuscarProducto() {
        val idBuscado: EditText = findViewById(R.id.txtModId)
        val nombreBuscado: EditText = findViewById(R.id.txtModNombre)
        val categoriaBuscado: EditText = findViewById(R.id.txtModCategorias)
        val cantidadSBuscado: EditText = findViewById(R.id.txtModCantidadS)
        val cantidadMBuscado: EditText = findViewById(R.id.txtModCantidadM)
        val cantidadLBuscado: EditText = findViewById(R.id.txtModCantidadL)
        val precioCompraBuscado: EditText = findViewById(R.id.txtModPrecioCompra)
        val precioVentaBuscado: EditText = findViewById(R.id.txtModPrecioVenta)
        val descripcionBuscado: EditText = findViewById(R.id.txtModDescripcion)
        val imagen1Buscado: ImageView = findViewById(R.id.Modimage1)

        if(idBuscado.getText().toString().isEmpty()){
            Toast.makeText(this, "El codigo esta vacío", Toast.LENGTH_SHORT).show()
        }else{
            val productoBuscado:ArrayList<Productos> =dbHelper.ConsultarProducto(idBuscado.getText().toString())
            if (productoBuscado.size==0){
                Toast.makeText(this, "Codigo de producto no existe", Toast.LENGTH_SHORT).show()
            }else{
                Toast.makeText(this, "Producto encontrado", Toast.LENGTH_SHORT).show()


             //   idBuscado.setText(productoBuscado[0].id).toString()
                nombreBuscado.setText(productoBuscado[0].nombre)
                categoriaBuscado.setText(productoBuscado[0].categoria)
                cantidadSBuscado.setText(productoBuscado[0].cantidadS.toString())
                cantidadMBuscado.setText(productoBuscado[0].cantidadM.toString())
                cantidadLBuscado.setText(productoBuscado[0].cantidadL.toString())
                precioCompraBuscado.setText(productoBuscado[0].precioCompra.toString())
                precioVentaBuscado.setText(productoBuscado[0].precioVenta.toString())
                descripcionBuscado.setText(productoBuscado[0].descripcion)
                Glide.with(this)
                    .load(productoBuscado[0].imagen1)
                    .into(imagen1Buscado)
            }
        }

    }

    fun ActualizarProducto(){
        val idMod: EditText = findViewById(R.id.txtModId)
        val nombreMod: EditText = findViewById(R.id.txtModNombre)
        val categoriaMod: EditText = findViewById(R.id.txtModCategorias)
        val cantidadModS: EditText = findViewById(R.id.txtModCantidadS)
        val cantidadModM: EditText = findViewById(R.id.txtModCantidadM)
        val cantidadModL: EditText = findViewById(R.id.txtModCantidadL)
        val precioCompraMod: EditText = findViewById(R.id.txtModPrecioCompra)
        val precioVentaMod: EditText = findViewById(R.id.txtModPrecioVenta)
        val descripcionMod: EditText = findViewById(R.id.txtModDescripcion)

        //condicion para que si esta vacio nos diga que no se puede mod
        if (nombreMod.text.toString()==""){
            Toast.makeText(this, "Busque un producto para modificar", Toast.LENGTH_SHORT).show()
        }else {
            val idResultado = dbHelper.ActualizarProducto(
                idMod.text.toString().toInt(),
                nombreMod.text.toString(),
                categoriaMod.text.toString().toInt(),
                cantidadModS.text.toString().toInt(),
                cantidadModM.text.toString().toInt(),
                cantidadModL.text.toString().toInt(),
                precioCompraMod.text.toString().toDouble(),
                precioVentaMod.text.toString().toDouble(),
                descripcionMod.text.toString()
            )
            val intent = Intent(this, InventarioActivity::class.java)
            startActivity(intent)

            if (idResultado == 0) {
                Toast.makeText(this, "No se pudo actualizar", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Se actualizó con éxito", Toast.LENGTH_SHORT).show()
                LimpiarFormulario()
            }
        }
    }

    private fun LimpiarFormulario() {
        val idMod: EditText = findViewById(R.id.txtModId)
        val nombreMod: EditText = findViewById(R.id.txtModNombre)
        val categoriaMod: EditText = findViewById(R.id.txtModCategorias)
        val cantidadModS: EditText = findViewById(R.id.txtModCantidadS)
        val cantidadModM: EditText = findViewById(R.id.txtModCantidadM)
        val cantidadModL: EditText = findViewById(R.id.txtModCantidadL)
        val precioCompraMod: EditText = findViewById(R.id.txtModPrecioCompra)
        val precioVentaMod: EditText = findViewById(R.id.txtModPrecioVenta)
        val descripcionMod: EditText = findViewById(R.id.txtModDescripcion)
        val imagen1Mod: ImageView = findViewById(R.id.Modimage1)

        idMod.setText("")
        nombreMod.setText("")
        categoriaMod.setText("")
        cantidadModS.setText("")
        cantidadModM.setText("")
        cantidadModL.setText("")
        precioCompraMod.setText("")
        precioVentaMod.setText("")
        descripcionMod.setText("")
        Glide.with(this)
            .load("")
            .into(imagen1Mod)

    }

    private fun EliminarProducto() {
        val idElim: EditText = findViewById(R.id.txtModId)

        if (idElim.text.toString()==""){
            Toast.makeText(this, "Busque un producto para eliminar", Toast.LENGTH_SHORT).show()
        }else {

            val idResultado = dbHelper.EliminarProducto(idElim.text.toString().toInt())
            val intent = Intent(this, InventarioActivity::class.java)
            startActivity(intent)

            if (idResultado == 0) {
                Toast.makeText(this, "No se pudo eliminar", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Se eliminó el producto", Toast.LENGTH_SHORT).show()
                LimpiarFormulario()
            }
        }
    }



}