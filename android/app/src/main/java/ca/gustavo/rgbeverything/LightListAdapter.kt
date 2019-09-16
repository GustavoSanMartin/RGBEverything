package ca.gustavo.rgbeverything

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class LightListAdapter(
    private val list: List<Light>,
    private val onClick: (String) -> Unit
) : RecyclerView.Adapter<LightViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LightViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.light_list_item, parent, false)

        return LightViewHolder(view)
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: LightViewHolder, position: Int) {
        holder.name.text = list[position].name
        holder.name.setOnClickListener { onClick(list[position].id) }
    }
}

class LightViewHolder(item: View) : RecyclerView.ViewHolder(item) {
    val name: TextView = item.findViewById(R.id.light_name)
}