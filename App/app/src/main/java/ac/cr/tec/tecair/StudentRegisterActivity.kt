package ac.cr.tec.tecair

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import ac.cr.tec.tecair.DatabaseHelper
import ac.cr.tec.tecair.models.Student
import android.content.Intent
import com.google.android.material.floatingactionbutton.FloatingActionButton

class StudentRegisterActivity : AppCompatActivity() {

    private lateinit var etCollege: EditText
    private lateinit var etCollegeID: EditText
    private lateinit var btnFinishSignup: Button
    private lateinit var databaseHelper: DatabaseHelper
    private var userID: Int = -1 // Initialize with a default value

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_student_register)

        etCollege = findViewById(R.id.et_name)
        etCollegeID = findViewById(R.id.et_userID)
        btnFinishSignup = findViewById(R.id.btn_finishSignup)
        databaseHelper = DatabaseHelper(this)

        val fabBack = findViewById<FloatingActionButton>(R.id.fab_back)
        fabBack.setOnClickListener {
            // Handle FAB click to navigate to GridActivity
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)}


        userID = intent.getIntExtra("userID", -1)

        btnFinishSignup.setOnClickListener {
            val college = etCollege.text.toString().trim()
            val collegeID = etCollegeID.text.toString().trim().toInt()

            val student = Student(collegeID, userID, college, 0)

            if (verifyUserInput(college, collegeID)) {
                // Add the student data to the database
                databaseHelper.addStudent(student)
                Toast.makeText(this, "Student registered successfully!", Toast.LENGTH_SHORT).show()
            }else{
                Toast.makeText(this, "Error adding student to the database.", Toast.LENGTH_SHORT).show()
                Toast.makeText(this, "Invalid input. Please check your college and CollegeID.", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
    private fun verifyUserInput(college: String, collegeID: Int): Boolean {
        // Check if college contains only letters and is not empty
        val collegeRegex = Regex("^[A-Za-z ]+\$")
        val isCollegeValid = collegeRegex.matches(college) && college.isNotEmpty()
        // Check if collegeID is not zero (indicating that it is not left blank)
        val isCollegeIDValid = collegeID != 0
        // Return true if college is valid and collegeID is not zero, otherwise, return false
        return isCollegeValid && isCollegeIDValid
    }




