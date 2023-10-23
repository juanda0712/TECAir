package ac.cr.tec.tecair

import android.provider.BaseColumns


/**
 * Contiene las definiciones de las tablas y columnas de la base de datos*/
object DBContract {

    //Clase de datos para la tabla User
    class UserEntry : BaseColumns {
        companion object {
            val TABLE_NAME = "User"

            val COLUMN_NAME = "FullName"
            val COLUMN_USERID = "UserID"
            val COLUMN_PASSENGERID = "PassengerID"
            val COLUMN_PASSWORD = "Password"
            val COLUMN_PHONE = "PhoneNumber"
            val COLUMN_EMAIL = "Email"
        }
    }
    //Clase de datos para la tabla Passenger
    class PassengerEntry:BaseColumns{
        companion object{
        val TABLE_NAME ="Passenger"

        val COLUMN_PASSENGERID = "PassengerID"
        val COLUMN_NAME= "Name"
        val COLUMN_PHONE= "PhoneNumber"
      }
    }
    //Clase de datos para la tabla Flight
    class FlightEntry: BaseColumns{
        companion object{
            val TABLE_NAME = "Flight"
            val COLUMN_FLIGHTNUMBER = "Number"
            val COLUMN_ORIGIN = "Origin"
            val COLUMN_DESTINATION = "Destination"
        }
    }
    //Clase de datos para la tabla Reservation
    class ReservationEntry: BaseColumns{
        companion object{
            val TABLE_NAME = "Reservation"
            val COLUMN_RESERVID= "ReservationID"
            val COLUMN_PASSENGERID= "UserID"
            val COLUMN_EXECUTIONID= "ExecutionID"
        }
    }
    //Clase de datos para la tabla Airport
    class AirportEntry:BaseColumns{
        companion object{
            val TABLE_NAME = "Airport"

            val COLUMN_NAME= "Name"
            val COLUMN_CITY= "City"
        }
    }
    //Clase de datos para la tabla Promociones
    class PromotionEntry:BaseColumns{
        companion object{
            val TABLE_NAME = "Promotion"
            val COLUMN_NUMBER= "Number"
            val COLUMN_EXECUTIONID= "ExecutionID"
            val COLUMN_ORIGIN = "Origin"
            val COLUMN_DESTINATION = "Destination"
            val COLUMN_PERIOD = "Period"
            val COLUMN_PRICE = "Price"
        }
    }
    //Clase de datos para la tabla Student
    class StudentEntry:BaseColumns{
        companion object{
            val TABLE_NAME = "Student"
            val COLUMN_USERID = "UserID"
            val COLUMN_UNICARD= "UniversityCard"
            val COLUMN_UNINAME= "UniversityName"
            val COLUMN_MILES= "Miles"
       }
    }
    //Clase de datos para la tabla Seat
    class SeatEntry:BaseColumns{
        companion object{
            val TABLE_NAME= "Seat"
            val COLUMN_NUMBER = "Number"
            val COLUMN_EXECUTIONID = "ExecutionID"
        }
    }
    //Clase de datos para la tabla Execution
    class ExecutionEntry:BaseColumns{
        companion object{
            val TABLE_NAME= "Execution"
            val COLUMN_EXECUTIONID = "ExecutionID"
            val COLUMN_FLIGHTNUMBER = "FlightNumber"
            val COLUMN_PLATENUMBER = "PlateNumber"
            val COLUMN_DATE = "Date"
            val COLUMN_DEPARTURETIME = "DepartureTime"
            val COLUMN_PRICE = "Price"
            val COLUMN_STATUS= "Status"
            val COLUMN_BOARDINGDOOR= "BoardingDoor"
        }
    }
    //Clase de datos para la tabla Ticket
    class TicketEntry:BaseColumns{
        companion object{
            val TABLE_NAME= "Ticket"
            val COLUMN_TICKETID = "TicketID"
            val COLUMN_SEATNUMBER = "SeatNumber"
            val COLUMN_EXECUTIONID = "ExecutionID"
            val COLUMN_PASSENGERID = "PassengerID"
            val COLUMN_USERID = "UserID"
            val COLUMN_TAX = "Taxes"
            val COLUMN_TOTAL= "TotalAmount"
        }
    }
    //Clase de datos para la tabla Plane
    class PlaneEntry:BaseColumns{
        companion object{
            val TABLE_NAME= "Planes"
            val COLUMN_PLATE= "PlateNumber"
            val COLUMN_COL= "ColumnNumber"
            val COLUMN_ROW= "RowNumber"
    }}

    //Clase de datos para la tabla Suitcase
    class SuitcaseEntry:BaseColumns{
        companion object{
        val TABLE_NAME="Suitcase"
        val COLUMN_SUITCASEID= "SuitcaseID"
        val COLUMN_PASSENGERID= "PassengerID"
        val COLUMN_WEIGHT= "Weight"
        val COLUMN_COLOR= "Color"
    }}

    //Clase de datos para la tabla Layover
    class LayoverEntry:BaseColumns{
        companion object{
        val TABLE_NAME="Layover"
        val COLUMN_LAYOVERID= "LayoverID"
        val COLUMN_FLIGHTNUMBER= "FlightNumber"
        val COLUMN_ORIGIN= "Origin"
        val COLUMN_DESTINATION= "Destination"
    }}

    //Clase de datos para la tabla ExecutionLayover
    class ExecutionLayoverEntry:BaseColumns{
        companion object{
        val TABLE_NAME="ExecutionLayover"
        val COLUMN_LAYOVERID= "LayoverID"
        val COLUMN_EXECUTIONID= "ExecutionID"
        val COLUMN_PRICE= "Price"
        }
    }

    class ReservationInfoEntry:BaseColumns{
        companion object{
        val TABLE_NAME="ReservationInfo"
        val COLUMN_FLIGHTNUMBER= "FlightNumber"
        val COLUMN_DATE= "Date"
        val COLUMN_PRICE= "Price"
        val COLUMN_ORIGIN= "Origin"
        val COLUMN_DESTINATION= "Destination"
        }
    }
}