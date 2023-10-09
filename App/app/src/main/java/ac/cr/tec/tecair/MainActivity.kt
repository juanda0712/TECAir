package ac.cr.tec.tecair

import android.os.Bundle
import androidx.activity.ComponentActivity
import android.view.View
import android.widget.EditText
import android.content.Intent
import android.widget.Toast


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

    }

    fun login(view: View) {
        val email = findViewById<EditText>(R.id.emailInput).text.toString()
        val password = findViewById<EditText>(R.id.passwordInput).text.toString()

        //verificacion de la existencia del usuario en la DB
        if(email == "a@email.com" && password == "password" ) {
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
        } else{
            Toast.makeText(this, "Invalid e-mail or password. Try again.", Toast.LENGTH_SHORT).show()
        }

    }

}