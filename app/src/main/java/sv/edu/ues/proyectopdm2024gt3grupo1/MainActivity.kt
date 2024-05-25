package sv.edu.ues.proyectopdm2024gt3grupo1


import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
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

        dbHelper = DataHelper(this)

        val btnRegistrase: Button = findViewById(R.id.btnRegistarse)
        btnRegistrase.setOnClickListener(){
            dbHelper.AddSesion("Activa")
            val intent = Intent(this, RegistrarseActivity::class.java)
            startActivity(intent)

        }

        val btnIniciarSesion: Button = findViewById(R.id.btnIniciarSesion)
        btnIniciarSesion.setOnClickListener(){
           val result= dbHelper.AddSesion("Activa")
         Autenticar()
        }

    }

    fun Autenticar() {
        val txtUsuario: EditText = findViewById(R.id.txtUser)
        val txtPassword: EditText = findViewById(R.id.txtPassword)



        val idUsuario = dbHelper.ObtenerIdUsuario(txtUsuario.text.toString())
        guardarUsuario(this, idUsuario.toString())

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
        val usuar = dbHelper.ConsultarUsuario(idUsuario.toString())
        val nombre = usuar[0].nombres

        if (registro.isNotEmpty()) {
            val userBD = registro[0].usuario
            val passBD = registro[0].Contrasena
            val rolDB = registro[0].rol


            if (usuario == userBD && password == passBD) {
                Toast.makeText(this, "Bienvenido $nombre!!", Toast.LENGTH_SHORT).show()
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

    // Método para guardar el usuario en SharedPreferences
    fun guardarUsuario(context: Context,nombreUsuario: String) {
        val user  = context.getSharedPreferences("MyAppPrefs", Context.MODE_PRIVATE)
        val editor = user.edit()
        editor.putString("USERNAME_KEY", nombreUsuario)
        editor.apply()
    }


}

