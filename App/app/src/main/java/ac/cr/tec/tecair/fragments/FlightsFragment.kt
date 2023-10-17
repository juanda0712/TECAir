package ac.cr.tec.tecair.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ac.cr.tec.tecair.R
import ac.cr.tec.tecair.DatabaseHelper
import ac.cr.tec.tecair.models.Airport

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

        val ap1 = Airport(3564, "AeroJachudo","Buenos Aires", "Argentina")
        databaseHelper.addAP(ap1)
        val ap2 = Airport(4985, "PaloRalo","Sydney", "Australia")
        databaseHelper.addAP(ap2)

        consultarAP()



        val spinOrigen = viewOfLayout.findViewById<Spinner>(R.id.origen)
        spinOrigen?.adapter = ArrayAdapter(requireContext(), androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, airports) as SpinnerAdapter
        spinOrigen.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                Toast.makeText(context, "Origen es: " + airports[p2], Toast.LENGTH_SHORT).show()
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")
            }
        }

        val spinDestino = viewOfLayout.findViewById<Spinner>(R.id.destino)
        spinDestino?.adapter = ArrayAdapter(requireContext(), androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, airports) as SpinnerAdapter
        spinDestino.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                Toast.makeText(context, "Destino es: " + airports[p2], Toast.LENGTH_SHORT).show()
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")
            }
        }




        return viewOfLayout
    }

    //Consulta Aeropuerto
    private fun consultarAP() {


        val db = databaseHelper.readableDatabase
        val cursor = db.rawQuery("select * from " + DBContract.AeropuertoEntry.TABLE_NAME, null)

        while (cursor.moveToNext()){
            var ap = Airport(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getString(3))

            airports.add(ap.ciudad + ", " + ap.pais)
        }
    }


}