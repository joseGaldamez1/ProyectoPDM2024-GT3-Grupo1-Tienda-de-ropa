package sv.edu.ues.proyectopdm2024gt3grupo1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import sv.edu.ues.proyectopdm2024gt3grupo1.data.DataHelper
import java.text.NumberFormat
import java.util.Locale

class ReporteActivity : AppCompatActivity() {

    private lateinit var dbHelper: DataHelper

    private lateinit var nombreRecuperado: ArrayList<String>
    private lateinit var imagenRecuperado: ArrayList<String>
    private lateinit var cantidadRecuperado: ArrayList<Int>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reporte)

        //Toolbar
        val toolbar: Toolbar = findViewById(R.id.toolbarReporte)
        setSupportActionBar(toolbar)
        getSupportActionBar()?.setDisplayHomeAsUpEnabled(true)
        getSupportActionBar()?.setDisplayShowHomeEnabled(true)

        dbHelper = DataHelper(this)
        nombreRecuperado = ArrayList()
        imagenRecuperado = ArrayList()
        cantidadRecuperado = ArrayList()
        LlenarProductosEnVista()
        ReporteFinanciero()

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        return super.onCreateOptionsMenu(menu)
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            android.R.id.home->finish()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun LlenarProductosEnVista() {
        val ListaProductos = dbHelper.verProductosMasVendidos()

        if (ListaProductos.size== 0) {
            Toast.makeText(this, "La lista esta vacía", Toast.LENGTH_SHORT).show()
        } else {
            for (prod in ListaProductos) {
                //Accede a los atributos o metodos de cada objeto productos
                nombreRecuperado.add(prod.nombre)
                imagenRecuperado.add(prod.imagen)
                cantidadRecuperado.add(prod.cantidad)
            }

            var recyclerView: RecyclerView = findViewById(R.id.RecycleReporte)
            val adapter = CustomerAdapterReporte(nombreRecuperado, imagenRecuperado, cantidadRecuperado)
            recyclerView.layoutManager = LinearLayoutManager(this)
            recyclerView.adapter = adapter
        }
    }

    private fun ReporteFinanciero(){
        val Lista = dbHelper.verProductosMasVendidos()
        var cantidad = 0
        var costo = 0.00
        var venta =0.00
        val textCostos: TextView = findViewById(R.id.txtCostos)
        val textVentas: TextView = findViewById(R.id.txtIngresos)
        val textGanancias: TextView = findViewById(R.id.txtGanancias)

        var costoTotal = 0.00
        var ventaTotal =0.00
        var gananciaTotal =0.00
        for (prod in Lista){
            cantidad = prod.cantidad
            costo = prod.precioCompra
            venta = prod.precioVenta.toString().toDouble()

            costoTotal += cantidad*costo
            ventaTotal += cantidad*venta
        }
        gananciaTotal = ventaTotal-costoTotal
        //Seteando las ganancias
        textCostos.setText(NumberFormat.getCurrencyInstance(Locale.US).format(costoTotal))
        textVentas.setText(NumberFormat.getCurrencyInstance(Locale.US).format(ventaTotal))
        textGanancias.setText(NumberFormat.getCurrencyInstance(Locale.US).format(gananciaTotal))
    }
}