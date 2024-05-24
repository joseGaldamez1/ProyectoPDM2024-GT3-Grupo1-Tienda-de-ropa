package sv.edu.ues.proyectopdm2024gt3grupo1

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import sv.edu.ues.proyectopdm2024gt3grupo1.data.DataHelper


class CustomAdapterHome(
    imagenRecuperado:ArrayList<String>, nombreRecuperado:ArrayList<String>, precioRecuperado:ArrayList<String>
): RecyclerView.Adapter<CustomAdapterHome.ViewHolder>() {

    private lateinit var ImagenRecuperado: ArrayList<String>
    private lateinit var NombreRecuperado: ArrayList<String>
    private lateinit var PrecioRecuperado: ArrayList<String>


    init {
        this.ImagenRecuperado = imagenRecuperado
        this.NombreRecuperado = nombreRecuperado
        this.PrecioRecuperado = precioRecuperado
    }

    public class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        //prueba de accion en cardView
        val  boton: ImageButton = itemView.findViewById(R.id.btnCarrito)
        val  imagen: ImageView = itemView.findViewById(R.id.txtImagenHome)

        var itemImagen: ImageView = itemView.findViewById(R.id.txtImagenHome)
        var itemNombre: TextView = itemView.findViewById(R.id.txtNombreHome)
        var itemPrecio: TextView = itemView.findViewById(R.id.txtPrecioHome)




        init {

            boton.setOnClickListener(){
                Toast.makeText(itemView.context, "Seleccione la imagen para ver detalles de: "+ itemNombre.text.toString(), Toast.LENGTH_SHORT).show()
            }

            imagen.setOnClickListener(){
                val intent = Intent(itemView.context, DetalleProductoActivity::class.java).apply {
                    putExtra("nombreProducto", itemNombre.text.toString())
                }
                itemView.context.startActivity(intent)
            }

        }
    }


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CustomAdapterHome.ViewHolder {

        val v = LayoutInflater.from(parent.context).inflate(R.layout.activity_detalle_home, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
       holder.itemNombre.text=NombreRecuperado[position]
       holder.itemPrecio.text=PrecioRecuperado[position]

        val imageUrl = ImagenRecuperado[position]

        Glide.with(holder.itemView.context)
            .load(imageUrl)
            .error("https://www.shutterstock.com/image-vector/default-ui-image-placeholder-wireframes-600nw-1037719192.jpg")
            .into(holder.itemImagen)

    }

    override fun getItemCount(): Int {
        return ImagenRecuperado.size
    }


}