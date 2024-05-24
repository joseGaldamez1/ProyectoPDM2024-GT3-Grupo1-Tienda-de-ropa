package sv.edu.ues.proyectopdm2024gt3grupo1

data class Productos(
    val id:Int,
    val nombre:String,
    val categoria:String,
    val cantidadS:Int,
    val cantidadM:Int,
    val cantidadL:Int,
    val precioCompra:Double,
    val precioVenta:Double,
    val descripcion:String,
    val imagen1: String
)

data class Pedidos(
    val numeroPedido : Int,
    val idUsuario: Int,
    val idProducto: Int,
    val nombreProd:String,
    val talla: String,
    val cantidad: Int,
    val precioVenta: Double,
    val imagen: String,
    val estado: String
)

data class Usuario(
    val nombres : String,
    val apellidos: String,
    val usuario: String,
    val contrasena :String,
    val telefono: String,
    val distrito: Int,
    val direccion: Double,
    val rol: Int
)

data class Autenticacion(
    val usuario: String,
    val Contrasena: String,
    val rol: String
)