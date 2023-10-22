package ac.cr.tec.tecair.adapters

import ac.cr.tec.tecair.models.Flight
import ac.cr.tec.tecair.R
import ac.cr.tec.tecair.databinding.ItemFlightRecyclerBinding
import ac.cr.tec.tecair.models.Execution
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class FlightRecyclerAdapter : RecyclerView.Adapter<FlightRecyclerAdapter.ViewHolder>() {

    private val flights: MutableList<Flight> = mutableListOf()
    private val executions: MutableList<Execution> = mutableListOf()
    private var onItemClickListener: ((Flight, Execution) -> Unit)? = null

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): ViewHolder {
        val v = LayoutInflater.from(viewGroup.context).inflate(R.layout.item_flight_recycler, viewGroup, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, i: Int) {
        // Update the ViewHolder with flight data
        val flight = flights[i]
        val execution = executions[i]
        viewHolder.bind(flight, execution)
    }

    override fun getItemCount(): Int {
        return flights.size
    }

    fun setFlightsAndExecutions(newFlights: List<Flight>, newExecutions: List<Execution>) {
        flights.clear()
        flights.addAll(newFlights)
        executions.clear()
        executions.addAll(newExecutions)
        notifyDataSetChanged()
    }

    fun setOnItemClickListener(listener: (Flight, Execution) -> Unit) {
        onItemClickListener = listener
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val itemImage: ImageView = itemView.findViewById(R.id.itemImage)
        private val itemTitle: TextView = itemView.findViewById(R.id.itemTitle)
        private val itemDate: TextView = itemView.findViewById(R.id.itemDate)
        private val itemPrice: TextView = itemView.findViewById(R.id.itemPrice)

        // Bind the flight data to the ViewHolder
        fun bind(flight: Flight, execution: Execution) {
            itemTitle.text = "${flight.origin} - ${flight.destination}"
            itemDate.text = "Date: ${execution.date}"
            itemPrice.text = "Price: ${execution.price}"
            itemImage.setImageResource(R.drawable.ic_baseline_airplane)

            // Set click listener for the item view
            itemView.setOnClickListener {
                onItemClickListener?.invoke(flight, execution)
            }
        }
    }
}
