package ac.cr.tec.tecair

import ac.cr.tec.tecair.adapters.PromosRecyclerAdapter
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class PromotionsActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: PromosRecyclerAdapter
    private lateinit var databaseHelper: DatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_promotions)

        recyclerView = findViewById(R.id.promoRecyclerView)
        databaseHelper = DatabaseHelper(this)

        val promotions = databaseHelper.getAllPromos()
        adapter = PromosRecyclerAdapter(promotions)

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        val fabBack = findViewById<FloatingActionButton>(R.id.fab_back)
        fabBack.setOnClickListener {
            // Handle FAB click to navigate to OtherActivity
            val intent = Intent(this, GridActivity::class.java)
            startActivity(intent)}
    }
}
