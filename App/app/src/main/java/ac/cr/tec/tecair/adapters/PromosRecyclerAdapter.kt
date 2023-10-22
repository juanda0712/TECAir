package ac.cr.tec.tecair.adapters
import ac.cr.tec.tecair.R
import ac.cr.tec.tecair.models.Promotion
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class PromosRecyclerAdapter(private val promotions: List<Promotion>) : RecyclerView.Adapter<PromosRecyclerAdapter.ViewHolder>() {

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): ViewHolder {
        val v = LayoutInflater.from(viewGroup.context).inflate(R.layout.item_promo_recycler, viewGroup, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, i: Int) {
        val promotion = promotions[i]
        viewHolder.bind(promotion)
    }

    override fun getItemCount(): Int {
        return promotions.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val itemImage: ImageView = itemView.findViewById(R.id.itemImage)
        private val itemPrice: TextView = itemView.findViewById(R.id.itemPrice)
        private val itemPromoCode: TextView = itemView.findViewById(R.id.itemTitle)
        private val itemPromoValid: TextView = itemView.findViewById(R.id.itemDate)

        fun bind(promotion: Promotion) {
            itemPrice.text = "Discount: ${promotion.promoPrice}"
            itemPromoCode.text = "Promo Code: ${promotion.promoNumber}"
            itemPromoValid.text = "Valid through: ${promotion.promoPeriod}"
            itemImage.setImageResource(R.drawable.baseline_local_offer_24)
        }
    }
}
