package ac.cr.tec.tecair

import ac.cr.tec.tecair.models.*
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteConstraintException
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import android.database.sqlite.SQLiteOpenHelper

//Funciona como un API para la creación de la DB local
class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        //Si se cambia el esquema de la DB se debe incrementar la version
        val DATABASE_VERSION = 1
        val DATABASE_NAME = "TECAirDB.db"

        /**UserTable**/
        val USERTABLE_NAME = DBContract.UserEntry.TABLE_NAME
        val COL_USERID = DBContract.UserEntry.COLUMN_USERID
        val COL_PASSID = DBContract.UserEntry.COLUMN_PASSENGERID
        val COL_NAME = DBContract.UserEntry.COLUMN_NAME
        val COL_PHONE = DBContract.UserEntry.COLUMN_PHONE
        val COL_PASSWORD = DBContract.UserEntry.COLUMN_PASSWORD
        val COL_EMAIL = DBContract.UserEntry.COLUMN_EMAIL

        private val SQL_CREATE_USERTABLE =
            "CREATE TABLE $USERTABLE_NAME (" +
                    "$COL_USERID INTEGER PRIMARY KEY," +
                    "$COL_PASSID INTEGER," +
                    "$COL_NAME STRING," +
                    "$COL_PHONE STRING," +
                    "$COL_EMAIL STRING," +
                    "$COL_PASSWORD STRING," +
                    "FOREIGN KEY ($COL_PASSID) REFERENCES ${DBContract.PassengerEntry.TABLE_NAME}(${DBContract.PassengerEntry.COLUMN_PASSENGERID})" +
                    ")"


        /**PassengerTable**/
        val PASSENGERTABLE_NAME = DBContract.PassengerEntry.TABLE_NAME
        val COL_PASSENGERID = DBContract.PassengerEntry.COLUMN_PASSENGERID
        val COL_PASSENGERNAME = DBContract.PassengerEntry.COLUMN_NAME
        val COL_PASSENGERPHONE = DBContract.PassengerEntry.COLUMN_PHONE

        private val SQL_CREATE_PASSENGERTABLE =
            "CREATE TABLE $PASSENGERTABLE_NAME (" +
                    "$COL_PASSENGERID INTEGER PRIMARY KEY," +
                    "$COL_PASSENGERNAME STRING," +
                    "$COL_PASSENGERPHONE STRING" +
                    ")"


        /**FlightTable**/
        val FLIGHTTABLE_NAME = DBContract.FlightEntry.TABLE_NAME
        val COL_FLIGHTNUMBER = DBContract.FlightEntry.COLUMN_FLIGHTNUMBER
        val COL_ORIGIN = DBContract.FlightEntry.COLUMN_ORIGIN
        val COL_DESTINATION = DBContract.FlightEntry.COLUMN_DESTINATION

        private val SQL_CREATE_FLIGHTTABLE =
            "CREATE TABLE $FLIGHTTABLE_NAME (" +
                    "$COL_FLIGHTNUMBER INTEGER PRIMARY KEY," +
                    "$COL_ORIGIN STRING," +
                    "$COL_DESTINATION STRING" +
                    ")"

        /**ReservationTable**/
        val RESERVATIONTABLE_NAME = DBContract.ReservationEntry.TABLE_NAME
        val COL_RESERVID = DBContract.ReservationEntry.COLUMN_RESERVID
        val COL_RESUSERID = DBContract.ReservationEntry.COLUMN_PASSENGERID
        val COL_RESEXECUTIONID = DBContract.ReservationEntry.COLUMN_EXECUTIONID

        private val SQL_CREATE_RESERVATIONTABLE =
            "CREATE TABLE $RESERVATIONTABLE_NAME (" +
                    "$COL_RESERVID INTEGER PRIMARY KEY," +
                    "$COL_RESUSERID INTEGER," +
                    "$COL_RESEXECUTIONID INTEGER" +
                    ")"

        /**AirportTable**/
        val AIRPORTTABLE_NAME = DBContract.AirportEntry.TABLE_NAME
        val COL_AIRPORTNAME = DBContract.AirportEntry.COLUMN_NAME
        val COL_CITY = DBContract.AirportEntry.COLUMN_CITY

        private val SQL_CREATE_AIRPORTTABLE =
            "CREATE TABLE $AIRPORTTABLE_NAME (" +
                    "$COL_AIRPORTNAME STRING PRIMARY KEY," +
                    "$COL_CITY STRING" +
                    ")"

        /**PromotionTable**/
        val PROMOTIONTABLE_NAME = DBContract.PromotionEntry.TABLE_NAME
        val COL_PROMONUMBER = DBContract.PromotionEntry.COLUMN_NUMBER
        val COL_PROMOEXECID = DBContract.PromotionEntry.COLUMN_EXECUTIONID
        val COL_PROMOPERIOD = DBContract.PromotionEntry.COLUMN_PERIOD
        val COL_PROMOPRICE = DBContract.PromotionEntry.COLUMN_PRICE

        private val SQL_CREATE_PROMOTIONTABLE =
            "CREATE TABLE $PROMOTIONTABLE_NAME (" +
                    "$COL_PROMONUMBER INTEGER PRIMARY KEY," +
                    "$COL_PROMOEXECID INTEGER," +
                    "$COL_PROMOPERIOD STRING," +
                    "$COL_PROMOPRICE STRING," +
                    "FOREIGN KEY ($COL_PROMOEXECID) REFERENCES ${DBContract.ExecutionEntry.TABLE_NAME}(${DBContract.ExecutionEntry.COLUMN_EXECUTIONID})" +
                    ")"


        /**StudentTable**/
        val STUDENTTABLE_NAME = DBContract.StudentEntry.TABLE_NAME
        val COL_UNICARD = DBContract.StudentEntry.COLUMN_UNICARD
        val COL_STUDENTUSERID = DBContract.StudentEntry.COLUMN_USERID
        val COL_UNINAME = DBContract.StudentEntry.COLUMN_UNINAME
        val COL_MILES = DBContract.StudentEntry.COLUMN_MILES

        private val SQL_CREATE_STUDENTTABLE =
            "CREATE TABLE $STUDENTTABLE_NAME (" +
                    "$COL_UNICARD INTEGER PRIMARY KEY," +
                    "$COL_STUDENTUSERID INTEGER," +
                    "$COL_UNINAME TEXT," +
                    "$COL_MILES INTEGER" +
                    ")"

        /**SeatTable**/
        val SEATTABLE_NAME = DBContract.SeatEntry.TABLE_NAME
        val COL_SEATNUMBER = DBContract.SeatEntry.COLUMN_NUMBER
        val COL_SEATEXECUTIONID = DBContract.SeatEntry.COLUMN_EXECUTIONID

        private val SQL_CREATE_SEATTABLE =
            "CREATE TABLE $SEATTABLE_NAME (" +
                    "$COL_SEATNUMBER INTEGER," +
                    "$COL_SEATEXECUTIONID INTEGER," +
                    "PRIMARY KEY ($COL_SEATNUMBER, $COL_SEATEXECUTIONID)" +
                    ")"

        /**ExecutionTable**/
        val EXECUTIONTABLE_NAME = DBContract.ExecutionEntry.TABLE_NAME
        val COL_EXECUTIONID = DBContract.ExecutionEntry.COLUMN_EXECUTIONID
        val COL_EXECFLIGHTNUMBER = DBContract.ExecutionEntry.COLUMN_FLIGHTNUMBER
        val COL_EXECPLATENUMBER = DBContract.ExecutionEntry.COLUMN_PLATENUMBER
        val COL_EXECDATE = DBContract.ExecutionEntry.COLUMN_DATE
        val COL_EXECDEPARTURETIME = DBContract.ExecutionEntry.COLUMN_DEPARTURETIME
        val COL_EXECPRICE = DBContract.ExecutionEntry.COLUMN_PRICE
        val COL_EXECSTATUS = DBContract.ExecutionEntry.COLUMN_STATUS
        val COL_BOARDINGDOOR = DBContract.ExecutionEntry.COLUMN_BOARDINGDOOR


        private val SQL_CREATE_EXECUTIONTABLE =
            "CREATE TABLE $EXECUTIONTABLE_NAME (" +
                    "$COL_EXECUTIONID INTEGER PRIMARY KEY," +
                    "$COL_EXECFLIGHTNUMBER INTEGER," +
                    "$COL_EXECPLATENUMBER STRING," +
                    "$COL_EXECDATE STRING," +
                    "$COL_EXECDEPARTURETIME STRING," +
                    "$COL_EXECPRICE INTEGER," +
                    "$COL_EXECSTATUS STRING," +
                    "$COL_BOARDINGDOOR STRING," +
                    "FOREIGN KEY ($COL_EXECFLIGHTNUMBER) REFERENCES ${DBContract.FlightEntry.TABLE_NAME}(${DBContract.FlightEntry.COLUMN_FLIGHTNUMBER})," +
                    "FOREIGN KEY ($COL_EXECPLATENUMBER) REFERENCES ${DBContract.PlaneEntry.TABLE_NAME}(${DBContract.PlaneEntry.COLUMN_PLATE})" +
                    ")"

        /**TicketTable**/
        val TICKETTABLE_NAME = DBContract.TicketEntry.TABLE_NAME
        val COL_TICKETID = DBContract.TicketEntry.COLUMN_TICKETID
        val COL_TICKETSEATNUMBER = DBContract.TicketEntry.COLUMN_SEATNUMBER
        val COL_TICKETEXECID = DBContract.TicketEntry.COLUMN_EXECUTIONID
        val COL_TICKETPASSENGERID = DBContract.TicketEntry.COLUMN_PASSENGERID
        val COL_TICKETUSERID = DBContract.TicketEntry.COLUMN_USERID
        val COL_TAXES = DBContract.TicketEntry.COLUMN_TAX
        val COL_TOTAL = DBContract.TicketEntry.COLUMN_TOTAL

        private val SQL_CREATE_TICKETTABLE =
            "CREATE TABLE $TICKETTABLE_NAME (" +
                    "$COL_TICKETID INTEGER PRIMARY KEY," +
                    "$COL_TICKETSEATNUMBER INTEGER," +
                    "$COL_TICKETEXECID INTEGER," +
                    "$COL_TICKETPASSENGERID INTEGER," +
                    "$COL_TICKETUSERID INTEGER," +
                    "$COL_TAXES INTEGER," +
                    "$COL_TOTAL INTEGER," +
                    "FOREIGN KEY ($COL_TICKETSEATNUMBER) REFERENCES ${DBContract.SeatEntry.TABLE_NAME}(${DBContract.SeatEntry.COLUMN_NUMBER})," +
                    "FOREIGN KEY ($COL_TICKETEXECID) REFERENCES ${DBContract.ExecutionEntry.TABLE_NAME}(${DBContract.ExecutionEntry.COLUMN_EXECUTIONID})," +
                    "FOREIGN KEY ($COL_TICKETPASSENGERID) REFERENCES ${DBContract.PassengerEntry.TABLE_NAME}(${DBContract.PassengerEntry.COLUMN_PASSENGERID})," +
                    "FOREIGN KEY ($COL_TICKETUSERID) REFERENCES ${DBContract.UserEntry.TABLE_NAME}(${DBContract.UserEntry.COLUMN_USERID})" +
                    ")"

        /**PlaneTable**/
        val PLANETABLE_NAME = DBContract.PlaneEntry.TABLE_NAME
        val COL_PLATENUMBER = DBContract.PlaneEntry.COLUMN_PLATE //PrimaryKey
        val COL_COLUMNNUMBER = DBContract.PlaneEntry.COLUMN_COL
        val COL_ROWNUMBER = DBContract.PlaneEntry.COLUMN_ROW

        private val SQL_CREATE_PLANETABLE =
            "CREATE TABLE $PLANETABLE_NAME (" +
                    "$COL_PLATENUMBER STRING PRIMARY KEY," +
                    "$COL_COLUMNNUMBER INTEGER," +
                    "$COL_ROWNUMBER INTEGER" +
                    ")"

        /**SuitcaseTable**/
        val SUITCASETABLE_NAME = DBContract.SuitcaseEntry.TABLE_NAME
        val COL_SUITCASEID = DBContract.SuitcaseEntry.COLUMN_SUITCASEID //PrimaryKey
        val COL_SUITCASEPASSENGERID = DBContract.SuitcaseEntry.COLUMN_PASSENGERID //foreign key
        val COL_WEIGHT = DBContract.SuitcaseEntry.COLUMN_WEIGHT
        val COL_COLOR = DBContract.SuitcaseEntry.COLUMN_COLOR

        private val SQL_CREATE_SUITCASETABLE =
            "CREATE TABLE $SUITCASETABLE_NAME (" +
                    "$COL_SUITCASEID INTEGER PRIMARY KEY," +
                    "$COL_SUITCASEPASSENGERID INTEGER," +
                    "$COL_WEIGHT INTEGER," +
                    "$COL_COLOR STRING," +
                    "FOREIGN KEY ($COL_SUITCASEPASSENGERID) REFERENCES ${DBContract.PassengerEntry.TABLE_NAME}(${DBContract.PassengerEntry.COLUMN_PASSENGERID})" +
                    ")"

        /**LayoverTable**/
        val LAYOVERTABLE_NAME = DBContract.LayoverEntry.TABLE_NAME
        val COL_LAYOVERID = DBContract.LayoverEntry.COLUMN_LAYOVERID //PrimaryKey
        val COL_LAYOVERFLIGHTNUMBER = DBContract.LayoverEntry.COLUMN_FLIGHTNUMBER //foreign key
        val COL_LAYOVERORIGIN = DBContract.LayoverEntry.COLUMN_ORIGIN //foreign key
        val COL_LAYOVERDESTIN = DBContract.LayoverEntry.COLUMN_DESTINATION  //foreign key
        private val SQL_CREATE_LAYOVERTABLE = (
                "CREATE TABLE $LAYOVERTABLE_NAME (" +
                        "$COL_LAYOVERID INTEGER PRIMARY KEY, " +
                        "$COL_LAYOVERFLIGHTNUMBER INTEGER, " +
                        "$COL_LAYOVERORIGIN STRING, " +
                        "$COL_LAYOVERDESTIN STRING, " +
                        "FOREIGN KEY ($COL_LAYOVERFLIGHTNUMBER) REFERENCES FlightTable(FlightNumber), " +
                        "FOREIGN KEY ($COL_LAYOVERORIGIN) REFERENCES AirportTable(AirportCode), " +
                        "FOREIGN KEY ($COL_LAYOVERDESTIN) REFERENCES AirportTable(AirportCode)" +
                        ");")

        /**ExecutionLayoverTable**/
        val XLAYOVERTABLE_NAME = DBContract.ExecutionLayoverEntry.TABLE_NAME
        val COL_XLAYOVERID = DBContract.ExecutionLayoverEntry.COLUMN_LAYOVERID //PrimaryKey
        val COL_XLAYOVEREXECUTIONID =
            DBContract.ExecutionLayoverEntry.COLUMN_EXECUTIONID //PrimaryKey
        val COL_XLAYOVERPRICE = DBContract.ExecutionLayoverEntry.COLUMN_PRICE
        private val SQL_CREATE_XLAYOVERTABLE = (
                "CREATE TABLE " + XLAYOVERTABLE_NAME + " (" +
                        COL_XLAYOVERID + " INTEGER, " +
                        COL_XLAYOVEREXECUTIONID + " INTEGER, " +
                        COL_XLAYOVERPRICE + " INTEGER, " +
                        "PRIMARY KEY (" + COL_XLAYOVERID + ", " + COL_XLAYOVEREXECUTIONID + ")" +
                        ");")

        private val SQL_DELETE_USERTABLE = "DROP TABLE IF EXISTS " + DBContract.UserEntry.TABLE_NAME
        private val SQL_DELETE_PASSENGERTABLE =
            "DROP TABLE IF EXISTS " + DBContract.PassengerEntry.TABLE_NAME
        private val SQL_DELETE_FLIGHTTABLE =
            "DROP TABLE IF EXISTS " + DBContract.FlightEntry.TABLE_NAME
        private val SQL_DELETE_RESERVATIONTABLE =
            "DROP TABLE IF EXISTS " + DBContract.ReservationEntry.TABLE_NAME
        private val SQL_DELETE_AIRPORTTABLE =
            "DROP TABLE IF EXISTS " + DBContract.AirportEntry.TABLE_NAME
        private val SQL_DELETE_PROMOTIONTABLE =
            "DROP TABLE IF EXISTS " + DBContract.PromotionEntry.TABLE_NAME
        private val SQL_DELETE_STUDENTTABLE =
            "DROP TABLE IF EXISTS " + DBContract.StudentEntry.TABLE_NAME
        private val SQL_DELETE_SEATTABLE = "DROP TABLE IF EXISTS " + DBContract.SeatEntry.TABLE_NAME
        private val SQL_DELETE_EXECUTIONTABLE =
            "DROP TABLE IF EXISTS " + DBContract.ExecutionEntry.TABLE_NAME
        private val SQL_DELETE_TICKETTABLE =
            "DROP TABLE IF EXISTS " + DBContract.TicketEntry.TABLE_NAME
        private val SQL_DELETE_PLANETABLE =
            "DROP TABLE IF EXISTS " + DBContract.PlaneEntry.TABLE_NAME
        private val SQL_DELETE_SUITCASETABLE =
            "DROP TABLE IF EXISTS " + DBContract.SuitcaseEntry.TABLE_NAME
        private val SQL_DELETE_LAYOVERTABLE =
            "DROP TABLE IF EXISTS " + DBContract.LayoverEntry.TABLE_NAME
        private val SQL_DELETE_XLAYOVERTABLE =
            "DROP TABLE IF EXISTS " + DBContract.ExecutionLayoverEntry.TABLE_NAME

    }

    /* Crea la base de datos*/
    override fun onCreate(db: SQLiteDatabase) {

        db.execSQL(SQL_CREATE_USERTABLE)//x
        db.execSQL(SQL_CREATE_AIRPORTTABLE)//x
        db.execSQL(SQL_CREATE_EXECUTIONTABLE)//x
        db.execSQL(SQL_CREATE_LAYOVERTABLE)//x
        db.execSQL(SQL_CREATE_FLIGHTTABLE)//x
        db.execSQL(SQL_CREATE_PASSENGERTABLE)//x
        db.execSQL(SQL_CREATE_PLANETABLE)//x
        db.execSQL(SQL_CREATE_PROMOTIONTABLE)//x
        db.execSQL(SQL_CREATE_RESERVATIONTABLE)//x
        db.execSQL(SQL_CREATE_SEATTABLE)//x
        db.execSQL(SQL_CREATE_STUDENTTABLE)//x
        db.execSQL(SQL_CREATE_SUITCASETABLE)
        db.execSQL(SQL_CREATE_TICKETTABLE)//x
        db.execSQL(SQL_CREATE_XLAYOVERTABLE)//x
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        //Descarta la fecha y reinicia
        db.execSQL(SQL_DELETE_USERTABLE)//x
        db.execSQL(SQL_DELETE_AIRPORTTABLE)//x
        db.execSQL(SQL_DELETE_EXECUTIONTABLE)//x
        db.execSQL(SQL_DELETE_LAYOVERTABLE)//x
        db.execSQL(SQL_DELETE_FLIGHTTABLE)//x
        db.execSQL(SQL_DELETE_PASSENGERTABLE)//x
        db.execSQL(SQL_DELETE_PLANETABLE)//x
        db.execSQL(SQL_DELETE_PROMOTIONTABLE)//x
        db.execSQL(SQL_DELETE_RESERVATIONTABLE)//x
        db.execSQL(SQL_DELETE_SEATTABLE)//x
        db.execSQL(SQL_DELETE_STUDENTTABLE)//x
        db.execSQL(SQL_DELETE_SUITCASETABLE)
        db.execSQL(SQL_DELETE_TICKETTABLE)//x
        db.execSQL(SQL_DELETE_XLAYOVERTABLE)//x
        onCreate(db)
    }

    override fun onDowngrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        super.onDowngrade(db, oldVersion, newVersion)
    }

    @Throws(SQLiteConstraintException::class)
    fun insertData(nombre: String, userID: Int, phone: String): Long {
        //Establece el repositorio de datos en modo escritura
        val db = writableDatabase

        //Mapea los valores donde los nombres de columnas son las llaves
        val insertvalues = ContentValues()
        insertvalues.put(DBContract.UserEntry.COLUMN_NAME, nombre)
        insertvalues.put(DBContract.UserEntry.COLUMN_USERID, userID)
        insertvalues.put(DBContract.UserEntry.COLUMN_PHONE, phone)


        //Inserta la nueva fila retornando la llave primaria de la nueva fila
        val action = db.insert(DBContract.UserEntry.TABLE_NAME, null, insertvalues)
        db.close() //cierra la conexion con la DB
        return action
    }

    /**
     * Metodo que crea el record del usuario en DB
     *
     * @param user
     */
    fun addUser(user: User) {
        val db = this.writableDatabase
        val insertvalues = ContentValues()
        insertvalues.put(DBContract.UserEntry.COLUMN_NAME, user.fullName)
        insertvalues.put(DBContract.UserEntry.COLUMN_USERID, user.userID)
        insertvalues.put(DBContract.UserEntry.COLUMN_PASSWORD, user.password)
        insertvalues.put(DBContract.UserEntry.COLUMN_PHONE, user.phoneNumber)
        insertvalues.put(DBContract.UserEntry.COLUMN_EMAIL, user.email)
        insertvalues.put(DBContract.UserEntry.COLUMN_PASSENGERID, user.passengerID)

        // insertar fila
        db.insert(USERTABLE_NAME, null, insertvalues)
        db.close()
    }

    /**
     * Metodo que agrega información del pasajero a la DB
     *
     * @param passenger
     */
    fun addPassenger(passenger: Passenger) {
        val db = this.writableDatabase
        val insertvalues = ContentValues()
        insertvalues.put(DBContract.PassengerEntry.COLUMN_PASSENGERID, passenger.passengerID)
        insertvalues.put(DBContract.PassengerEntry.COLUMN_NAME, passenger.name)
        insertvalues.put(DBContract.PassengerEntry.COLUMN_PHONE, passenger.phoneNumber)

        //insert
        db.insert(PASSENGERTABLE_NAME, null, insertvalues)
        db.close()
    }

    /**
     * Metodo que agrega información del vuelo a la DB
     *
     * @param Flight
     */

    fun addFlight(flight: Flight) {
        val db = this.writableDatabase
        val insertvalues = ContentValues()
        insertvalues.put(DBContract.FlightEntry.COLUMN_FLIGHTNUMBER, flight.number)
        insertvalues.put(DBContract.FlightEntry.COLUMN_ORIGIN, flight.origin)
        insertvalues.put(DBContract.FlightEntry.COLUMN_DESTINATION, flight.destination)

        //insert
        db.insert(FLIGHTTABLE_NAME, null, insertvalues)
        db.close()
    }

    /**
     * Metodo que agrega información de la reservacion a la DB
     *
     * @param reservation
     */
    fun addReservation(reservation: Reservation) {
        val db = this.writableDatabase
        val insertvalues = ContentValues()
        insertvalues.put(DBContract.ReservationEntry.COLUMN_RESERVID, reservation.reservationID)
        insertvalues.put(DBContract.ReservationEntry.COLUMN_PASSENGERID, reservation.passengerID)
        insertvalues.put(DBContract.ReservationEntry.COLUMN_EXECUTIONID, reservation.executionID)

        //insert
        db.insert(RESERVATIONTABLE_NAME, null, insertvalues)
        db.close()
    }

    /**
     * Metodo que agrega información del aeropuerto a la DB
     *
     * @param Airport
     */
    fun addAirport(airport: Airport) {
        val db = this.writableDatabase
        val insertvalues = ContentValues()
        insertvalues.put(DBContract.AirportEntry.COLUMN_NAME, airport.name)
        insertvalues.put(DBContract.AirportEntry.COLUMN_CITY, airport.city)

        //insert
        db.insert(AIRPORTTABLE_NAME, null, insertvalues)
        db.close()
    }

    /**
     * Metodo que agrega información de las promos a la DB
     *
     * @param promotion
     */
    fun addPromotion(promotion: Promotion) {
        val db = this.writableDatabase
        val insertvalues = ContentValues()
        insertvalues.put(DBContract.PromotionEntry.COLUMN_NUMBER, promotion.promoNumber)
        insertvalues.put(DBContract.PromotionEntry.COLUMN_EXECUTIONID, promotion.promoExecID)
        insertvalues.put(DBContract.PromotionEntry.COLUMN_PERIOD, promotion.promoPeriod)
        insertvalues.put(DBContract.PromotionEntry.COLUMN_PRICE, promotion.promoPrice)

        //insert
        db.insert(PROMOTIONTABLE_NAME, null, insertvalues)
        db.close()
    }

    /**
     * Metodo que agrega información del estudiante a la DB
     *
     * @param student
     */
    fun addStudent(student: Student) {
        val db = this.writableDatabase
        val insertvalues = ContentValues()
        insertvalues.put(DBContract.StudentEntry.COLUMN_UNICARD, student.universityCard)
        insertvalues.put(DBContract.StudentEntry.COLUMN_USERID, student.userID)
        insertvalues.put(DBContract.StudentEntry.COLUMN_UNINAME, student.universityName)
        insertvalues.put(DBContract.StudentEntry.COLUMN_MILES, student.miles)

        //insert
        db.insert(STUDENTTABLE_NAME, null, insertvalues)
        db.close()
    }

    /**
     * Metodo que agrega información del asiento a la DB
     *
     * @param seat
     */
    fun addSeat(seat: Seat) {
        val db = this.writableDatabase
        val insertvalues = ContentValues()
        insertvalues.put(DBContract.SeatEntry.COLUMN_NUMBER, seat.number)
        insertvalues.put(DBContract.SeatEntry.COLUMN_EXECUTIONID, seat.executionID)

        //insert
        db.insert(SEATTABLE_NAME, null, insertvalues)
        db.close()
    }

    /**
     * Metodo que agrega información de la ejecución a la DB
     *
     * @param execution
     */
    fun addExecution(execution: Execution) {
        val db = this.writableDatabase
        val insertvalues = ContentValues()
        insertvalues.put(DBContract.ExecutionEntry.COLUMN_EXECUTIONID, execution.executionID)
        insertvalues.put(DBContract.ExecutionEntry.COLUMN_FLIGHTNUMBER, execution.flightNumber)
        insertvalues.put(DBContract.ExecutionEntry.COLUMN_PLATENUMBER, execution.plateNumber)
        insertvalues.put(DBContract.ExecutionEntry.COLUMN_DATE, execution.date)
        insertvalues.put(DBContract.ExecutionEntry.COLUMN_DEPARTURETIME, execution.departureTime)
        insertvalues.put(DBContract.ExecutionEntry.COLUMN_PRICE, execution.price)
        insertvalues.put(DBContract.ExecutionEntry.COLUMN_STATUS, execution.status)
        insertvalues.put(DBContract.ExecutionEntry.COLUMN_BOARDINGDOOR, execution.boardingDoor)

        //insert
        db.insert(EXECUTIONTABLE_NAME, null, insertvalues)
        db.close()
    }

    /**
     * Metodo que agrega información del tiquete a la DB
     *
     * @param ticket
     */
    fun addTicket(ticket: Ticket) {
        val db = this.writableDatabase
        val insertvalues = ContentValues()
        insertvalues.put(DBContract.TicketEntry.COLUMN_TICKETID, ticket.ticketID)
        insertvalues.put(DBContract.TicketEntry.COLUMN_SEATNUMBER, ticket.seatNumber)
        insertvalues.put(DBContract.TicketEntry.COLUMN_EXECUTIONID, ticket.executionID)
        insertvalues.put(DBContract.TicketEntry.COLUMN_PASSENGERID, ticket.passengerID)
        insertvalues.put(DBContract.TicketEntry.COLUMN_USERID, ticket.userID)
        insertvalues.put(DBContract.TicketEntry.COLUMN_TAX, ticket.taxes)
        insertvalues.put(DBContract.TicketEntry.COLUMN_TOTAL, ticket.totalAmount)


        //insert
        db.insert(TICKETTABLE_NAME, null, insertvalues)
        db.close()
    }

    /**
     * Metodo que agrega información del avion a la DB
     *
     * @param plane
     */
    fun addPlane(plane: Plane) {
        val db = this.writableDatabase
        val insertvalues = ContentValues()
        insertvalues.put(DBContract.PlaneEntry.COLUMN_PLATE, plane.plateNumber)
        insertvalues.put(DBContract.PlaneEntry.COLUMN_COL, plane.columnNumber)
        insertvalues.put(DBContract.PlaneEntry.COLUMN_ROW, plane.rowNumber)

        //insert
        db.insert(PLANETABLE_NAME, null, insertvalues)
        db.close()
    }

    /**
     * Metodo que agrega información del equipaje a la DB
     *
     * @param suitcase
     */
    fun addSuitcase(suitcase: Suitcase) {
        val db = this.writableDatabase
        val insertvalues = ContentValues()
        insertvalues.put(DBContract.SuitcaseEntry.COLUMN_SUITCASEID, suitcase.suitcaseID)
        insertvalues.put(DBContract.SuitcaseEntry.COLUMN_PASSENGERID, suitcase.passengerID)
        insertvalues.put(DBContract.SuitcaseEntry.COLUMN_WEIGHT, suitcase.weight)
        insertvalues.put(DBContract.SuitcaseEntry.COLUMN_COLOR, suitcase.color)

        //insert
        db.insert(SUITCASETABLE_NAME, null, insertvalues)
        db.close()
    }

    /**
     * Metodo que agrega información de la escala a la DB
     *
     * @param layover
     */
    fun addLayover(layover: Layover) {
        val db = this.writableDatabase
        val insertvalues = ContentValues()
        insertvalues.put(DBContract.LayoverEntry.COLUMN_LAYOVERID, layover.layoverID)
        insertvalues.put(DBContract.LayoverEntry.COLUMN_FLIGHTNUMBER, layover.flightNumber)
        insertvalues.put(DBContract.LayoverEntry.COLUMN_ORIGIN, layover.origin)
        insertvalues.put(DBContract.LayoverEntry.COLUMN_DESTINATION, layover.destination)

        //insert
        db.insert(LAYOVERTABLE_NAME, null, insertvalues)
        db.close()

    }

    /**
     * Metodo que agrega información de la escala de la ejecución a la DB
     *
     * @param executionLayover
     */
    fun addExecutionLayover(executionLayover: ExecutionLayover) {
        val db = this.writableDatabase
        val insertvalues = ContentValues()
        insertvalues.put(
            DBContract.ExecutionLayoverEntry.COLUMN_LAYOVERID,
            executionLayover.layoverID
        )
        insertvalues.put(
            DBContract.ExecutionLayoverEntry.COLUMN_EXECUTIONID,
            executionLayover.executionID
        )
        insertvalues.put(DBContract.ExecutionLayoverEntry.COLUMN_PRICE, executionLayover.price)

        //insert
        db.insert(XLAYOVERTABLE_NAME, null, insertvalues)
        db.close()
    }


    /**
     * Metodo que elimina una fila de la tabla de usuarios
     * basándose en la cédula
     *
     * @param user
     */
    @Throws(SQLiteConstraintException::class)
    fun deleteUserData(id: String): Int {
        val db = writableDatabase
        // Define el "where" de la consulta
        val selection = DBContract.UserEntry.COLUMN_USERID + " LIKE ?"
        //Especifica los argumentos en el orden del placeholder
        val selectionArgs = arrayOf(id)
        // Directiva de SQL que retorna el numero de filas borradas
        val action = db.delete(DBContract.UserEntry.TABLE_NAME, selection, selectionArgs)
        db.close()
        return action
    }

    /**Método q lee todos los datos de la tabla de usuarios y
     * los almacena en un ArrayList*/
    fun readUsers(): ArrayList<User> {
        val dBdata = ArrayList<User>()
        val db = writableDatabase
        var cursor: Cursor? = null
        try {
            cursor = db.rawQuery("select * from " + DBContract.UserEntry.TABLE_NAME, null)
        } catch (e: SQLiteException) {
            db.execSQL(SQL_CREATE_USERTABLE)
            return ArrayList()
        }

        var userID: Int
        var passengerID: Int
        var name: String
        var phoneNumber: String
        var password: String
        var email: String

        if (cursor!!.moveToFirst()) {
            while (cursor.isAfterLast == false) {
                name =
                    cursor.getString(cursor.getColumnIndexOrThrow(DBContract.UserEntry.COLUMN_NAME))
                userID =
                    cursor.getInt(cursor.getColumnIndexOrThrow(DBContract.UserEntry.COLUMN_USERID))
                phoneNumber =
                    cursor.getString(cursor.getColumnIndexOrThrow(DBContract.UserEntry.COLUMN_PHONE))
                password =
                    cursor.getString(cursor.getColumnIndexOrThrow(DBContract.UserEntry.COLUMN_PASSWORD))
                email =
                    cursor.getString(cursor.getColumnIndexOrThrow(DBContract.UserEntry.COLUMN_EMAIL))
                passengerID =
                    cursor.getInt(cursor.getColumnIndexOrThrow(DBContract.UserEntry.COLUMN_PASSENGERID))

                dBdata.add(User(name, userID, password, phoneNumber, email, passengerID))
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
        val columns = arrayOf(COL_EMAIL)
        val db = this.readableDatabase
        // selection criteria
        val selection = "$COL_EMAIL = ?"
        // selection argument
        val selectionArgs = arrayOf(email)
        // query user table with condition

        val cursor = db.query(
            USERTABLE_NAME, //Table to query
            columns,        //columns to return
            selection,      //columns for the WHERE clause
            selectionArgs,  //The values for the WHERE clause
            null,  //group the rows
            null,   //filter by row groups
            null
        )  //The sort order
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
        val columns = arrayOf(COL_USERID)
        val db = this.readableDatabase
        // selection criteria
        val selection = "$COL_EMAIL = ? AND $COL_PASSWORD = ?"
        // selection arguments
        val selectionArgs = arrayOf(email, password)
        // query user table with conditions

        val cursor = db.query(
            USERTABLE_NAME, //Table to query
            columns, //columns to return
            selection, //columns for the WHERE clause
            selectionArgs, //The values for the WHERE clause
            null,  //group the rows
            null, //filter by row groups
            null
        ) //The sort order
        val cursorCount = cursor.count
        cursor.close()
        db.close()
        if (cursorCount > 0)
            return true
        return false
    }

    fun checkFlight(origin: String, destination: String): List<Flight> {
        val flightList = mutableListOf<Flight>()
        val query = "SELECT * FROM $FLIGHTTABLE_NAME WHERE " +
                "${DBContract.FlightEntry.COLUMN_ORIGIN} = ? AND " +
                "${DBContract.FlightEntry.COLUMN_DESTINATION} = ?"

        val db = this.readableDatabase
        val cursor = db.rawQuery(query, arrayOf(origin, destination))

        if (cursor != null && cursor.moveToFirst()) {
            val flightNumberIndex =
                cursor.getColumnIndex(DBContract.FlightEntry.COLUMN_FLIGHTNUMBER)
            val originIndex = cursor.getColumnIndex(DBContract.FlightEntry.COLUMN_ORIGIN)
            val destinationIndex = cursor.getColumnIndex(DBContract.FlightEntry.COLUMN_DESTINATION)

            do {
                val flightNumber = cursor.getInt(flightNumberIndex)
                val flightOrigin = cursor.getString(originIndex)
                val flightDestination = cursor.getString(destinationIndex)

                val flight = Flight(flightNumber, flightOrigin, flightDestination)
                flightList.add(flight)
            } while (cursor.moveToNext())
        }

        cursor?.close()
        db.close()

        return flightList
    }

    fun checkExecution(flightNumber: Int): List<Execution> {
        val executionList = mutableListOf<Execution>()
        val db = this.readableDatabase
        val query =
            "SELECT ${DBContract.ExecutionEntry.COLUMN_DATE}, ${DBContract.ExecutionEntry.COLUMN_PRICE} " +
                    "FROM $EXECUTIONTABLE_NAME " +
                    "WHERE ${DBContract.ExecutionEntry.COLUMN_FLIGHTNUMBER} = ?"

        val cursor = db.rawQuery(query, arrayOf(flightNumber.toString()))

        if (cursor != null && cursor.moveToFirst()) {
            val dateIndex = cursor.getColumnIndex(DBContract.ExecutionEntry.COLUMN_DATE)
            val priceIndex = cursor.getColumnIndex(DBContract.ExecutionEntry.COLUMN_PRICE)

            do {
                val date = cursor.getString(dateIndex)
                val price = cursor.getString(priceIndex)

                val execution = Execution(
                    executionID = 0,  // You can set this to 0 or provide an appropriate value
                    flightNumber = flightNumber,
                    plateNumber = 0,  // You can set this to 0 or provide an appropriate value
                    date = date,
                    departureTime = "",  // You can set this to an empty string or provide an appropriate value
                    price = price,
                    status = "",  // You can set this to an empty string or provide an appropriate value
                    boardingDoor = ""  // You can set this to an empty string or provide an appropriate value
                )
                executionList.add(execution)
            } while (cursor.moveToNext())
        }

        cursor?.close()
        db.close()

        return executionList
    }

    fun getAllPromos(): List<Promotion> {
        val promotionList = mutableListOf<Promotion>()
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM ${DBContract.PromotionEntry.TABLE_NAME}", null)

        while (cursor.moveToNext()) {
            val promotionNumberIndex = cursor.getColumnIndex(DBContract.PromotionEntry.COLUMN_NUMBER)
            val promoPeriodIndex = cursor.getColumnIndex(DBContract.PromotionEntry.COLUMN_PERIOD)
            val promoPriceIndex = cursor.getColumnIndex(DBContract.PromotionEntry.COLUMN_PRICE)

            // Check if columns exist in the cursor
            if (promotionNumberIndex >= 0 && promoPeriodIndex >= 0 && promoPriceIndex >= 0) {
                val promotionNumber = cursor.getInt(promotionNumberIndex)
                val promoPeriod = cursor.getString(promoPeriodIndex)
                val promoPrice = cursor.getString(promoPriceIndex)

                val promotion = Promotion(promotionNumber,0, promoPeriod, promoPrice)
                promotionList.add(promotion)
            }
        }

        cursor.close()
        return promotionList
    }

    fun getUserData(): User? {
        var userData: User? = null
        val selectQuery = "SELECT $COL_NAME, $COL_EMAIL, $COL_PHONE, $COL_USERID FROM $USERTABLE_NAME"
        val db = this.readableDatabase

        val cursor = db.rawQuery(selectQuery, null)
        if (cursor.moveToFirst()) {
            val nameColumnIndex = cursor.getColumnIndex(COL_NAME)
            val emailColumnIndex = cursor.getColumnIndex(COL_EMAIL)
            val phoneColumnIndex = cursor.getColumnIndex(COL_PHONE)
            val userIdColumnIndex = cursor.getColumnIndex(COL_USERID)
            val passwordColumnIndex = cursor.getColumnIndex(COL_PASSWORD)

            // Check if the columns exist
            if (nameColumnIndex >= 0 && emailColumnIndex >= 0 && phoneColumnIndex >= 0 && userIdColumnIndex >= 0) {
                val name = cursor.getString(nameColumnIndex)
                val email = cursor.getString(emailColumnIndex)
                val phone = cursor.getString(phoneColumnIndex)
                val userId = cursor.getInt(userIdColumnIndex)
                val password = cursor.getString(passwordColumnIndex)

                // Create a User object with the retrieved data
                userData = User(name, userId, password , phone, email, null)
            }
        }

        cursor.close()
        db.close()

        return userData
    }




    fun getAllReservationsWithInfo(any: Any) {

    }


}