package sv.edu.ues.proyectopdm2024gt3grupo1


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide


class CustomerAdapter(
    idRecuperado:ArrayList<Int>,
    nombreRecuperado:ArrayList<String>,
    categoriaRecuperado:ArrayList<String>,
    cantidadRecuperado:ArrayList<Int>,
    precioVentaRecuperado:ArrayList<Double>,
    imagenRecuperado1: ArrayList<String>,

):RecyclerView.Adapter<CustomerAdapter.ViewHolder>(){

    private lateinit var IdRecuperado:ArrayList<Int>
    private lateinit var NombreRecuperado:ArrayList<String>
    private lateinit var CategoriaRecuperado:ArrayList<String>
    private lateinit var CantidadRecuperado:ArrayList<Int>
    private lateinit var PrecioCompraRecuperado:ArrayList<Double>
    private lateinit var PrecioVentaRecuperado:ArrayList<Double>
    private lateinit var DescripcionRecuperado:ArrayList<String>
    private lateinit var ImagenRecuperado1:ArrayList<String>

    init {
        this.IdRecuperado=idRecuperado
        this.NombreRecuperado=nombreRecuperado
        this.CategoriaRecuperado=categoriaRecuperado
        this.CantidadRecuperado=cantidadRecuperado
        this.PrecioVentaRecuperado=precioVentaRecuperado
        this.ImagenRecuperado1=imagenRecuperado1

    }

    public class ViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){

        var itemId: TextView =itemView.findViewById(R.id.txtIdProducto)
        var itemNombre: TextView =itemView.findViewById(R.id.txtNombreProducto)
        var itemCategoria: TextView =itemView.findViewById(R.id.txtCategoriaProducto)
        var itemCantidad: TextView =itemView.findViewById(R.id.txtCantidadProductos)
        var itemPrecioVenta: TextView =itemView.findViewById(R.id.txtPrecioProductos)
        var itemImagen1: ImageView = itemView.findViewById(R.id.txtVerImagen)


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomerAdapter.ViewHolder {
        val v= LayoutInflater.from(parent.context).inflate(R.layout.detalle_inventario, parent,false)
        return ViewHolder(v)


    }

    override fun onBindViewHolder(holder: CustomerAdapter.ViewHolder, position: Int) {
        holder.itemId.text=IdRecuperado[position].toString()
        holder.itemNombre.text=NombreRecuperado[position]
        holder.itemCategoria.text=CategoriaRecuperado[position].toString()
        holder.itemCantidad.text=CantidadRecuperado[position].toString()
        holder.itemPrecioVenta.text=PrecioVentaRecuperado[position].toString()


        val imageUrl = ImagenRecuperado1[position]

        Glide.with(holder.itemView.context)
            .load(imageUrl)
            .error("https://www.shutterstock.com/image-vector/default-ui-image-placeholder-wireframes-600nw-1037719192.jpg")
            .into(holder.itemImagen1)
    }

    override fun getItemCount(): Int {
        return IdRecuperado.size
    }
}