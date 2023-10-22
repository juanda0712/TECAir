package ac.cr.tec.tecair

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.widget.TextView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class PassengerActivity : AppCompatActivity() {
    private lateinit var usernameTextView: TextView
    private lateinit var emailTextView: TextView
    private lateinit var phoneTextView: TextView
    private lateinit var userIdTextView: TextView
    private lateinit var passwordTextView: TextView

    private var isPasswordVisible: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_passenger)

        // Initialize Views
        usernameTextView = findViewById(R.id.usernameTextView)
        emailTextView = findViewById(R.id.emailTextView)
        phoneTextView = findViewById(R.id.phoneTextView)
        userIdTextView = findViewById(R.id.userIdTextView)
        passwordTextView = findViewById(R.id.passwordTextView)

        // Load user data from the database
        val databaseHelper = DatabaseHelper(this)
        val user = databaseHelper.getUserData() // Replace with your database query

        // Populate TextViews with user data
        user?.let {
            usernameTextView.text = it.fullName
            emailTextView.text = it.email
            phoneTextView.text = it.phoneNumber
            userIdTextView.text = it.userID.toString()
            if (!isPasswordVisible) {
                passwordTextView.text = "*********"
            } else {
                passwordTextView.text = it.password // Display actual password when visible
            }
        }

        // Handle password TextView click event to toggle visibility
        passwordTextView.setOnClickListener {
            isPasswordVisible = !isPasswordVisible
            if (!isPasswordVisible) {
                passwordTextView.text = "*********"
            } else {
                passwordTextView.text = user?.password // Display actual password when visible
            }
        }

        val fabBack = findViewById<FloatingActionButton>(R.id.fab_back)
        fabBack.setOnClickListener {
            // Handle FAB click to navigate to OtherActivity
            val intent = Intent(this, GridActivity::class.java)
            startActivity(intent)
        }
    }
}