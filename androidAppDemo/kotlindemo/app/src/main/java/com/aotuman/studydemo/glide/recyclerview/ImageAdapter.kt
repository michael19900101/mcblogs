package com.aotuman.studydemo.glide.recyclerview

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.aotuman.studydemo.R
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.glide_recycler_item.view.*

class ImageAdapter(private val myDataset: MutableList<String>) :
    RecyclerView.Adapter<ImageAdapter.ImageViewHolder>() {

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder.
    // Each data item is just a string in this case that is shown in a TextView.
    class ImageViewHolder(val layout: ViewGroup) : RecyclerView.ViewHolder(layout) {
        val imageView = layout.image
        val textView = layout.tv_num
    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(parent: ViewGroup,
                                    viewType: Int): ImageAdapter.ImageViewHolder {
        // create a new view
        val layout = LayoutInflater.from(parent.context)
            .inflate(R.layout.glide_recycler_item, parent, false) as ViewGroup
        // set the view's size, margins, paddings and layout parameters
        return ImageViewHolder(layout)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        holder.textView.text = (position + 1).toString()
        Glide.with(holder.imageView)
            .load(myDataset[position])
            .into(holder.imageView)
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = myDataset.size
}