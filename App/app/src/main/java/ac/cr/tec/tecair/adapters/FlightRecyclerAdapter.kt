package ac.cr.tec.tecair.adapters

import ac.cr.tec.tecair.models.Flight
import ac.cr.tec.tecair.R
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class FlightRecyclerAdapter(private val listFlights: List<Flight>): RecyclerView.Adapter<FlightRecyclerAdapter.ViewHolder>()  {
    //Adapter entre la lista de vuelos y el AndroidRecyclerView que mostrará los resultados

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
       val view: View = LayoutInflater.from(parent.context)
           .inflate(R.layout.item_flight_recycler, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, pos: Int) {
        holder.textViewID.text = listFlights[pos].number.toString()
        holder.textViewOrigin.text= listFlights[pos].origin.toString()
        holder.textViewDestination.text= listFlights[pos].destination.toString()
    }

    override fun getItemCount(): Int {
        return listFlights.size
    }

    inner class ViewHolder(view: View): RecyclerView.ViewHolder(view){
        //Links each element of the Flights list to a row in RecyclerView widget


        var textViewID: TextView
        var textViewOrigin: TextView
        var textViewDestination: TextView

        init {
            textViewID = view.findViewById<View>(R.id.textViewCodigo) as TextView
            textViewOrigin = view.findViewById<View>(R.id.textViewDescuento) as TextView
            textViewDestination = view.findViewById<View>(R.id.textViewVencimiento) as TextView
        }
    }



}
