package sv.edu.ues.proyectopdm2024gt3grupo1

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class CustomerAdapterCategorias(
    catRecuperado:ArrayList<String>

    ):RecyclerView.Adapter<CustomerAdapterCategorias.ViewHolder>() {

    private lateinit var CatRecuperado: ArrayList<String>



    init {
        this.CatRecuperado = catRecuperado
    }

    public class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var itemCat: TextView = itemView.findViewById(R.id.txtIdCategorias)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int ): CustomerAdapterCategorias.ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.detalle_categoria, parent, false)
        return ViewHolder(v)


    }

    override fun getItemCount(): Int {
        return CatRecuperado.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemCat.text=CatRecuperado[position]
    }
}

