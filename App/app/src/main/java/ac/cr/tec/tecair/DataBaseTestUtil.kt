package ac.cr.tec.tecair

import ac.cr.tec.tecair.models.Flight
import ac.cr.tec.tecair.models.Execution
import ac.cr.tec.tecair.models.Promotion

class DatabaseTestUtil(private val databaseHelper: DatabaseHelper) {

    fun addFlightSearchSampleData() {
        val sampleFlights = listOf(
            Flight(1, "SFO", "JFK"),
            Flight(4, "SFO", "JFK"),
            Flight(2, "LAX", "ORD"),
            Flight(3, "ATL", "DFW")
        )

        val sampleExecutions = listOf(
            Execution(1, 1, 101, "2023-01-10", "08:00 AM", "$200.00", "On Time", "Gate A"),
            Execution(2, 2, 102, "2023-01-11", "09:30 AM", "$250.00", "Delayed", "Gate B"),
            Execution(3, 3, 103, "2023-01-12", "10:45 AM", "$180.00", "On Time", "Gate C"),
            Execution(4, 4, 103, "2023-01-12", "10:45 AM", "$180.00", "On Time", "Gate Z")
        )

        for (flight in sampleFlights) {
            databaseHelper.addFlight(flight)
        }

        for (execution in sampleExecutions) {
            databaseHelper.addExecution(execution)
        }
    }
    fun addPromoSampleData() {
        val samplePromos = listOf(
            Promotion(420420, 0, "2023-01-10 / 2023-03-10", "$350"),
            Promotion(696969, 0, "2023-01-10 / 2023-15-10", "$100"),
            Promotion(131313, 0, "2023-01-11 / 2023-05-11", "$250"),
            Promotion(404404, 0, "2023-01-12 / 2023-15-12", "$150")
        )

        for (promotion in samplePromos) {
            databaseHelper.addPromotion(promotion)
        }

    }
}

