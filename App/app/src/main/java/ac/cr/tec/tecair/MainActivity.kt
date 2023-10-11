package ac.cr.tec.tecair

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import ac.cr.tec.tecair.databinding.ActivityMainBinding
import android.widget.EditText
import android.view.View
import android.widget.Toast


class MainActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //init UI
        initUI()
    }

    private fun initUI(){
        binding.fabBack.setOnClickListener{

        }
        binding.btnSignin.setOnClickListener{

        }

        binding.tvForgotpass.setOnClickListener{

        }

        binding.tvSignup.setOnClickListener{

        }


    }

    fun login(view: View){
        val email = findViewById<EditText>(R.id.et_email).text.toString()
        val password  = findViewById<EditText>(R.id.et_password).text.toString()

        //validacion del usuario en la DB
        if(email== "user@email.com" && password== "password"){
            val intent= Intent(this, HomeActivity::class.java)
            startActivity(intent)
        } else {
            Toast.makeText(this, "Invalid email or password", Toast.LENGTH_SHORT).show()
        }

    }
}
