package ac.cr.tec.tecair.models

/* Clase encargada de la información del usuario*/
data class User(var nombre:String, var apellido1:String,
                var apellido2:String,var cedula:String,
                var tipo:String, var password:String,
                var tel:String, var correo:String) {
}