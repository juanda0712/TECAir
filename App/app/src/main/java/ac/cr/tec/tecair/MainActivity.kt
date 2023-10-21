    package ac.cr.tec.tecair

    import android.app.Activity
    import android.content.Intent
    import androidx.appcompat.app.AppCompatActivity
    import android.os.Bundle
    import ac.cr.tec.tecair.databinding.ActivityMainBinding
    import android.widget.EditText
    import android.view.View
    import android.widget.Toast
    import ac.cr.tec.tecair.DatabaseHelper


    class MainActivity : AppCompatActivity() {
        private val activity = this@MainActivity
        private lateinit var databaseHelper: DatabaseHelper


        private lateinit var binding: ActivityMainBinding


        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            databaseHelper = DatabaseHelper(activity)
            binding = ActivityMainBinding.inflate(layoutInflater)
            setContentView(binding.root)
            // Init UI
            initUI()
        }

        private fun initUI(){

            binding.btnSignin.setOnClickListener{

            }

            binding.tvForgotpass.setOnClickListener{
                val intent = Intent(this, GridActivity::class.java)
                startActivity(intent)

            }

            binding.tvSignup.setOnClickListener{
                val intent = Intent(this, RegisterActivity::class.java)
                startActivity(intent)

            }


        }

        fun login(view: View){
            val email = findViewById<EditText>(R.id.et_email).text.toString()
            val password  = findViewById<EditText>(R.id.et_password).text.toString()

            //validacion del usuario en la DB
            if(databaseHelper!!.checkUser(email!!.trim {it <= ' '}, password!!.trim{it <= ' '})){
                emptyInputEditText()

                val intent= Intent(this, HomeActivity::class.java)
                startActivity(intent)
            } else {
                Toast.makeText(this, "Invalid email or password", Toast.LENGTH_SHORT).show()
            }
        }

        //Limpia el input
        private fun emptyInputEditText() {
            findViewById<EditText>(R.id.et_email)!!.text=null
            findViewById<EditText>(R.id.et_password)!!.text=null
        }
    }
