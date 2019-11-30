package com.scu.uscm.board

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.scu.uscm.R
import com.scu.uscm.database.model.Event
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_board_event.*

class BoardAdapter(var eventList: MutableList<Event>) : RecyclerView.Adapter<BoardAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.item_board_event, parent, false)
    )

    override fun getItemCount(): Int = eventList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.event_name.text = eventList[position].eventName
        holder.event_time.text = eventList[position].eventTime
    }

    class ViewHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView),
        LayoutContainer

}