package com.testm.demosdk.view.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.testm.demosdk.R
import com.testm.demosdk.model.SoundItemUi


//why can't i insert it in?
interface OnClickListener {
    fun onClick(item: SoundItemUi)
}

class SoundsAdapter(private var dataSet: Array<SoundItemUi>, private val onClickListener: OnClickListener? = null) :

    RecyclerView.Adapter<SoundsAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val textView: TextView = view.findViewById(R.id.textView)

    }

    fun setItems(items: Array<SoundItemUi>) {
        dataSet = items
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {

        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.sound_item, viewGroup, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {

        val item = dataSet[position]

        viewHolder.textView.text = item.soundItem.name

        viewHolder.itemView.setOnClickListener {
            onClickListener?.onClick(item)
        }
    }

    override fun getItemCount() = dataSet.size

}
