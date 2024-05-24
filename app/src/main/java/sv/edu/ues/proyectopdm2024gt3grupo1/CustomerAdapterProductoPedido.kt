package sv.edu.ues.proyectopdm2024gt3grupo1

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class CustomerAdapterProductoPedido(nombre: ArrayList<String>, cantidad: ArrayList<Int>, precio: ArrayList<Double>, imagen: ArrayList<String>)
    : RecyclerView.Adapter<CustomerAdapterProductoPedido.ViewHolder>()
{
    private lateinit var NombreRecuperado: ArrayList<String>
    private lateinit var CantidadRecuperado: ArrayList<Int>
    private lateinit var PrecioRecuperado: ArrayList<Double>
    private lateinit var ImagenRecuperado: ArrayList<String>

    init {
        this.NombreRecuperado = nombre
        this.CantidadRecuperado = cantidad
        this.PrecioRecuperado = precio
        this.ImagenRecuperado = imagen
    }

    public class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var itemNombre: TextView = itemView.findViewById(R.id.txtNombrePedido)
        var itemCantidad: TextView = itemView.findViewById(R.id.txtCantidadPedido)
        var itemPrecio: TextView = itemView.findViewById(R.id.txtPrecioPedido)
        var itemImagen: ImageView = itemView.findViewById(R.id.txtImagenPedido)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CustomerAdapterProductoPedido.ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.detalle_item_pedido, parent, false)
        return CustomerAdapterProductoPedido.ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return NombreRecuperado.size
    }

    override fun onBindViewHolder(holder: CustomerAdapterProductoPedido.ViewHolder, position: Int) {
        holder.itemNombre.text=NombreRecuperado[position]
        holder.itemCantidad.text=CantidadRecuperado[position].toString()
        holder.itemPrecio.text = PrecioRecuperado[position].toString()
        val imageUrl = ImagenRecuperado[position]

        Glide.with(holder.itemView.context)
            .load(imageUrl)
            .error("https://www.shutterstock.com/image-vector/default-ui-image-placeholder-wireframes-600nw-1037719192.jpg")
            .into(holder.itemImagen)
    }

}