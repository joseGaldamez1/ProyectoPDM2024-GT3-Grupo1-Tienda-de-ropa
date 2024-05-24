package sv.edu.ues.proyectopdm2024gt3grupo1


data class Categorias(val id: Int, val nombre: String) {
    override fun toString(): String {
        return nombre
    }
}

data class Departamentos(val id: Int, val nombre: String) {
    override fun toString(): String {
        return nombre
    }
}

data class Municipios(val id: Int, val nombre: String) {
    override fun toString(): String {
        return nombre
    }
}

data class Distritos(val id: Int, val nombre: String) {
    override fun toString(): String {
        return nombre
    }
}