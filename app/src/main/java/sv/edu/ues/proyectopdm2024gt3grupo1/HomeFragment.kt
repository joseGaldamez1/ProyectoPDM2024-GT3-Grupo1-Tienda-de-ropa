package sv.edu.ues.proyectopdm2024gt3grupo1

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
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
        recyclerView = view.findViewById(R.id.RecycleHome) //este si era

        //prueba
        recyclerView.layoutManager = GridLayoutManager(requireContext(), 2)
        adapter = CustomAdapterHome(imagenRecuperado, nombreRecuperado, precioRecuperado)
        recyclerView.adapter = adapter
        //fin prueba

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
            precioRecuperado.add(
                NumberFormat.getCurrencyInstance(Locale.US).format(prod.precioVenta)
            )
        }

          //Este es funcional
        /* val adapter = CustomAdapterHome(imagenRecuperado, nombreRecuperado, precioRecuperado)
            recyclerView.layoutManager = GridLayoutManager(requireContext(),2)
            recyclerView.adapter = adapter*/

        // Load data from API
        val urlBase = "https://fakestoreapi.com/"
        val retrofit = Retrofit.Builder()
            .baseUrl(urlBase)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val service = retrofit.create(PostApiService::class.java)

        lifecycleScope.launch {
            try {
                val response = service.getuserPost()
                val initialSize = imagenRecuperado.size

                response.forEach {
                    imagenRecuperado.add(it.image)
                    nombreRecuperado.add(it.title)
                    precioRecuperado.add(NumberFormat.getCurrencyInstance(Locale.US).format(it.price))
                }

                adapter.notifyDataSetChanged()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

    }

}

