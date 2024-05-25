package sv.edu.ues.proyectopdm2024gt3grupo1


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import sv.edu.ues.proyectopdm2024gt3grupo1.data.DataHelper


class CategoriasFragment : Fragment() {

    private lateinit var dbHelper: DataHelper

    private val imagenRecuperado = ArrayList<String>()
    private val nombreRecuperado = ArrayList<String>()
    private val precioRecuperado = ArrayList<String>()

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: CustomerAdapterItemCategorias
    private val CatRecuperado = ArrayList<String>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_categorias, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        dbHelper = DataHelper(requireContext())
        recyclerView = view.findViewById(R.id.RecycleCategoriasHome)
        LlenarDatosEnVista()
    }


    private fun LlenarDatosEnVista() {
        CatRecuperado.clear()
        val ListaCateg = dbHelper.verCategorias()

        if (ListaCateg.isEmpty()) {
            Toast.makeText(requireContext(), "No hay categorias", Toast.LENGTH_SHORT).show()
        } else {
            for (categorias in ListaCateg) {
                //Accede a los atributos o metodos de cada objeto productos

                CatRecuperado.add(categorias.nombre)
                adapter = CustomerAdapterItemCategorias(CatRecuperado)
                recyclerView.layoutManager = GridLayoutManager(requireContext(), 2)
                recyclerView.adapter = adapter

            }
            adapter.notifyDataSetChanged()
        }
    }



}
