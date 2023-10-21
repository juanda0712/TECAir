package ac.cr.tec.tecair

import ac.cr.tec.tecair.adapters.PromosRecyclerAdapter
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class PromotionsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.supportActionBar?.hide()
        setContentView(R.layout.activity_promotions)

        val recyclerView = findViewById<RecyclerView>(R.id.promoRecyclerView)
        val adapter= PromosRecyclerAdapter()

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter= adapter

        val fabBack = findViewById<FloatingActionButton>(R.id.fab_back)
        fabBack.setOnClickListener {
            // Handle FAB click to navigate to OtherActivity
            val intent = Intent(this, GridActivity::class.java)
            startActivity(intent)}

    }
}