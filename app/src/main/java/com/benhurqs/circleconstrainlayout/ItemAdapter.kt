package com.benhurqs.circleconstrainlayout

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import kotlinx.android.synthetic.main.item_list.view.*

/**
 * Created by benhursouza on 04/04/18.
 */
class ItemAdapter(private val myDataset: List<ItemObj>) :
        RecyclerView.Adapter<ItemAdapter.ViewHolder>() {

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder.
    // Each data item is just a string in this case that is shown in a TextView.
    class ViewHolder(var view: View) : RecyclerView.ViewHolder(view)


    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(parent: ViewGroup,
                                    viewType: Int): ItemAdapter.ViewHolder {
        // create a new view
        val layout = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_list, parent, false)
        // set the view's size, margins, paddings and layout parameters
        return ViewHolder(layout)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        var itemobj = myDataset[position]
        (holder.view.findViewById<View>(R.id.img) as ImageView).setImageResource(itemobj.img)
        (holder.view.findViewById<View>(R.id.text) as TextView).text = itemobj.text
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = myDataset.size

}