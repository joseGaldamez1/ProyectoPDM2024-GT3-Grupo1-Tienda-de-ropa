package sv.edu.ues.proyectopdm2024gt3grupo1

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import sv.edu.ues.proyectopdm2024gt3grupo1.data.DataHelper

class CustomerAdapterCarrito(
    nombreRecuperado:ArrayList<String>, imagenRecuperado:ArrayList<String>, precioRecuperado:ArrayList<String>, tallaRecuperado:ArrayList<String>,
    cantidadRecuperado:ArrayList<String>
): RecyclerView.Adapter<CustomerAdapterCarrito.ViewHolder>()  {

    private lateinit var NombreRecuperado: ArrayList<String>
    private lateinit var ImagenRecuperado: ArrayList<String>
    private lateinit var PrecioRecuperado: ArrayList<String>
    private lateinit var TallaRecuperado: ArrayList<String>
    private lateinit var CantidadRecuperado: ArrayList<String>

    init {
        this.NombreRecuperado = nombreRecuperado
        this.ImagenRecuperado = imagenRecuperado
        this.PrecioRecuperado = precioRecuperado
        this.TallaRecuperado = tallaRecuperado
        this.CantidadRecuperado = cantidadRecuperado
    }


    public class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var itemNombre: TextView = itemView.findViewById(R.id.txtNombreProductoCarr)
        var itemImagen: ImageView = itemView.findViewById(R.id.txtVerImagenCarr)
        var itemPrecio: TextView = itemView.findViewById(R.id.txtPrecioProductosCarr)
        var itemTalla: TextView = itemView.findViewById(R.id.spinnerCarritoCarr)
        var itemCantidad: TextView = itemView.findViewById(R.id.txtCantidadCarr)


        //prueba de click
        val eliminar: ImageButton = itemView.findViewById(R.id.btnEliminarCarrito)
        init {
            eliminar.setOnClickListener() {
                val dbHelper = DataHelper(itemView.context)
                val idProd = dbHelper.ConsultarIdProducto(itemNombre.text.toString())
                val usuar = dbHelper.obtenerUsuario(itemView.context)
                val estado = "Carrito"
                val idRes = dbHelper.EliminarCarrito(idProd ,usuar.toString().toInt(), estado)
                if (idRes == 0) {
                    Toast.makeText(itemView.context, "No se pudo eliminar", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(itemView.context, "Se eliminó el producto", Toast.LENGTH_SHORT).show()
                    itemNombre.setText("")
                    itemCantidad.setText("")
                    itemTalla.setText("")
                    itemImagen.setImageDrawable(null)
                    itemPrecio.setText("")
                }
            }
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomerAdapterCarrito.ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.detalle_item_carrito, parent, false)
        return CustomerAdapterCarrito.ViewHolder(v)
    }

    override fun onBindViewHolder(holder: CustomerAdapterCarrito.ViewHolder, position: Int) {
          holder.itemNombre.text=NombreRecuperado[position]
        val imageUrl = ImagenRecuperado[position]

        Glide.with(holder.itemView.context)
            .load(imageUrl)
            .error("https://www.shutterstock.com/image-vector/default-ui-image-placeholder-wireframes-600nw-1037719192.jpg")
            .into(holder.itemImagen)
        holder.itemPrecio.text=PrecioRecuperado[position]
        holder.itemTalla.text= TallaRecuperado[position]
        holder.itemCantidad.text=CantidadRecuperado[position]
    }

    override fun getItemCount(): Int {
        return ImagenRecuperado.size
    }

}

