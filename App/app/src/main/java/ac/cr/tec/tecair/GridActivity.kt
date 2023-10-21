package ac.cr.tec.tecair

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.cardview.widget.CardView

class GridActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_grid)

        val passengerCardView = findViewById<CardView>(R.id.passengerCardView)
        val flightsCardView = findViewById<CardView>(R.id.flightsCardView)
        val reservationsCardView = findViewById<CardView>(R.id.reservationsCardView)
        val promotionsCardView = findViewById<CardView>(R.id.promotionsCardView)

        // Set click listeners for CardViews
        passengerCardView.setOnClickListener {
            val intent = Intent(this, PassengerActivity::class.java)
            startActivity(intent)
        }

        flightsCardView.setOnClickListener {
            val intent = Intent(this, FlightsActivity::class.java)
            startActivity(intent)
        }

        reservationsCardView.setOnClickListener {
            val intent = Intent(this, ReservationActivity::class.java)
            startActivity(intent)
        }

        promotionsCardView.setOnClickListener {
            val intent = Intent(this, PromotionsActivity::class.java)
            startActivity(intent)
        }
    }
}