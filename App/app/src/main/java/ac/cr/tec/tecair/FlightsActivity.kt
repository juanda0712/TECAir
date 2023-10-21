package ac.cr.tec.tecair

import ac.cr.tec.tecair.adapters.FlightRecyclerAdapter
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class FlightsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.supportActionBar?.hide()
        setContentView(R.layout.activity_flights)

        val recyclerView = findViewById<RecyclerView>(R.id.flightRecyclerView)
        val adapter= FlightRecyclerAdapter()

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter= adapter

        val fabBack = findViewById<FloatingActionButton>(R.id.fab_back)
        fabBack.setOnClickListener {
            // Handle FAB click to navigate to OtherActivity
            val intent = Intent(this, GridActivity::class.java)
            startActivity(intent)}

    }
}