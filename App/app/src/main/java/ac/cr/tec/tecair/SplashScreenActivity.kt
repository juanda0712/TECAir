package ac.cr.tec.tecair

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import android.content.Intent
import android.os.Handler
import android.os.Looper


class SplashScreenActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Set the custom layout for the splash screen
        setContentView(R.layout.activity_splash_screen)

        // Delay the transition to MainActivity using a Handler
        Handler(Looper.getMainLooper()).postDelayed({
            // Create an Intent to start the MainActivity
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)

            // Finish the SplashScreenActivity
            finish()
        }, 3000) // Adjust the delay time as needed (in milliseconds)
    }
}


