package sv.edu.ues.proyectopdm2024gt3grupo1

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import sv.edu.ues.proyectopdm2024gt3grupo1.data.DataHelper
import java.text.NumberFormat
import java.util.Locale


class HomeFragment : Fragment() {

    private lateinit var dbHelper: DataHelper

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: CustomAdapterHome

    private val imagenRecuperado = ArrayList<String>()
    private val nombreRecuperado = ArrayList<String>()
    private val precioRecuperado = ArrayList<String>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflar el layout para este fragmento
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        dbHelper = DataHelper(requireContext())
        recyclerView = view.findViewById(R.id.RecycleHome)


        LlenarDatosEnVista()


    }

    private fun LlenarDatosEnVista() {
        val ListaProductos = dbHelper.verProductos()

        imagenRecuperado.clear()
        nombreRecuperado.clear()
        precioRecuperado.clear()

            for (prod in ListaProductos) {
                //Accede a los atributos o metodos de cada objeto productos
                imagenRecuperado.add(prod.imagen1)
                nombreRecuperado.add(prod.nombre)
                precioRecuperado.add(NumberFormat.getCurrencyInstance(Locale.US).format(prod.precioVenta))
            }


            val adapter = CustomAdapterHome(imagenRecuperado, nombreRecuperado, precioRecuperado)
            recyclerView.layoutManager = GridLayoutManager(requireContext(),2)
            recyclerView.adapter = adapter

    }

}

