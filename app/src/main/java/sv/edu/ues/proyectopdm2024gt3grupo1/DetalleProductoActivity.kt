package sv.edu.ues.proyectopdm2024gt3grupo1


import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import com.bumptech.glide.Glide
import sv.edu.ues.proyectopdm2024gt3grupo1.data.DataHelper


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
            Toast.makeText(null, "El codigo esta vacío", Toast.LENGTH_SHORT).show()
        }else{
            val productoBuscado:ArrayList<Productos> =dbHelper.ConsultarProductoNombre(nombreProd)
                nombre.setText(productoBuscado[0].nombre)
                precio.setText("$ "+ productoBuscado[0].precioVenta.toString())
                descripcion.setText(productoBuscado[0].descripcion)
                Glide.with(this)
                    .load(productoBuscado[0].imagen1)
                    .into(imagen)
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
                val idResultado = dbHelper.AddCarrito(1, res.toInt(), Produ, txtCantidad.text.toString().toInt())
                if (idResultado == -1L) {
                    Toast.makeText(this, "Hubo un error", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, "Agregado al carrito!!", Toast.LENGTH_SHORT).show()
                    txtCantidad.setText("")
                }
            }
        } else if (Produ == "M") {
            if (txtCantidad.text.toString().toInt() > tallaM.toInt()) {
                Toast.makeText(this, "No hay suficientes existencias", Toast.LENGTH_SHORT).show()
            } else {
                val idResultado =
                    dbHelper.AddCarrito(1, res.toInt(), Produ, txtCantidad.text.toString().toInt())
                if (idResultado == -1L) {
                    Toast.makeText(this, "Hubo un error", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, "Agregado al carrito!!", Toast.LENGTH_SHORT).show()
                    txtCantidad.setText("")
                }
            }
        } else if (Produ == "L") {
            if (txtCantidad.text.toString().toInt() > tallaL.toInt()) {
                Toast.makeText(this, "No hay suficientes existencias", Toast.LENGTH_SHORT).show()
            } else {
                val idResultado =
                    dbHelper.AddCarrito(1, res.toInt(), Produ, txtCantidad.text.toString().toInt())
                if (idResultado == -1L) {
                    Toast.makeText(this, "Hubo un error", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, "Agregado al carrito!!", Toast.LENGTH_SHORT).show()
                    txtCantidad.setText("")
                }
            }
        } else {
            Toast.makeText(this, "Talla no válida", Toast.LENGTH_SHORT).show()
        }


        //fin prueba
       /*val idResultado = dbHelper.AddCarrito( 1, res.toInt(), Produ, txtCantidad.text.toString().toInt())
        if(idResultado==-1.toLong()){
            Toast.makeText(this, "Hubo un error", Toast.LENGTH_SHORT).show()
        }else{
            Toast.makeText(this, "Agregado al carrito!!", Toast.LENGTH_SHORT).show()
            txtCantidad.setText("")
        }*/
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
            Toast.makeText(this, "No se encontraron productos", Toast.LENGTH_SHORT).show()
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


}

