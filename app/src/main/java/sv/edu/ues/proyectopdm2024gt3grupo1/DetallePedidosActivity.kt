package sv.edu.ues.proyectopdm2024gt3grupo1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import sv.edu.ues.proyectopdm2024gt3grupo1.data.DataHelper

class DetallePedidosActivity : AppCompatActivity() {
    private lateinit var dbHelper: DataHelper


    private lateinit var NombreRecuperado: ArrayList<String>
    private lateinit var CantidadRecuperado: ArrayList<Int>
    private lateinit var PrecioVentaRecuperado: ArrayList<Double>
    private lateinit var ImagenRecuperado1: ArrayList<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detalle_pedidos)




        dbHelper = DataHelper(this)
        NombreRecuperado = ArrayList()
        CantidadRecuperado = ArrayList()
        PrecioVentaRecuperado = ArrayList()
        ImagenRecuperado1 = ArrayList()
        LlenarDatosEnVista()

    }

    private fun LlenarDatosEnVista() {
        val listaProductos = dbHelper.VerPedidos(1)

        val productosPorPedidoMap = HashMap<String, MutableList<Pedidos>>()

        for (prod in listaProductos) {
            val productosPedido = productosPorPedidoMap.getOrPut(prod.numeroPedido.toString()) { mutableListOf() }
            productosPedido.add(prod)
            Log.i("EJEMPLO", prod.toString())

        }

        val nombresRecuperados = ArrayList<String>()
        val cantidadesRecuperadas = ArrayList<Int>()
        val preciosVentaRecuperados = ArrayList<Double>()
        val imagenesRecuperadas = ArrayList<String>()

        for ((_, productos) in productosPorPedidoMap) {
            for (producto in productos) {
                nombresRecuperados.add(producto.nombreProd)
                cantidadesRecuperadas.add(producto.cantidad)
                preciosVentaRecuperados.add(producto.precioVenta)
                imagenesRecuperadas.add(producto.imagen)
            }
        }

        if (nombresRecuperados.isEmpty()) {
            Toast.makeText(this, "La lista está vacía", Toast.LENGTH_SHORT).show()
        } else {
            val inflater = LayoutInflater.from(this)
            val view = inflater.inflate(R.layout.activity_detalle_pedidos, null)
            var recyclerView: RecyclerView = view.findViewById(R.id.RecycleVerProductos)
            val adapter = CustomerAdapterProductoPedido(nombresRecuperados, cantidadesRecuperadas, preciosVentaRecuperados, imagenesRecuperadas)
            recyclerView.layoutManager = LinearLayoutManager(this)
            recyclerView.adapter = adapter
        }
    }

}
