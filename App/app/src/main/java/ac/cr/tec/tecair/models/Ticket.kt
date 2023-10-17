package ac.cr.tec.tecair.models

data class Ticket(var ticketID:Int, var seatNumber: Int, var executionID:Int,
                  var passengerID:Int, var userID:Int, var taxes:Int, var totalAmount:Int)

