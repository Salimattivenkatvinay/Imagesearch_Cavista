package com.vinay.imagesearch.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.mrittica.utils.convertToDateString
import com.vinay.imagesearch.R
import java.util.*

class CommentsAdapter(private val listdata: ArrayList<HashMap<String, String>>) : RecyclerView.Adapter<CommentsAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val listItem =
            layoutInflater.inflate(R.layout.item_comment, parent, false)
        return ViewHolder(listItem)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = listdata[position]
        holder.tvComment.text = data["comment_text"]
        holder.tvTime.text = "-" + data["time_stamp"]?.toLong()?.convertToDateString("hh:mm a dd/MM/YYYY")
    }

    override fun getItemCount(): Int {
        return listdata.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tvComment: TextView
        var tvTime: TextView

        init {
            tvComment = itemView.findViewById(R.id.tvComment)
            tvTime = itemView.findViewById(R.id.tvTime)
        }
    }

}