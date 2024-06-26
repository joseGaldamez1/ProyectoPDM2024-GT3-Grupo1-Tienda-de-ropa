package sv.edu.ues.proyectopdm2024gt3grupo1


import android.content.Context
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.core.net.toUri
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import sv.edu.ues.proyectopdm2024gt3grupo1.data.DataHelper
import java.text.NumberFormat
import java.util.Locale


class DetalleProductoActivity : AppCompatActivity() {

    private lateinit var dbHelper: DataHelper

    //Controles del formulario
    private lateinit var txtIdUsuario: EditText
    private lateinit var txtIdProducto: EditText
    private lateinit var txtTalla: Spinner
    private lateinit var txtCantidad: EditText
    private lateinit var Produ: String
    private lateinit var res: String
    private lateinit var txtNombre: String
    private var tallaSeleccionada = ""
    private lateinit var nombreSeleccionado: String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detalle_producto)

        dbHelper = DataHelper(this)


        val producto = intent.getStringExtra("nombreProducto")?: ""
        CargarProducto(producto)

        //toolbar
        val toolbar: Toolbar = findViewById(R.id.toolbarModificarEdit)
        setSupportActionBar(toolbar)
        getSupportActionBar()?.setDisplayHomeAsUpEnabled(true)
        getSupportActionBar()?.setDisplayShowHomeEnabled(true)

        Produ = findViewById<Spinner?>(R.id.spinnerCarrito).selectedItem.toString()
        txtNombre = findViewById<TextView?>(R.id.txtDetalleNombre).text.toString()
        nombreSeleccionado = findViewById<TextView?>(R.id.txtDetalleNombre).text.toString()


        //Agregar a la bolsa
        val btnCarr: Button = findViewById(R.id.btnAgregarBolsa)

        val rol = obtenerUsuario(this).toString()
        val prueba = dbHelper.ConsultarUsuario(rol)
        val roll = prueba[0].rol
        if(roll=="Administrador"){
         //   btnCarr.isEnabled = false
        }
        btnCarr.setOnClickListener(){
                GuardarCarrito()
        }

      val spinerCarrito: Spinner = findViewById(R.id.spinnerCarrito)
        spinerCarrito.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                tallaSeleccionada = parent.getItemAtPosition(position).toString()
                Produ = findViewById<Spinner?>(R.id.spinnerCarrito).selectedItem.toString()
                // llamar al metodo para cargar la cantidad de existencias
                Existencias()


            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }
        }



    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        //menuInflater.inflate(R.menu.menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){

            android.R.id.home->finish()
        }
        return super.onOptionsItemSelected(item)
    }


    //Funcion para desplegar los datos
    private fun CargarProducto(nombreProd: String) {

        val nombre: TextView = findViewById(R.id.txtDetalleNombre)
        val precio: TextView = findViewById(R.id.txtDetallePrecio)
        val descripcion: TextView = findViewById(R.id.txtDetalleDescripcionHome)
        val imagen: ImageView = findViewById(R.id.txtImagenDetalleHome)

        if(nombreProd.isEmpty()){
            Toast.makeText(this, "El codigo esta vacío", Toast.LENGTH_SHORT).show()
        }else {
            val productoBuscado: ArrayList<Productos>
            productoBuscado = dbHelper.ConsultarProductoNombre(nombreProd)
            if (productoBuscado.isNotEmpty()) {
                nombre.setText(productoBuscado[0].nombre)
                precio.setText(NumberFormat.getCurrencyInstance(Locale.US).format(productoBuscado[0].precioVenta))
                descripcion.setText(productoBuscado[0].descripcion)
                Glide.with(this)
                    .load(productoBuscado[0].imagen1)
                    .into(imagen)
            }else{
                //Intentar cargar aqui la informacion del producto
                Toast.makeText(this, "El producto no esta disponible", Toast.LENGTH_SHORT).show()
                val btnCarr: Button = findViewById(R.id.btnAgregarBolsa)
                btnCarr.isEnabled = false

                Glide.with(this)
                    .load("https://www.artenatur.com/wp-content/uploads/2017/02/proximamente.png")
                    .into(imagen)
                descripcion.setText("Este producto no está disponible por el momento")
                descripcion.setTextColor(Color.RED)
                precio.setText("$0.00")
                nombre.setText("S/N")
            }
        }
        }

    //funcion para agregar datos al carrito
    fun GuardarCarrito() {
        txtTalla = findViewById(R.id.spinnerCarrito)
        txtCantidad = findViewById(R.id.txtCantidadCarrito)
        // Produ = findViewById<Spinner?>(R.id.spinnerCarrito).selectedItem.toString()
        res = dbHelper.ConsultarIdProducto(txtNombre).toString()
        //prueba
        val exis: TextView = findViewById(R.id.txtDisponibilidadTallas)
        var tallaS = ""
        var tallaM = ""
        var tallaL = ""
        val tallas: ArrayList<Productos> = dbHelper.ConsultarProductoNombre(nombreSeleccionado.toString())
        tallaS = tallas[0].cantidadS.toString()
        tallaM = tallas[0].cantidadM.toString()
        tallaL = tallas[0].cantidadL.toString()
        if (Produ == "S") {
            if (txtCantidad.text.toString().toInt() > tallaS.toInt()) {
                Toast.makeText(this, "No hay suficientes existencias", Toast.LENGTH_SHORT).show()
            } else {
                val usuario = obtenerUsuario(this)
                val idResultado = dbHelper.AddCarrito(usuario.toString().toInt(), res.toInt(), Produ, txtCantidad.text.toString().toInt())
                Toast.makeText(this, "Agregado al carrito!!", Toast.LENGTH_SHORT).show()
                txtCantidad.setText("")
            }
        } else if (Produ == "M") {
            if (txtCantidad.text.toString().toInt() > tallaM.toInt()) {
                Toast.makeText(this, "No hay suficientes existencias", Toast.LENGTH_SHORT).show()
            } else {
                val usuario = obtenerUsuario(this)
                val idResultado = dbHelper.AddCarrito(usuario.toString().toInt(), res.toInt(), Produ, txtCantidad.text.toString().toInt())
                Toast.makeText(this, "Agregado al carrito!!", Toast.LENGTH_SHORT).show()
                txtCantidad.setText("")
            }
        } else if (Produ == "L") {
            if (txtCantidad.text.toString().toInt() > tallaL.toInt()) {
                Toast.makeText(this, "No hay suficientes existencias", Toast.LENGTH_SHORT).show()
            } else {
                val usuario = obtenerUsuario(this)
                    dbHelper.AddCarrito(usuario.toString().toInt(), res.toInt(), Produ, txtCantidad.text.toString().toInt())
                    Toast.makeText(this, "Agregado al carrito!!", Toast.LENGTH_SHORT).show()
                    txtCantidad.setText("")
                }
            }
        }


    fun Existencias() {
        val exis: TextView = findViewById(R.id.txtDisponibilidadTallas)
        var tallaS = ""
        var tallaM = ""
        var tallaL = ""

        // Consultar la lista de tallas
        val tallas: ArrayList<Productos> = dbHelper.ConsultarProductoNombre(nombreSeleccionado.toString())

        // Verificar si la lista no está vacía antes de acceder a los elementos
        if (tallas.isNotEmpty()) {
            tallaS = tallas[0].cantidadS.toString()
            tallaM = tallas[0].cantidadM.toString()
            tallaL = tallas[0].cantidadL.toString()
        } else {
            // Manejar el caso en que la lista esté vacía
           // Toast.makeText(this, "No se encontraron productos", Toast.LENGTH_SHORT).show()
            return
        }

        // Mostrar la disponibilidad de la talla seleccionada
        when (tallaSeleccionada) {
            "S" -> exis.text = tallaS
            "M" -> exis.text = tallaM
            "L" -> exis.text = tallaL
            else -> exis.text = "Talla no disponible"
        }
    }

    fun obtenerUsuario(context: Context): String? {
        val nomUser = context.getSharedPreferences("MyAppPrefs", Context.MODE_PRIVATE)
        return nomUser.getString("USERNAME_KEY", null)
    }


}

