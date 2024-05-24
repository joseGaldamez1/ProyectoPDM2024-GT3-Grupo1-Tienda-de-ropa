package sv.edu.ues.proyectopdm2024gt3grupo1


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.Toast
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView

class pruebaControlActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_prueba_control)

        val btnIniciar : ImageButton = findViewById(R.id.btnVer)
        val btnIniciarCategorias: ImageButton = findViewById(R.id.btnCategorias)
        val btnIniciarPedidos: ImageButton = findViewById(R.id.btnPedidos)
        val btnIniciarReportes: ImageButton = findViewById(R.id.btnReportes)
        val btnCliente: Button = findViewById(R.id.btnIngresarModoCliente)

        btnIniciar.setOnClickListener(){
            Toast.makeText(this, "Inventario", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, InventarioActivity::class.java)
            startActivity(intent)
        }

        btnIniciarCategorias.setOnClickListener(){
            Toast.makeText(this, "Categorías", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, AdministrarCategoriasActivity::class.java)
            startActivity(intent)
        }

        btnIniciarPedidos.setOnClickListener(){
            Toast.makeText(this, "Pedidos", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, PedidosActivity::class.java)
            startActivity(intent)
        }

        btnIniciarReportes.setOnClickListener(){
            Toast.makeText(this, "Reportes", Toast.LENGTH_SHORT).show()
        }

        btnCliente.setOnClickListener(){
            setContentView(R.layout.activity_main)

            val navView: BottomNavigationView = findViewById(R.id.nav_view)
            val navController = findNavController(R.id.nav_host_fragment_activity_main)

            navView.setupWithNavController(navController)
        }

    }


}