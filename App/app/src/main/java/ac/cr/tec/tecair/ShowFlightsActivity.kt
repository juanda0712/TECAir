package ac.cr.tec.tecair

import ac.cr.tec.tecair.adapters.FlightRecyclerAdapter
import ac.cr.tec.tecair.models.Flight
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

class ShowFlightsActivity : AppCompatActivity() {
    private val activity= this@ShowFlightsActivity
    private lateinit var flightsRecyclerView: RecyclerView
    private lateinit var flightsList: MutableList<Flight>
    private lateinit var flightsRecyclerAdapter: FlightRecyclerAdapter
    private lateinit var databaseHelper: DatabaseHelper


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show_flights)

        var databaseHelper= DatabaseHelper(activity)

        flightsList= mutableListOf<Flight>()

        val flightA= Flight(2, "SJO", "NYC")

        //flightResult()

        //flightsRecyclerAdapter = FlightsRecyclerAdapter(listaFlights)
        //recyclerViewFlights.adapter = flightsRecyclerAdapter

    }

    private fun flightResult(){
        val db= databaseHelper.readableDatabase

        val cursor= db.rawQuery("select" + DBContract.FlightEntry.COLUMN_FLIGHTNUMBER
                + ", " + DBContract.FlightEntry.COLUMN_ORIGIN + ", " + DBContract.FlightEntry.COLUMN_DESTINATION  +" from "
                + DBContract.FlightEntry.TABLE_NAME, null)
    }

}