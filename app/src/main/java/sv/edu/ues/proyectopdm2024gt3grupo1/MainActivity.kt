package sv.edu.ues.proyectopdm2024gt3grupo1


import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import sv.edu.ues.proyectopdm2024gt3grupo1.data.DataHelper
import sv.edu.ues.proyectopdm2024gt3grupo1.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var dbHelper: DataHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val btnRegistrase: Button = findViewById(R.id.btnRegistarse)
        btnRegistrase.setOnClickListener(){
            val intent = Intent(this, RegistrarseActivity::class.java)
            startActivity(intent)

        }

        dbHelper = DataHelper(this)

        val btnIniciarSesion: Button = findViewById(R.id.btnIniciarSesion)
        btnIniciarSesion.setOnClickListener(){
            Autenticar()
        }

    }

    fun Autenticar() {
        val txtUsuario: EditText = findViewById(R.id.txtUser)
        val txtPassword: EditText = findViewById(R.id.txtPassword)

        val usuario = txtUsuario.text.toString()
        val password = txtPassword.text.toString()

        if (usuario.isEmpty()) {
            Toast.makeText(this, "Ingrese su usuario", Toast.LENGTH_SHORT).show()
            return
        }

        if (password.isEmpty()) {
            Toast.makeText(this, "Ingrese la contraseña", Toast.LENGTH_SHORT).show()
            return
        }

        val registro = dbHelper.IniciarSesion(usuario)

        if (registro.isNotEmpty()) {
            val userBD = registro[0].usuario
            val passBD = registro[0].Contrasena
            val rolDB = registro[0].rol

            if (usuario == userBD && password == passBD) {
                Toast.makeText(this, "Bienvenido $usuario: $rolDB", Toast.LENGTH_SHORT).show()
                if(rolDB.equals("Usuario")){
                    setContentView(R.layout.activity_main)

                    val navView: BottomNavigationView = findViewById(R.id.nav_view)
                    val navController = findNavController(R.id.nav_host_fragment_activity_main)

                    navView.setupWithNavController(navController)
                }else{
                    val intent = Intent(this, pruebaControlActivity::class.java)
                    startActivity(intent)
                }
            }else {
                Toast.makeText(this, "Usuario o contraseña incorrectos", Toast.LENGTH_SHORT).show()
            }
        } else {
            Toast.makeText(this, "Usuario no encontrado", Toast.LENGTH_SHORT).show()
        }
    }

}

