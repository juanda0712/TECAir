    package ac.cr.tec.tecair

    import ac.cr.tec.tecair.models.Execution
    import ac.cr.tec.tecair.models.Flight
    import ac.cr.tec.tecair.models.Student
    import ac.cr.tec.tecair.models.User
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
        private lateinit var collegeTextView: TextView
        private lateinit var collegeIDTextView: TextView
        private lateinit var milesTextView: TextView
        private lateinit var databaseHelper: DatabaseHelper
        private lateinit var user: User
        private lateinit var student: Student

        private var isPasswordVisible: Boolean = false

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            this.supportActionBar?.hide()
            setContentView(R.layout.activity_passenger)

            // Initialize Views
            usernameTextView = findViewById(R.id.usernameTextView)
            emailTextView = findViewById(R.id.emailTextView)
            phoneTextView = findViewById(R.id.phoneTextView)
            userIdTextView = findViewById(R.id.userIdTextView)
            passwordTextView = findViewById(R.id.passwordTextView)
            collegeTextView = findViewById(R.id.collegeTextView)
            collegeIDTextView = findViewById(R.id.collegeIDTextView)
            milesTextView = findViewById(R.id.milesTextView)

            // Initialize the DatabaseHelper
            databaseHelper = DatabaseHelper(this)

            val fabBack = findViewById<FloatingActionButton>(R.id.fab_back)
            fabBack.setOnClickListener {
                // Handle FAB click to navigate to GridActivity
                val intent = Intent(this, GridActivity::class.java)
                startActivity(intent)
            }

            // Retrieve userID from LoginActivity (you need to pass it as an extra from LoginActivity)
            val userID = intent.getIntExtra("userID", 0)

            // Retrieve user and student data based on the userID
            user = databaseHelper.getUserData(userID)?:User("",0,"","","",0)
            student = databaseHelper.getStudentData(userID)?:Student(0,0,"",0)

            // Bind the data to the views
            bind(user, student)

            // Set a click listener to show/hide the password
            passwordTextView.setOnClickListener {
                isPasswordVisible = !isPasswordVisible
                updatePasswordVisibility()
            }

            // Initially hide the password
            updatePasswordVisibility()
        }

        private fun updatePasswordVisibility() {
            if (isPasswordVisible) {
                passwordTextView.text = user.password
            } else {
                passwordTextView.text = "********"
            }
        }

        fun bind(user: User, student: Student) {
            usernameTextView.text = user.fullName
            emailTextView.text = user.email
            phoneTextView.text = user.phoneNumber
            userIdTextView.text = user.userID.toString()
            collegeTextView.text = student.universityName
            collegeIDTextView.text = student.universityCard.toString()
            milesTextView.text = student.miles.toString()
        }
    }