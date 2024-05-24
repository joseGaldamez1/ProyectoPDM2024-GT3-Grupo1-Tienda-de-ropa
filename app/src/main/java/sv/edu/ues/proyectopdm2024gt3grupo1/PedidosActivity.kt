package sv.edu.ues.proyectopdm2024gt3grupo1

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import sv.edu.ues.proyectopdm2024gt3grupo1.data.DataHelper

class PedidosActivity : AppCompatActivity() {

    private lateinit var dbHelper: DataHelper

    private lateinit var numeroRecuperado: ArrayList<String>
    private lateinit var clienteRecuperado: ArrayList<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pedidos)

        dbHelper = DataHelper(this)
        numeroRecuperado = ArrayList()
        clienteRecuperado = ArrayList()


        LlenarPedidos()
    }


    private fun LlenarPedidos() {
        val listaProductos = dbHelper.VerPedidos(1)

        val pedidosMap = HashMap<String, MutableList<Pedidos>>()

        for (prod in listaProductos) {
            val productosPedido = pedidosMap.getOrPut(prod.numeroPedido.toString()) { mutableListOf() }
            productosPedido.add(prod)
        }


        val numerosRecuperado = ArrayList<String>()
        val clientesRecuperados = ArrayList<String>()
        val estadoRecuperado = ArrayList<String>()


        for ((numeroPedido, productos) in pedidosMap) {
            numerosRecuperado.add(numeroPedido)
            clientesRecuperados.add(productos.first().idUsuario.toString())
            estadoRecuperado.add(productos.first().estado)
        }

        var recyclerView: RecyclerView = findViewById(R.id.RecyclePedidos)
        val adapter = CustomerAdapterPedidos(numerosRecuperado, clientesRecuperados, estadoRecuperado )
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter
        LlenarDatosEnVista()
    }


    private fun LlenarDatosEnVista() {
        val listaProductos = dbHelper.VerPedidos(1)

        val productosPorPedidoMap = HashMap<String, MutableList<Pedidos>>()
        for (prod in listaProductos) {
            val productosPedido = productosPorPedidoMap.getOrPut(prod.numeroPedido.toString()) { mutableListOf() }
            productosPedido.add(prod)
           // Log.i("EJEMPLO", prod.toString())

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
            recyclerView.layoutManager = GridLayoutManager(this,2)
            recyclerView.adapter = adapter
            adapter.notifyDataSetChanged()
        }
    }

}