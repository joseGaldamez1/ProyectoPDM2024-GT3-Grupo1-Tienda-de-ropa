package sv.edu.ues.proyectopdm2024gt3grupo1

import android.content.Context
import android.content.SharedPreferences
import androidx.fragment.app.Fragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.AppCompatImageButton
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import sv.edu.ues.proyectopdm2024gt3grupo1.data.DataHelper


class CarritoFragment : Fragment() {

    private lateinit var dbHelper: DataHelper
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: CustomerAdapterCarrito

    private val imagenRecuperado = ArrayList<String>()
    private val nombreRecuperado = ArrayList<String>()
    private val precioRecuperado = ArrayList<String>()
    private val tallaRecuperado = ArrayList<String>()
    private val cantidadRecuperado = ArrayList<String>()

    //Total a pagar por el usuario
    private lateinit var totalPagar: TextView

    //Variable para almacenar el numero de pedido
    private lateinit var sharedPreferences: SharedPreferences
    private var numeroPedido: Int =0


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_carrito, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        dbHelper = DataHelper(requireContext())
        recyclerView = view.findViewById(R.id.RecycleCarrito)

        //Variable para el numeroPedido
        sharedPreferences = requireActivity().getSharedPreferences("app.pref", Context.MODE_PRIVATE)
        numeroPedido = sharedPreferences.getInt("numeroPedido",0)

        LlenarCarrito()
        //boton para crear pedido
        val btnPedido: Button = view.findViewById(R.id.btnCrearPedido)

        btnPedido.setOnClickListener(){
            numeroPedido++
            with(sharedPreferences.edit()){
                putInt("numeroPedido", numeroPedido)
                apply()
            }
            Toast.makeText(requireContext(), "Pedido enviado con exito", Toast.LENGTH_SHORT).show()
            dbHelper.ActualizarPedido(numeroPedido,"Pendiente", 1)
            LlenarCarrito()
        }

        //total a pagar
        totalPagar = view.findViewById(R.id.txtTotalPagar)
        calcularTotalPagar()


    }

    private fun LlenarCarrito() {
        val usuario = "1"
        val estado = "Carrito"
        val ListaProductos = dbHelper.verCarrito(usuario, estado)

        imagenRecuperado.clear()
        nombreRecuperado.clear()
        precioRecuperado.clear()
        cantidadRecuperado.clear()
        tallaRecuperado.clear()

        for (prod in ListaProductos) {
            // Divide la cadena de texto del producto en partes usando la coma como separador
            val partes = prod.split(", ")
            val nombreProd = partes[0].split(": ")[1]
            val precio = partes[1].split(": ")[1]
            val imagen = partes[2].split(": ")[1]
            val cantidad = partes[3].split(": ")[1]
            val talla = partes[4].split(": ")[1]

            // Agrega los valores recuperados a las listas correspondientes
            nombreRecuperado.add(nombreProd)
            precioRecuperado.add(precio)
            imagenRecuperado.add(imagen)
            cantidadRecuperado.add(cantidad)
            tallaRecuperado.add(talla)
        }

        // Configura el adaptador y el recyclerView
        val adapter = CustomerAdapterCarrito(nombreRecuperado, imagenRecuperado, precioRecuperado, tallaRecuperado, cantidadRecuperado)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = adapter

    }

    private fun calcularTotalPagar() {
        val usuario = "1"
        val estado = "Carrito"
        val ListaProductos = dbHelper.verCarrito(usuario, estado)
        var total = 0.0

        for (prod in ListaProductos) {
            val partes = prod.split(", ")
            val precio = partes[1].split(": ")[1].toDoubleOrNull() ?: 0.0
            val cantidad = partes[3].split(": ")[1].toIntOrNull() ?: 0

            total += precio * cantidad
        }
        Toast.makeText(requireContext(), "El total es: "+total, Toast.LENGTH_SHORT).show()
        totalPagar.setText(total.toString())
    }






}


