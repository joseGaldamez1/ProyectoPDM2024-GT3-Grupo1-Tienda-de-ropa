package sv.edu.ues.proyectopdm2024gt3grupo1

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.isVisible
import sv.edu.ues.proyectopdm2024gt3grupo1.data.DataHelper


class PerfilFragment : Fragment() {

    private lateinit var btnAdmin: Button
    private lateinit var dbHelper: DataHelper
    private lateinit var  nombre: TextView
    private lateinit var  telefono: TextView
    private lateinit var  direccion: TextView
    private lateinit var  nombreUsuario: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_perfil, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        dbHelper = DataHelper(requireContext())
        nombre = view.findViewById(R.id.txtNombrePerfil)
        telefono = view.findViewById(R.id.txtTelefonoPerfil)
        direccion = view.findViewById(R.id.txtDireccionPerfil)
        nombreUsuario = view.findViewById(R.id.txtUsuarioPerfil)
        CargarPerfil()

        val cerrar: Button = view.findViewById(R.id.btnCerrarSesion)
        cerrar.setOnClickListener(){
            dbHelper.EliminarSesion("Activa")
            val intent = Intent(requireContext(), MainActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
            startActivity(intent)


        }
    }

    private fun CargarPerfil() {

        if(nombre.equals("")){
            Toast.makeText(null, "Registrese o inicie sesion", Toast.LENGTH_SHORT).show()
        }else{
            //Obtener el id del usuario para cargarlo en el perfil
            val usuario = obtenerUsuario(requireContext()).toString()
            val datos =  dbHelper.ConsultarUsuario(usuario)
            val nombresApellidos = datos[0].nombres + " " + datos[0].apellidos
            val dire = datos[0].direccion + ", "+datos[0].distrito + ", " + datos[0].municipio+ ", " + datos[0].departamento
            nombre.setText(nombresApellidos)
            telefono.setText(datos[0].telefono)
            direccion.setText(dire)
            nombreUsuario.setText(datos[0].usuario)

        }
    }

    // Método para obtener el usuario de SharedPreferences
    fun obtenerUsuario(context: Context): String? {
        val nomUser = context.getSharedPreferences("MyAppPrefs", Context.MODE_PRIVATE)
        return nomUser.getString("USERNAME_KEY", null)
    }

}
