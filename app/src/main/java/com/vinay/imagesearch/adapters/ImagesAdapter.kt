package com.vinay.imagesearch.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.vinay.imagesearch.CommentsActivity
import com.vinay.imagesearch.R
import com.vinay.imagesearch.models.ImageModel

class ImagesAdapter(var mItemList: List<ImageModel?>?, var context: Context) :
    RecyclerView.Adapter<ViewHolder>() {

    private val VIEW_TYPE_ITEM = 0
    private val VIEW_TYPE_LOADING = 1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return if (viewType == VIEW_TYPE_ITEM) {
            val view: View = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_row, parent, false)
            ItemViewHolder(view)
        } else {
            val view: View = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_loading, parent, false)
            LoadingViewHolder(view)
        }
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        if (viewHolder is ItemViewHolder) {
            populateItemRows(viewHolder, position)
        } else if (viewHolder is LoadingViewHolder) {
            showLoadingView(viewHolder, position)
        }
    }

    override fun getItemCount(): Int {
        return if (mItemList == null) 0 else mItemList!!.size
    }

    /**
     * The following method decides the type of ViewHolder to display in the RecyclerView
     *
     * @param position
     * @return
     */
    override fun getItemViewType(position: Int): Int {
        return if (mItemList!![position] == null) VIEW_TYPE_LOADING else VIEW_TYPE_ITEM
    }

    private inner class ItemViewHolder(itemView: View) : ViewHolder(itemView) {
        var tvItem: TextView = itemView.findViewById(R.id.tvItem)
        var ivItem: ImageView = itemView.findViewById(R.id.ivItem)
    }

    private inner class LoadingViewHolder(itemView: View) : ViewHolder(itemView) {
        var progressBar: ProgressBar = itemView.findViewById(R.id.progressBar)
    }

    private fun showLoadingView(viewHolder: LoadingViewHolder, position: Int) {
        //ProgressBar would be displayed
    }

    private fun populateItemRows(viewHolder: ItemViewHolder, position: Int) {
        val item = mItemList!![position]
        viewHolder.tvItem.text = item?.title

        Glide.with(context)
            .load(item?.link)
            .thumbnail(.1f)
            .into(viewHolder.ivItem);

        viewHolder.ivItem.setOnClickListener {
            context.startActivity(
                Intent(context, CommentsActivity::class.java)
                    .putExtra("data", item)
            )
        }
    }

}