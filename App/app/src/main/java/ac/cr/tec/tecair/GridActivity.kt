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
            val userID = intent.getIntExtra("userID", 0)
            val intent = Intent(this, PassengerActivity::class.java)
            intent.putExtra("userID", userID)
            startActivity(intent)
        }

        flightsCardView.setOnClickListener {
            // Call the addSampleData function to populate sample data
            //SOLO PARA TESTING
            val databaseHelper = DatabaseHelper(this)
            DatabaseTestUtil(databaseHelper).addFlightSearchSampleData()

            // Start the FlightsActivity
            val intent = Intent(this, FlightsActivity::class.java)
            startActivity(intent)
        }

        reservationsCardView.setOnClickListener {
            val intent = Intent(this, ReservationActivity::class.java)
            val userID = intent.getIntExtra("userID", 0)
            intent.putExtra("userID", userID)
            startActivity(intent)
        }

        promotionsCardView.setOnClickListener {
            //Call the addPromoSampleData to populate sample data
            //TESTING
            val databaseHelper=DatabaseHelper(this)
            DatabaseTestUtil(databaseHelper).addPromoSampleData()

            //Start the promoactivity
            val intent = Intent(this, PromotionsActivity::class.java)
            startActivity(intent)
        }
    }
}