package ac.cr.tec.tecair

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.Toast

class RegisterActivity : AppCompatActivity() {

    private lateinit var databaseHelper: DatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        // Initialize the database helper
        databaseHelper = DatabaseHelper(this)

        // Init UI and handle the registration logic
        initUI()
    }

    private fun initUI() {
        val etName = findViewById<EditText>(R.id.et_college)
        val etID = findViewById<EditText>(R.id.et_collegeID)
        val etPhoneNumber = findViewById<EditText>(R.id.editTextPhone)
        val etEmail = findViewById<EditText>(R.id.et_email)
        val etPassword = findViewById<EditText>(R.id.et_password)
        val checkStudent = findViewById<CheckBox>(R.id.checkStudent)

        val btnRegister = findViewById<Button>(R.id.btn_finishSignup)

        btnRegister.setOnClickListener {
            val email = etEmail.text.toString()
            val password = etPassword.text.toString()
            val id= etID.text.toString()
            val phoneNumber = etPhoneNumber.text.toString()
            val name = etName.text.toString()

            if (isValidRegistrationInput(name, id, phoneNumber, email, password)) {
                if (checkStudent.isChecked) {
                    // Start an activity to recover more information about the student
                    val studentRecoveryIntent = Intent(this, StudentRegisterActivity::class.java)
                    startActivity(studentRecoveryIntent)
                } else {
                    // Start the normal next activity
                    val normalNextActivityIntent = Intent(this, GridActivity::class.java)
                    startActivity(normalNextActivityIntent)
                }

                // Clear input fields
                etEmail.text.clear()
                etPassword.text.clear()
                etName.text.clear()
                etID.text.clear()
                etPhoneNumber.text.clear()


            }
        }
    }

    private fun isValidRegistrationInput(name:String, id:String, phoneNumber:String, email: String, password: String, ): Boolean {
        if (email.isEmpty() || password.isEmpty()|| name.isEmpty()||phoneNumber.isEmpty() || id.isEmpty()) {
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
            return false
        }
        return true
    }
}

