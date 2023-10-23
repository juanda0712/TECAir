package ac.cr.tec.tecair

import ac.cr.tec.tecair.models.User
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.Toast

class RegisterActivity : AppCompatActivity() {

    private lateinit var databaseHelper: DatabaseHelper

    private lateinit var etName: EditText
    private lateinit var etID: EditText
    private lateinit var etPhoneNumber: EditText
    private lateinit var etEmail: EditText
    private lateinit var etPassword: EditText
    private lateinit var checkStudent: CheckBox

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        // Initialize the database helper
        databaseHelper = DatabaseHelper(this)

        // Initialize UI components
        etName = findViewById(R.id.et_name)
        etID = findViewById(R.id.et_userID)
        etPhoneNumber = findViewById(R.id.editTextPhone)
        etEmail = findViewById(R.id.editTextTextEmailAddress)
        etPassword = findViewById(R.id.editTextTextPassword)
        checkStudent = findViewById(R.id.checkStudent)

        // Init registration logic
        initRegistration()
    }

    private fun initRegistration() {
        val btnRegister = findViewById<Button>(R.id.btn_finishSignup)

        btnRegister.setOnClickListener {
            val email = etEmail.text.toString()
            val password = etPassword.text.toString()
            val phoneNumber = etPhoneNumber.text.toString()
            val name = etName.text.toString()

            val idText = etID.text.toString()

            if (isValidRegistrationInput(name, idText, phoneNumber, email, password)) {
                try {
                    val id = idText.toInt()
                    if (!databaseHelper.checkUser(email.trim())) {
                        val user = User(name, id, password, phoneNumber, email, null)
                        databaseHelper.addUser(user)
                        Toast.makeText(this, getString(R.string.success_message), Toast.LENGTH_SHORT).show()

                        val targetClass = if (checkStudent.isChecked) {
                            StudentRegisterActivity::class.java
                        } else {
                            MainActivity::class.java
                        }
                        val nextActivityIntent = Intent(this, targetClass)
                        startActivity(nextActivityIntent)

                        // Clear input fields
                        clearInputFields()
                    } else {
                        Toast.makeText(this, "Failed user registration", Toast.LENGTH_SHORT).show()
                    }
                } catch (e: NumberFormatException) {
                    Toast.makeText(this, "Invalid ID format. Please enter a valid number.", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
    private fun isValidRegistrationInput(
        name: String,
        idText: String,
        phoneNumber: String,
        email: String,
        password: String
    ): Boolean {
        if (email.isEmpty() || password.isEmpty() || name.isEmpty() || phoneNumber.isEmpty() || idText.isEmpty()) {
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
            return false
        }
        return true
    }

    private fun clearInputFields() {
        etName.text.clear()
        etID.text.clear()
        etPhoneNumber.text.clear()
        etEmail.text.clear()
        etPassword.text.clear()
    }
}
