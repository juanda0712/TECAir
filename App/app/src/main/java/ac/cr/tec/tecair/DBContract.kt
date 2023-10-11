package ac.cr.tec.tecair

import android.provider.BaseColumns


/**
 * Contiene las definiciones de las tablas y columnas de la base de datos*/
object DBContract {
    class UserEntry : BaseColumns {
        companion object {
            val TABLE_NAME = "usuario"

            val COLUMN_NOMBRE = "nombre"
            val COLUMN_APELLIDO1 = "apellido1"
            val COLUMN_APELLIDO2 = "apellido2"
            val COLUMN_CEDULA = "cedula"
            val COLUMN_TIPO = "tipo"
            val COLUMN_PASSWORD = "password"
            val COLUMN_TEL = "tel"
            val COLUMN_CORREO = "correo"
        }
    }
}