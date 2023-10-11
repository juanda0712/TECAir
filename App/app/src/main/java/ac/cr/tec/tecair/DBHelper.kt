package ac.cr.tec.tecair

import ac.cr.tec.tecair.models.User
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteConstraintException
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import android.database.sqlite.SQLiteOpenHelper

//Funciona como un API
class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION){

    companion object {
        //Si se cambia el esquema de la DB se debe incrementar la version
        val DATABASE_VERSION = 1
        val DATABASE_NAME = "TECAirDB.db"
        val TABLE_NAME = DBContract.UserEntry.TABLE_NAME

        val Col_1 = DBContract.UserEntry.COLUMN_CEDULA
        val Col_2 = DBContract.UserEntry.COLUMN_NOMBRE
        val Col_3 = DBContract.UserEntry.COLUMN_APELLIDO1
        val Col_4 = DBContract.UserEntry.COLUMN_APELLIDO2
        val Col_5 = DBContract.UserEntry.COLUMN_TEL
        val Col_6 = DBContract.UserEntry.COLUMN_CORREO
        val Col_7 = DBContract.UserEntry.COLUMN_TIPO
        val Col_8 = DBContract.UserEntry.COLUMN_PASSWORD



        private val SQL_CREATE_DATABASE =
            "CREATE TABLE " + DBContract.UserEntry.TABLE_NAME + " (" +
                    DBContract.UserEntry.COLUMN_CEDULA + " INTEGER," +
                    DBContract.UserEntry.COLUMN_NOMBRE + " TEXT," +
                    DBContract.UserEntry.COLUMN_APELLIDO1 + " TEXT," +
                    DBContract.UserEntry.COLUMN_APELLIDO2 + " TEXT," +
                    DBContract.UserEntry.COLUMN_TEL + " TEXT," +
                    DBContract.UserEntry.COLUMN_CORREO + " TEXT," +
                    DBContract.UserEntry.COLUMN_TIPO + " TEXT," +
                    DBContract.UserEntry.COLUMN_PASSWORD + " TEXT)"

        private val SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS " + DBContract.UserEntry.TABLE_NAME
    }


    /* Crea la base de datos*/
    override fun onCreate(db: SQLiteDatabase) {

        db.execSQL(SQL_CREATE_DATABASE)
    }
    
    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        //Descarta la fecha y reinicia
        db.execSQL(SQL_DELETE_ENTRIES)
        onCreate(db)
    }

    override fun onDowngrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        super.onDowngrade(db, oldVersion, newVersion)
    }

    @Throws(SQLiteConstraintException::class)
    fun insertData(nombre: String, apellido1: String, apellido2: String): Long {
        //Establece el repositorio de datos en modo escritura
        val db = writableDatabase

        //Mapea los valores donde los nombres de columnas son las llaves
        val insertvalues = ContentValues()
        insertvalues.put(DBContract.UserEntry.COLUMN_NOMBRE, nombre)
        insertvalues.put(DBContract.UserEntry.COLUMN_APELLIDO1, apellido1)
        insertvalues.put(DBContract.UserEntry.COLUMN_APELLIDO2, apellido2)


        //Inserta la nueva fila retornando la llave primaria de la nueva fila
        val action = db.insert(DBContract.UserEntry.TABLE_NAME, null, insertvalues)
        db.close() //cierra la conexion con la DB
        return action
    }

    /**
     * Metodo que crea el record del usuario
     *
     * @param user
     */
    fun addUser(user: User) {
        val db = this.writableDatabase
        val insertvalues = ContentValues()
        insertvalues.put(DBContract.UserEntry.COLUMN_NOMBRE, user.nombre)
        insertvalues.put(DBContract.UserEntry.COLUMN_APELLIDO1, user.apellido1)
        insertvalues.put(DBContract.UserEntry.COLUMN_APELLIDO2, user.apellido2)
        insertvalues.put(DBContract.UserEntry.COLUMN_CEDULA, user.cedula)
        insertvalues.put(DBContract.UserEntry.COLUMN_CORREO, user.correo)
        insertvalues.put(DBContract.UserEntry.COLUMN_PASSWORD, user.password)
        insertvalues.put(DBContract.UserEntry.COLUMN_TEL, user.tel)
        insertvalues.put(DBContract.UserEntry.COLUMN_TIPO, user.tipo)
        // insertar fila
        db.insert(TABLE_NAME, null, insertvalues)
        db.close()
    }


    /**
     * Metodo que elimina una fila de la tabla de usuarios
     * basándose en la cédula
     *
     * @param user
     */
    @Throws(SQLiteConstraintException::class)
    fun deleteData(id: String): Int {
        //Establece el repositorio de datos en modo escritura
        val db = writableDatabase
        // Define el "where" de la consulata
        val selection = DBContract.UserEntry.COLUMN_CEDULA + " LIKE ?"
        //Especifica los argumentos en el orden del placeholder
        val selectionArgs = arrayOf(id)
        // Directiva de SQL que retorna el numero de filas borradas
        val action = db.delete(DBContract.UserEntry.TABLE_NAME, selection, selectionArgs)
        db.close()
        return action
    }

    /**Método q lee todos los datos de la tabla de usuarios y
     * los almacena en un ArrayList*/
    fun readAllData(): ArrayList<User> {
        val dBdata = ArrayList<User>()
        val db = writableDatabase
        var cursor: Cursor? = null
        try {
            cursor = db.rawQuery("select * from " + DBContract.UserEntry.TABLE_NAME, null)
        } catch (e: SQLiteException) {
            db.execSQL(SQL_CREATE_DATABASE)
            return ArrayList()
        }

        var cedula: String
        var nombre: String
        var apellido1: String
        var apellido2: String
        var tel: String
        var correo: String
        var tipo: String
        var password: String

        if (cursor!!.moveToFirst()) {
            while (cursor.isAfterLast == false) {
                nombre = cursor.getString(cursor.getColumnIndexOrThrow(DBContract.UserEntry.COLUMN_NOMBRE))
                apellido1 = cursor.getString(cursor.getColumnIndexOrThrow(DBContract.UserEntry.COLUMN_APELLIDO1))
                apellido2 = cursor.getString(cursor.getColumnIndexOrThrow(DBContract.UserEntry.COLUMN_APELLIDO2))
                cedula = cursor.getString(cursor.getColumnIndexOrThrow(DBContract.UserEntry.COLUMN_CEDULA))
                tipo = cursor.getString(cursor.getColumnIndexOrThrow(DBContract.UserEntry.COLUMN_TIPO))
                password = cursor.getString(cursor.getColumnIndexOrThrow(DBContract.UserEntry.COLUMN_PASSWORD))
                tel = cursor.getString(cursor.getColumnIndexOrThrow(DBContract.UserEntry.COLUMN_TEL))
                correo = cursor.getString(cursor.getColumnIndexOrThrow(DBContract.UserEntry.COLUMN_CORREO))

                dBdata.add(User(nombre, apellido1, apellido2, cedula, tipo, password, tel, correo))
                cursor.moveToNext()
            }
        }
        return dBdata
    }

    /**
     * Verifica si un usuario con un correo electrónico dado existe
     * en la base de datos
     * */
    fun checkUser(email: String): Boolean {
        //Arreglo de columnas
        val columns = arrayOf(Col_6)
        val db = this.readableDatabase
        // selection criteria
        val selection = "$Col_8 = ?"
        // selection argument
        val selectionArgs = arrayOf(email)
        // query user table with condition

        val cursor = db.query(
            TABLE_NAME, //Table to query
            columns,        //columns to return
            selection,      //columns for the WHERE clause
            selectionArgs,  //The values for the WHERE clause
            null,  //group the rows
            null,   //filter by row groups
            null)  //The sort order
        val cursorCount = cursor.count
        cursor.close()
        db.close()
        if (cursorCount > 0) {
            return true
        }
        return false
    }

    /**
     * Verifica si un usuario con un correo electrónico y una contraseña
     * dados existe en la base de datos
     * */
    fun checkUser(email: String, password: String): Boolean {
        // array of columns to fetch
        val columns = arrayOf(Col_1)
        val db = this.readableDatabase
        // selection criteria
        val selection = "$Col_6 = ? AND $Col_8 = ?"
        // selection arguments
        val selectionArgs = arrayOf(email, password)
        // query user table with conditions

        val cursor = db.query(
            TABLE_NAME, //Table to query
            columns, //columns to return
            selection, //columns for the WHERE clause
            selectionArgs, //The values for the WHERE clause
            null,  //group the rows
            null, //filter by row groups
            null) //The sort order
        val cursorCount = cursor.count
        cursor.close()
        db.close()
        if (cursorCount > 0)
            return true
        return false
    }
}