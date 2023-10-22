package ac.cr.tec.tecair.models

data class ReservationInfo(
    val reservationID: Int,
    val passengerName: String,
    val flightNumber: Int,
    val originDestination: String,
    val executionDate: String,
    val executionPrice: String
)

