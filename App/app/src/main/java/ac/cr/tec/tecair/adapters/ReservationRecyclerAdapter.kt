package ac.cr.tec.tecair.adapters

import ReservationInfo
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ac.cr.tec.tecair.R
import ac.cr.tec.tecair.databinding.ItemReservationRecyclerBinding
import android.widget.ImageView
import androidx.databinding.DataBindingUtil


class ReservationRecyclerAdapter : RecyclerView.Adapter<ReservationRecyclerAdapter.ViewHolder>() {
    private var reservationInfoList: MutableList<ReservationInfo> = mutableListOf()

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): ViewHolder {
        val v = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.item_reservation_recycler, viewGroup, false)
        return ViewHolder(v)
    }


    override fun onBindViewHolder(viewHolder: ViewHolder, i: Int) {
        // Update the ViewHolder with flight data
        val reservationInfo = reservationInfoList[i]

        viewHolder.bind(reservationInfo)
    }

    override fun getItemCount(): Int {
        return reservationInfoList.size
    }

    fun setReservationInfoList(reservations: List<ReservationInfo>) {
        reservationInfoList.addAll(reservations)
        notifyDataSetChanged()
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val itemImage:ImageView = itemView.findViewById(R.id.itemImage)
        private val itemFlightNumber:TextView = itemView.findViewById(R.id.itemFlightNumber)
        private val itemDate:TextView= itemView.findViewById(R.id.itemDate)
        private val itemPrice:TextView= itemView.findViewById(R.id.itemPrice)
        private val oriDest:TextView= itemView.findViewById(R.id.OriDest)

            //bind the reservationInfo data to the ViewHolder
            fun bind(reservationInfo: ReservationInfo) {
                itemFlightNumber.text = "Flight Number: ${reservationInfo.flightNumber}"
                itemDate.text = "${reservationInfo.date}"
                itemPrice.text = "${reservationInfo.price}"
                oriDest.text = "${reservationInfo.origin} - ${reservationInfo.destination}"
                // Set the image resource
                itemImage.setImageResource(R.drawable.baseline_airplane_ticket_24)
            }
        }
    }
