package ac.cr.tec.tecair

import ReservationActivity
import ac.cr.tec.tecair.adapters.FlightRecyclerAdapter
import ac.cr.tec.tecair.models.Execution
import ac.cr.tec.tecair.models.Flight
import ac.cr.tec.tecair.models.Reservation
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class FlightsActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: FlightRecyclerAdapter
    private lateinit var databaseHelper: DatabaseHelper
    private lateinit var originEditText: EditText
    private lateinit var destinationEditText: EditText
    private lateinit var searchButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.supportActionBar?.hide()
        setContentView(R.layout.activity_flights)

        recyclerView = findViewById(R.id.flightRecyclerView)
        adapter = FlightRecyclerAdapter()
        databaseHelper = DatabaseHelper(this)

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        originEditText = findViewById(R.id.originEditText)
        destinationEditText = findViewById(R.id.destinationEditText)
        searchButton = findViewById(R.id.searchButton)

        val fabBack = findViewById<FloatingActionButton>(R.id.fab_back)
        fabBack.setOnClickListener {
            // Handle FAB click to navigate to OtherActivity
            val intent = Intent(this, GridActivity::class.java)
            startActivity(intent)
        }

        searchButton.setOnClickListener {
            val origin = originEditText.text.toString().trim()
            val destination = destinationEditText.text.toString().trim()

            if (origin.isNotEmpty() && destination.isNotEmpty()) {
                // Fetch flights from the database based on the provided origin and destination
                val flights = databaseHelper.checkFlight(origin, destination)

                // Initialize an empty list to store executions
                val executions = mutableListOf<Execution>()

                // Extract flight numbers from the flights and fetch executions for each flight number
                val flightNumbers = flights.map { it.number }
                for (flightNumber in flightNumbers) {
                    val execution = databaseHelper.checkExecution(flightNumber)
                    executions.addAll(execution)
                }

                // Set flights and executions in the adapter
                adapter.setFlightsAndExecutions(flights, executions)

                // Set click listener for the RecyclerView items to confirm reservations
                adapter.setOnItemClickListener { flight, execution ->
                    showReservationConfirmationDialog(flight, execution)
                }
            } else {
                Toast.makeText(this, "Please enter both origin and destination.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun showReservationConfirmationDialog(flight: Flight, execution: Execution) {
        AlertDialog.Builder(this)
            .setTitle("Confirm Reservation")
            .setMessage("Do you want to confirm the reservation for ${flight.number} on ${execution.date}?")
            .setPositiveButton("Confirm") { _, _ ->
                // Generate unique IDs for reservation and passenger
                val reservationId = generateUniqueReservationId()
                val passengerId = generateUniquePassengerId()

                // Create a reservation with the generated IDs
                val reservation = Reservation(reservationId, passengerId, execution.executionID)
                // Add the reservation to the database
                databaseHelper.addReservation(reservation)
                Toast.makeText(this, "Reservation confirmed!", Toast.LENGTH_SHORT).show()

                // Start ReservationActivity and pass necessary data
                val intent = Intent(this, ReservationActivity::class.java)
                intent.putExtra("reservationId", reservationId)
                intent.putExtra("flightId", flight.number)
                startActivity(intent)
            }

            .setNegativeButton("Cancel") { _, _ ->
                // User canceled the reservation
            }
            .show()
    }
}


    // Generate a unique reservation ID
    private fun generateUniqueReservationId(): Int {
        return System.currentTimeMillis().toInt()
    }

    // Generate a unique passenger ID
    private fun generateUniquePassengerId(): Int {
        return System.currentTimeMillis().toInt()
    }



