package sv.edu.ues.proyectopdm2024gt3grupo1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import sv.edu.ues.proyectopdm2024gt3grupo1.data.DataHelper

class RegistrarseActivity : AppCompatActivity() {

    private lateinit var dbHelper: DataHelper
    private lateinit var SpinnerDepartamentos: Spinner
    private lateinit var SpinnerMunicipios: Spinner
    private lateinit var SpinnerDistritos: Spinner
    private lateinit var distrito: String

    //Controles del formulario
    private lateinit var txtNombres: EditText
    private lateinit var txtApellidos: EditText
    private lateinit var txtUsuario: EditText
    private lateinit var txtContrasena: EditText
    private lateinit var txtTelefono: EditText
    private lateinit var txtDireccion: EditText
    private lateinit var txtRol: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registrarse)

        SpinnerDepartamentos = findViewById(R.id.spinnerDepartamento)
        SpinnerMunicipios = findViewById(R.id.spinnerMunicipio)
        SpinnerDistritos = findViewById(R.id.spinnerDistrito)
        dbHelper = DataHelper(this)

        //lamar a la funcion para llenar los departamentos
            llenarSpinnerDepartamentos()

        //Toolbar
        //toolbar
        val toolbar: Toolbar = findViewById(R.id.toolbarRegistrarse)
        setSupportActionBar(toolbar)
        getSupportActionBar()?.setDisplayHomeAsUpEnabled(true)
        getSupportActionBar()?.setDisplayShowHomeEnabled(true)


        SpinnerDepartamentos.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                val departamento = parent.getItemAtPosition(position).toString()
                if (departamento=="Seleccione") {
                    llenarSpinnerMunicipios("Seleccione")
                }else{
                    llenarSpinnerMunicipios(departamento)
                }
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }

        SpinnerMunicipios.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                val municipios = parent.getItemAtPosition(position).toString()
                if (municipios=="Seleccione") {
                    llenarSpinnerDistritos("Seleccione")
                }else{
                    llenarSpinnerDistritos(municipios)
                }
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }

        SpinnerDistritos.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
               //Variable para guardar en el usuario
               distrito = parent.getItemAtPosition(position).toString()
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }

        //Boton registrarse
        val btnRegistrase: Button = findViewById(R.id.btnRegistarse)
        btnRegistrase.setOnClickListener(){
            GuardarUsuario()
        }

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        return super.onCreateOptionsMenu(menu)
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            android.R.id.home->finish()
        }
        return super.onOptionsItemSelected(item)
    }


    //FUNCION PARA LLENAR EL SPINNER DEPARTAMENTOS
    private fun llenarSpinnerDepartamentos() {
        val nombreDepartamentos: ArrayList<Departamentos> = dbHelper.verDepartamentos()
        val nombre: ArrayList<String> = ArrayList()
        nombre.add("Seleccione")
        for (departamentos in nombreDepartamentos) {
            nombre.add(departamentos.nombre)
        }
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, nombre)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        SpinnerDepartamentos.adapter = adapter
    }

    //FUNCION PARA LLENAR EL SPINNER MUNICIPIOS
    private fun llenarSpinnerMunicipios(nombre: String) {
        val nombreMunicipios: ArrayList<Municipios> = dbHelper.verMunicipios(nombre)
        val nombres: ArrayList<String> = ArrayList()
        nombres.clear()
        nombres.add("Seleccione")
        for (municipios in nombreMunicipios) {
            nombres.add(municipios.nombre)
        }
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, nombres)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        SpinnerMunicipios.adapter = adapter
    }

    //FUNCION PARA LLENAR EL SPINNER DISTRITOS
    private fun llenarSpinnerDistritos(nombre: String) {
        val nombreDistritos: ArrayList<Distritos> = dbHelper.verDistritros(nombre)
        val nombres: ArrayList<String> = ArrayList()
        nombres.clear()
        nombres.add("Seleccione")
        for (distritos in nombreDistritos) {
            nombres.add(distritos.nombre)
        }
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, nombres)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        SpinnerDistritos.adapter = adapter
    }

    fun GuardarUsuario(){
        txtNombres= findViewById(R.id.txtNombreRegistrarse)
        txtApellidos= findViewById(R.id.txtApellidoRegistrarse)
        txtUsuario= findViewById(R.id.txtUsuarioRegistrarse)
        txtContrasena= findViewById(R.id.txtPasswordRegistrarse)
        txtTelefono= findViewById(R.id.txtTelefonoRegistrarse)
        txtDireccion= findViewById(R.id.txtDireccionRegistrarse)
        txtRol = "2"
        val idDistrito = dbHelper.ObtenerIdDistrito(distrito)


        val idResultado = dbHelper.AddUsuario(txtNombres.text.toString(), txtApellidos.text.toString(), txtUsuario.text.toString(), txtContrasena.text.toString(), txtTelefono.text.toString(), idDistrito, txtDireccion.text.toString(), txtRol.toString().toInt())
        if(idResultado==-1.toLong()){
            Toast.makeText(this, "Hubo un error", Toast.LENGTH_SHORT).show()
        }else{
            Toast.makeText(this, "Registrado con exito!!", Toast.LENGTH_SHORT).show()
        }
        LimpiarFormulario()
    }

    private fun LimpiarFormulario() {
        txtNombres = findViewById(R.id.txtNombreRegistrarse)
        txtApellidos = findViewById(R.id.txtApellidoRegistrarse)
        txtUsuario = findViewById(R.id.txtUsuarioRegistrarse)
        txtContrasena = findViewById(R.id.txtPasswordRegistrarse)
        txtTelefono = findViewById(R.id.txtTelefonoRegistrarse)
        txtDireccion = findViewById(R.id.txtDireccionRegistrarse)

        txtNombres.setText("")
        txtApellidos.setText("")
        txtUsuario.setText("")
        txtContrasena.setText("")
        txtTelefono.setText("")
        txtDireccion.setText("")
        SpinnerDepartamentos.setSelection(0)
        SpinnerMunicipios.setSelection(0)
        SpinnerDistritos.setSelection(0)
    }

}