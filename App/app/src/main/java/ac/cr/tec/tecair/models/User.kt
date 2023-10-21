package ac.cr.tec.tecair.models

/* Clase encargada de la información del usuario*/
data class User(
    var fullName:String, var userID:Int,
    var password:String, var phoneNumber:String,
    var email:String, var passengerID: Int?
)