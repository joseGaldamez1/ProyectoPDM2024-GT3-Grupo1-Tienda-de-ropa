package sv.edu.ues.proyectopdm2024gt3grupo1

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView

class CustomerAdapterPedidos(Pedido:ArrayList<String>, idCliente:ArrayList<String>, estado:ArrayList<String>): RecyclerView.Adapter<CustomerAdapterPedidos.ViewHolder>()
{
    private lateinit var NumeroRecuperado: ArrayList<String>
    private lateinit var ClienteRecuperado: ArrayList<String>
    private lateinit var EstadoRecuperado: ArrayList<String>

    init {
        this.NumeroRecuperado = Pedido
        this.ClienteRecuperado = idCliente
        this.EstadoRecuperado = estado
    }

    public class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var itemNumero: TextView = itemView.findViewById(R.id.txtNumeroPedido)
        var itemCliente: TextView = itemView.findViewById(R.id.txtUsuarioPedido)
        var itemEstado: TextView = itemView.findViewById(R.id.txtEstado)
        //prueba de accion en cardView
        val  botonVerDetalles: Button = itemView.findViewById(R.id.btnVerDetalle)
        init {

            botonVerDetalles.setOnClickListener(){
                val intent = Intent(itemView.context, DatosPedidoActivity::class.java).apply {
                    putExtra("numeroPedido", itemNumero.text.toString())
                }
                itemView.context.startActivity(intent)
                 }



        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomerAdapterPedidos.ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.activity_detalle_pedidos, parent, false)
        return CustomerAdapterPedidos.ViewHolder(v)
    }

    override fun onBindViewHolder(holder: CustomerAdapterPedidos.ViewHolder, position: Int) {
        holder.itemNumero.text=NumeroRecuperado[position]
        holder.itemCliente.text=ClienteRecuperado[position]
        holder.itemEstado.text = EstadoRecuperado[position]
    }

    override fun getItemCount(): Int {
        return ClienteRecuperado.size
    }
}