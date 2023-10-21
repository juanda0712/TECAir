package ac.cr.tec.tecair.adapters
import ac.cr.tec.tecair.models.Flight
import ac.cr.tec.tecair.R
import ac.cr.tec.tecair.databinding.ItemFlightRecyclerBinding
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class PromosRecyclerAdapter: RecyclerView.Adapter<PromosRecyclerAdapter.ViewHolder>(){

    val titles= arrayOf("Promo1", "Promo2"," Promo3")
    val details= arrayOf("Details1", "Details2", "Details3")
    val images= intArrayOf(R.drawable.baseline_local_offer_24,R.drawable.baseline_local_offer_24,R.drawable.baseline_local_offer_24)

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): ViewHolder {
        val v = LayoutInflater.from(viewGroup.context).inflate(R.layout.item_promo_recycler, viewGroup, false)
        return  ViewHolder(v)

    }
    override fun onBindViewHolder(viewHolder: ViewHolder, i: Int) {
        viewHolder.itemTitle.text= titles[i]
        viewHolder.itemDetail.text= details[i]
        viewHolder.itemImage.setImageResource(images[i])
    }
    override fun getItemCount(): Int {
        return titles.size
    }
    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        var itemImage: ImageView
        var itemTitle: TextView
        var itemDetail: TextView

        init {
            itemImage= itemView.findViewById(R.id.itemImage)
            itemTitle= itemView.findViewById(R.id.itemTitle)
            itemDetail= itemView.findViewById(R.id.itemDetail)
        }

    }

}
