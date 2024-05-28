package sv.edu.ues.proyectopdm2024gt3grupo1

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import sv.edu.ues.proyectopdm2024gt3grupo1.data.DataHelper

class CustomerAdapterItemCategorias(
    catRecuperado:ArrayList<String>
):RecyclerView.Adapter<CustomerAdapterItemCategorias.ViewHolder>() {

    private lateinit var CatRecuperado: ArrayList<String>


    init {
        this.CatRecuperado = catRecuperado
    }

    public class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var itemCat: TextView = itemView.findViewById(R.id.txtIdCategorias)
        val  imagen: ImageView = itemView.findViewById(R.id.imagenCategoria)



        init {
            imagen.setOnClickListener() {
              val intent = Intent(itemView.context, ProductosPorCategoriaActivity::class.java).apply {
                    putExtra("nombreCategoria", itemCat.text.toString())
                }
                itemView.context.startActivity(intent)

            }
        }


    }



    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CustomerAdapterItemCategorias.ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.detalle_item_categoria, parent, false)
        return ViewHolder(v)

    }
        override fun getItemCount(): Int {
            return CatRecuperado.size
        }

    override fun onBindViewHolder(
        holder: CustomerAdapterItemCategorias.ViewHolder,
        position: Int
    ) {
        holder.itemCat.text = CatRecuperado[position]
    }



}




