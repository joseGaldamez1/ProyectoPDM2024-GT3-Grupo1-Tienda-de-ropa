package sv.edu.ues.proyectopdm2024gt3grupo1

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import sv.edu.ues.proyectopdm2024gt3grupo1.data.DataHelper
import java.text.NumberFormat
import java.util.Locale

class DatosPedidoActivity : AppCompatActivity() {

    var nomProducto: String = ""
    var total = 0.00
    private lateinit var dbHelper: DataHelper
    private lateinit var NombreRecuperado: ArrayList<String>
    private lateinit var CantidadRecuperado: ArrayList<Int>
    private lateinit var PrecioVentaRecuperado: ArrayList<Double>
    private lateinit var ImagenRecuperado: ArrayList<String>
    private lateinit var TallaRecuperado: ArrayList<String>

    //cliente
    private lateinit var nombre: TextView
    private lateinit var telefono: TextView
    private lateinit var direccion: TextView
    private lateinit var totalPagar: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_datos_pedido)

        val producto = intent.getStringExtra("numeroPedido") ?: ""
        nomProducto = producto
        dbHelper = DataHelper(this)
        NombreRecuperado = ArrayList()
        CantidadRecuperado = ArrayList()
        PrecioVentaRecuperado = ArrayList()
        ImagenRecuperado = ArrayList()
        TallaRecuperado = ArrayList()

        LlenarDatosEnVista()

        //cliente
        nombre = findViewById(R.id.txtNombrePedido)
        telefono = findViewById(R.id.txtTelefonoPedido)
        direccion = findViewById(R.id.txtDireccionPedido)
        totalPagar = findViewById(R.id.txtTotalPagarPedido)

        CargarPerfil()
        //toolbar
        val toolbar: Toolbar = findViewById(R.id.toolbarDetallePedido)
        setSupportActionBar(toolbar)
        getSupportActionBar()?.setDisplayHomeAsUpEnabled(true)
        getSupportActionBar()?.setDisplayShowHomeEnabled(true)

        //finalizar pedido
        val btnFinalizar: Button = findViewById(R.id.btnFinalizarPedido)
        btnFinalizar.setOnClickListener() {
            FinalizarPedido()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {//finish()
                val rol = obtenerUsuario(this).toString()
                val prueba = dbHelper.ConsultarUsuario(rol)
                val roll = prueba[0].rol
                if(roll=="Usuario"){
                    finish()
                }else{
                    finish()
                    val intent = Intent(this, PedidosActivity::class.java)
                    startActivity(intent)
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun LlenarDatosEnVista() {
        NombreRecuperado.clear()
        CantidadRecuperado.clear()
        PrecioVentaRecuperado.clear()
        ImagenRecuperado.clear()
        TallaRecuperado.clear()
        val ListaProductos = dbHelper.verDetallePedido(nomProducto)
        var estado: String
        var rol: String

        for (prod in ListaProductos) {
            //Accede a los atributos o metodos de cada objeto productos
            NombreRecuperado.add(prod.nombre)
            CantidadRecuperado.add(prod.cantidad)
            PrecioVentaRecuperado.add(prod.precio)
            ImagenRecuperado.add(prod.imagen)
            TallaRecuperado.add(prod.talla)
            total += prod.cantidad * prod.precio

            estado=prod.estado
            if(estado=="Finalizado"){
                val btnFin: Button = findViewById(R.id.btnFinalizarPedido)
                btnFin.visibility = View.GONE
            }

            //rol=prod.rol
            rol = obtenerUsuario(this).toString()
            val prueba = dbHelper.ConsultarUsuario(rol)
            val roll = prueba[0].rol
            if(roll=="Usuario"){
                val btnFin: Button = findViewById(R.id.btnFinalizarPedido)
                btnFin.visibility = View.GONE
            }
        }

        var recyclerView: RecyclerView = findViewById(R.id.recycleDetallePedido)
        val adapter = CustomerAdapterProductoPedido(
            NombreRecuperado,
            CantidadRecuperado,
            PrecioVentaRecuperado,
            ImagenRecuperado,
            TallaRecuperado
        )
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter
    }
    fun obtenerUsuario(context: Context): String? {
        val nomUser = context.getSharedPreferences("MyAppPrefs", Context.MODE_PRIVATE)
        return nomUser.getString("USERNAME_KEY", null)
    }

    private fun CargarPerfil() {

        //Obtener el id del usuario para cargarlo en el perfil
        val id = dbHelper.verDetallePedido(nomProducto)
        val idClien = id[0].idCliente
        val datos = dbHelper.ConsultarUsuario(idClien)
        val nombresApellidos = datos[0].nombres + " " + datos[0].apellidos
        val dire =
            datos[0].direccion + ", " + datos[0].distrito + ", " + datos[0].municipio + ", " + datos[0].departamento
        nombre.setText(nombresApellidos)
        telefono.setText(datos[0].telefono)
        direccion.setText(dire)
        //formato con 2 decimales
        totalPagar.setText(NumberFormat.getCurrencyInstance(Locale.US).format(total))
    }

    fun FinalizarPedido(){
        val id = dbHelper.verDetallePedido(nomProducto)
        val idClien = id[0].idCliente
        val resultado = dbHelper.FinalizarPedido("Finalizado", idClien.toInt(), nomProducto.toInt())
        if(resultado >= 1){
            Toast.makeText(this, "Pedido Finalizado", Toast.LENGTH_SHORT).show()
            LlenarDatosEnVista()
        /*    val intent = Intent(this, PedidosActivity::class.java)
            startActivity(intent)*/
        }else{
            Toast.makeText(this, "Ocurrió un error, no puede finalizar el pedido", Toast.LENGTH_SHORT).show()
        }
    }
}