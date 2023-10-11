package ac.cr.tec.tecair

import android.app.backup.BackupAgentHelper
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class RegisterActivity : AppCompatActivity() {

    private val activity = this@RegisterActivity
    private lateinit var databaseHelper: DatabaseHelper


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
    }
}