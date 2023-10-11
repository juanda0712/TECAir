package ac.cr.tec.tecair
import ac.cr.tec.tecair.databinding.ActivityHomeBinding
import ac.cr.tec.tecair.fragments.FlightsFragment
import ac.cr.tec.tecair.fragments.PromosFragment
import android.content.Intent
import android.os.Bundle

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment


class HomeActivity: AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val flightsFragment = FlightsFragment()
        val promosFragment = PromosFragment()

        makeCurrentFragment(flightsFragment)

        binding.bottomNav.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.ic_vuelos -> makeCurrentFragment(flightsFragment)
                R.id.ic_promos -> makeCurrentFragment(promosFragment)
            }
            true
        }
    }

    private fun makeCurrentFragment(fragment: Fragment) =
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.fl_wrapper, fragment)
            commit()
        }

    fun goReg() {
        val intent = Intent(this, RegisterActivity::class.java)
        startActivity(intent)
    }
}
