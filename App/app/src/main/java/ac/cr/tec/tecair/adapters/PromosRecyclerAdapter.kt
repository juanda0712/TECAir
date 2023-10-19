package ac.cr.tec.tecair.adapters

import ac.cr.tec.tecair.R
import ac.cr.tec.tecair.models.Promotion
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class PromosRecyclerAdapter(private val listPromos: List<Promotion>): RecyclerView.Adapter<PromosRecyclerAdapter.ViewHolder>(){

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val view: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_promo_recycler, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, i: Int) {
        holder.textViewPromoNumber.text = listPromos[i].promoNumber.toString()
        holder.textViewPromoExecutionID.text = listPromos[i].promoExecID.toString()
        holder.textViewPromoPeriod.text = listPromos[i].promoPeriod
        //holder.textViewPromoPrice.text = listPromos[i].promoPrice //descomentar luego
    }

    override fun getItemCount(): Int {
        return listPromos.size
    }

    inner class ViewHolder(view: View): RecyclerView.ViewHolder(view){
        //Links each element of the Promotion list to a row in RecyclerView widget


        var textViewPromoNumber: TextView
        var textViewPromoExecutionID: TextView
        var textViewPromoPeriod: TextView
       // var textViewPromoPrice: TextView //descomentar luego

        init {
            textViewPromoNumber = view.findViewById<View>(R.id.textViewPromoNumber) as TextView
            textViewPromoExecutionID = view.findViewById<View>(R.id.textViewPromoExecID) as TextView
            textViewPromoPeriod = view.findViewById<View>(R.id.textViewPromoPeriod) as TextView
           // textViewPromoPrice = view.findViewById<View>(R.id.textViewPromoPrice) as TextView //descomentar luego
        }
    }
}

