package ac.cr.tec.tecair

import ReservationInfo
import ac.cr.tec.tecair.adapters.ReservationRecyclerAdapter
import android.content.Intent
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
        supportActionBar?.hide()
        setContentView(R.layout.activity_reservation)

        recyclerView = findViewById(R.id.reservationRecyclerView)
        adapter = ReservationRecyclerAdapter()
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        // Initialize the DatabaseHelper
        databaseHelper = DatabaseHelper(this)

        // Fetch reservation info from the SQLite table
        val reservationInfoList = databaseHelper.getReservationInfo()

        // Set the list of ReservationInfo in the adapter
        adapter.setReservationInfoList(reservationInfoList)

        val fabBack = findViewById<FloatingActionButton>(R.id.fab_back)
        fabBack.setOnClickListener {
            // Handle FAB click to navigate to OtherActivity
            val intent = Intent(this, GridActivity::class.java)
            startActivity(intent)
        }
    }
}
