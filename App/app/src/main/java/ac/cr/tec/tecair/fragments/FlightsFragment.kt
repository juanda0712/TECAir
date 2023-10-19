package ac.cr.tec.tecair.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ac.cr.tec.tecair.R
import ac.cr.tec.tecair.DatabaseHelper
import ac.cr.tec.tecair.DBContract
import ac.cr.tec.tecair.models.Airport
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import android.widget.AdapterView

/**
 * A simple [Fragment] subclass.
 */
class FlightsFragment : Fragment() {
    private lateinit var viewOfLayout: View
    private lateinit var airports: MutableList<String>
    private lateinit var databaseHelper: DatabaseHelper

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        viewOfLayout = inflater.inflate(R.layout.fragment_flights, container, false)
        databaseHelper = DatabaseHelper(requireActivity())
        airports = mutableListOf<String>()

        val ap1 = Airport("SJO", "San José")
        databaseHelper.addAirport(ap1)

        consultarAP()

        val spinOrigen = viewOfLayout.findViewById<Spinner>(R.id.origen)
        spinOrigen?.adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item, airports)
        spinOrigen.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                Toast.makeText(context, "Origen es: " + airports[p2], Toast.LENGTH_SHORT).show()
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                // Implement this method if needed
            }
        }

        val spinDestino = viewOfLayout.findViewById<Spinner>(R.id.destino)
        spinDestino?.adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item, airports)
        spinDestino.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                Toast.makeText(context, "Destino es: " + airports[p2], Toast.LENGTH_SHORT).show()
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                // Implement this method if needed
            }
        }

        return viewOfLayout
    }

    // Consulta Aeropuerto
    private fun consultarAP() {
        val db = databaseHelper.readableDatabase
        val cursor = db.rawQuery("select * from " + DBContract.AirportEntry.TABLE_NAME, null)

        while (cursor.moveToNext()) {
            var ap = Airport(cursor.getString(0), cursor.getString(1))
            airports.add(ap.name + ", " + ap.city)
        }
    }
}
