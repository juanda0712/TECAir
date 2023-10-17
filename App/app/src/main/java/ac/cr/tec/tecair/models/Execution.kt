package ac.cr.tec.tecair.models

import java.sql.Time
import java.util.Date

data class Execution(var executionID: Int, var flightNumber: Int, var plateNumber: Int,
                     var date: String, var departureTime: String, var price: String,
                     var status:String, var boardingDoor:String)
