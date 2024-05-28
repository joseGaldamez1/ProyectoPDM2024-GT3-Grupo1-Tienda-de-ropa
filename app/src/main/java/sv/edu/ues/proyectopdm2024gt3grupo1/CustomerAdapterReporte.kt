package sv.edu.ues.proyectopdm2024gt3grupo1

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import org.w3c.dom.Text

class CustomerAdapterReporte(nombreRecuperado:ArrayList<String>, imagenRecuperado:ArrayList<String>, cantidadRecuperado:ArrayList<Int>
):RecyclerView.Adapter<CustomerAdapterReporte.ViewHolder>() {

    private lateinit var NombreRecuperado: ArrayList<String>
    private lateinit var ImagenRecuperado: ArrayList<String>
    private lateinit var CantidadRecuperado: ArrayList<Int>
    init {
        this.NombreRecuperado = nombreRecuperado
        this.ImagenRecuperado = imagenRecuperado
        this.CantidadRecuperado = cantidadRecuperado
    }

    public class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var itemNombre: TextView = itemView.findViewById(R.id.txtNombreReporte)
        var itemImagen: ImageView = itemView.findViewById(R.id.txtImagenReporte)
        var itemCantidad: TextView = itemView.findViewById(R.id.txtcantidadReporte)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_reporte, parent, false)
        return CustomerAdapterReporte.ViewHolder(v)
    }

    override fun getItemCount(): Int {
      return NombreRecuperado.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemNombre.text=NombreRecuperado[position]
        val imageUrl = ImagenRecuperado[position]
        Glide.with(holder.itemView.context)
            .load(imageUrl)
            .error("https://www.shutterstock.com/image-vector/default-ui-image-placeholder-wireframes-600nw-1037719192.jpg")
            .into(holder.itemImagen)
        holder.itemCantidad.text = CantidadRecuperado[position].toString()
    }
}
