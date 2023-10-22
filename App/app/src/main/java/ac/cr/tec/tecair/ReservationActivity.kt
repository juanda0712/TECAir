import ac.cr.tec.tecair.DatabaseHelper
import ac.cr.tec.tecair.R
import ac.cr.tec.tecair.adapters.ReservationRecyclerAdapter
import ac.cr.tec.tecair.models.ReservationInfo
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton


class ReservationActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: ReservationRecyclerAdapter
    private lateinit var databaseHelper: DatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reservation)

        recyclerView = findViewById(R.id.reservationRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        databaseHelper = DatabaseHelper(this)

        // Fetch reservations with additional info
        val reservationsWithInfo = mutableListOf<ReservationInfo>()

        databaseHelper.getAllReservationsWithInfo { reservationInfoList: List<ReservationInfo>? ->
            if (reservationInfoList != null) {
                reservationsWithInfo.addAll(reservationInfoList)

                // Check if all data has been fetched
                if (reservationsWithInfo.size == reservationInfoList.size) {
                    // Update the RecyclerView with the fetched data
                    adapter = ReservationRecyclerAdapter(reservationsWithInfo)
                    recyclerView.adapter = adapter
                }
            }
        }

        val fabBack = findViewById<FloatingActionButton>(R.id.fab_back)
        fabBack.setOnClickListener {
            finish() // Finish the current activity to go back
        }
    }
}



