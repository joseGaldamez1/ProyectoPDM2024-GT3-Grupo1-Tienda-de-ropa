package sv.edu.ues.proyectopdm2024gt3grupo1

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import sv.edu.ues.proyectopdm2024gt3grupo1.data.DataHelper

class PedidosActivity : AppCompatActivity() {

    private lateinit var dbHelper: DataHelper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pedidos)

        dbHelper = DataHelper(this)


        LlenarPedidos()

        //toolbar
        val toolbar: Toolbar = findViewById(R.id.toolbarPedidos)
        setSupportActionBar(toolbar)
        getSupportActionBar()?.setDisplayHomeAsUpEnabled(true)
        getSupportActionBar()?.setDisplayShowHomeEnabled(true)
    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            android.R.id.home-> {
                val intent = Intent(this, pruebaControlActivity::class.java)
                startActivity(intent)}
                //finish()
            }

        return super.onOptionsItemSelected(item)
    }



    private fun LlenarPedidos() {
        val listaProductos = dbHelper.VerPedidos()
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
            clientesRecuperados.add(productos.first().idUsuario.toString() +" "+ productos.first().apellidos)
            estadoRecuperado.add(productos.first().estado)
        }

        var recyclerView: RecyclerView = findViewById(R.id.RecyclePedidos)
        val adapter = CustomerAdapterPedidos(numerosRecuperado, clientesRecuperados, estadoRecuperado )
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

    }
}