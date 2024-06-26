package sv.edu.ues.proyectopdm2024gt3grupo1


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import sv.edu.ues.proyectopdm2024gt3grupo1.data.DataHelper

class pruebaControlActivity : AppCompatActivity() {
    private lateinit var dbHelper: DataHelper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_prueba_control)

        dbHelper  = DataHelper(this)

        val btnIniciar : ImageButton = findViewById(R.id.btnVer)
        val btnIniciarCategorias: ImageButton = findViewById(R.id.btnCategorias)
        val btnIniciarPedidos: ImageButton = findViewById(R.id.btnPedidos)
        val btnIniciarReportes: ImageButton = findViewById(R.id.btnReportes)
        val btnCliente: Button = findViewById(R.id.btnIngresarModoCliente)

        btnIniciar.setOnClickListener(){
            val intent = Intent(this, InventarioActivity::class.java)
            startActivity(intent)
        }

        btnIniciarCategorias.setOnClickListener(){
            val intent = Intent(this, AdministrarCategoriasActivity::class.java)
            startActivity(intent)
        }

        btnIniciarPedidos.setOnClickListener(){
            val intent = Intent(this, PedidosActivity::class.java)
            startActivity(intent)
        }

        btnIniciarReportes.setOnClickListener(){
            val intent = Intent(this, ReporteActivity::class.java)
            startActivity(intent)
        }


        btnCliente.setOnClickListener(){
            setContentView(R.layout.activity_main)

            val navView: BottomNavigationView = findViewById(R.id.nav_view)
            val navController = findNavController(R.id.nav_host_fragment_activity_main)
            navView.setupWithNavController(navController)
        }
        val alert = AlertDialog.Builder(this)
        val btnCargarDatos: Button = findViewById(R.id.btnCargarDatos)
        btnCargarDatos.setOnClickListener(){
            val categ = dbHelper.verCategorias()
            var verificar = false

            for (categoria in categ) {
                if (categoria.nombre == "Ropa para dormir") {
                    verificar = true
                    break
                }
            }
            alert.setMessage("¿Quiere cargar los datos iniciales?")
                .setCancelable(false)
                .setPositiveButton("Si"){dialog, id ->
                    if(verificar==true){
                        Toast.makeText(this, "Los datos iniciales solo se pueden cargar una vez", Toast.LENGTH_SHORT).show()
                    }else{
                            dbHelper.DatosProductosIniciales()
                            dbHelper.DatosCategoriasIniciales()
                            dbHelper.DatosPedidoInicial()
                        Toast.makeText(this, "Se cargaron los datos con éxito", Toast.LENGTH_SHORT).show()
                    }
                }
                .setNegativeButton("No") { dialog, id ->
                    dialog.dismiss()
                }
                .create()
                .show()

        }

    }
}