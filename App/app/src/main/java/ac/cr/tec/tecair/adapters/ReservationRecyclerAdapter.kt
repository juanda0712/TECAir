package ac.cr.tec.tecair.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ac.cr.tec.tecair.R
import ac.cr.tec.tecair.models.ReservationInfo

class ReservationRecyclerAdapter(
    private val reservationInfoList: List<ReservationInfo>
) : RecyclerView.Adapter<ReservationRecyclerAdapter.ViewHolder>() {

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): ViewHolder {
        val v = LayoutInflater.from(viewGroup.context).inflate(R.layout.item_reservation_recycler, viewGroup, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, i: Int) {
        val reservationInfo = reservationInfoList[i]
        viewHolder.bind(reservationInfo)
    }

    override fun getItemCount(): Int {
        return reservationInfoList.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val itemImage: ImageView = itemView.findViewById(R.id.itemImage)
        private val itemPassengerName: TextView = itemView.findViewById(R.id.passengerName)
        private val itemReservationID: TextView = itemView.findViewById(R.id.reservationId)
        private val itemFlightNumber: TextView = itemView.findViewById(R.id.flightNumber)
        private val itemOriginDestination: TextView = itemView.findViewById(R.id.originDestination)
        private val itemExecutionDate: TextView = itemView.findViewById(R.id.executionDate)
        private val itemExecutionPrice: TextView = itemView.findViewById(R.id.executionPrice)

        fun bind(reservationInfo: ReservationInfo) {
            itemPassengerName.text = "Passenger Name: ${reservationInfo.passengerName ?: "Unknown"}"
            itemReservationID.text = "Reservation ID: ${reservationInfo.reservationID}"
            itemFlightNumber.text = "Flight Number: ${reservationInfo.flightNumber ?: "Unknown"}"
            itemOriginDestination.text = "Origin - Destination: ${reservationInfo.originDestination ?: "Unknown"}"
            itemExecutionDate.text = "Execution Date: ${reservationInfo.executionDate ?: "Unknown"}"
            itemExecutionPrice.text = "Execution Price: ${reservationInfo.executionPrice ?: "Unknown"}"

            // You can set the itemImage here based on your requirements
            itemImage.setImageResource(R.drawable.baseline_airplane_ticket_24)
        }
    }
}
