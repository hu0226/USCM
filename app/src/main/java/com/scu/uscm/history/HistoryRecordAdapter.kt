package com.scu.uscm.history

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.scu.uscm.R
import com.scu.uscm.database.model.Schedule
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_history_record.*

class HistoryRecordAdapter(var signHistoryList: MutableList<Schedule>) :
    RecyclerView.Adapter<HistoryRecordAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.item_history_record, parent, false)
    )

    override fun getItemCount() = signHistoryList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.class_name.text = signHistoryList[position].className
        holder.signed_time.text = signHistoryList[position].signTime
    }

    class ViewHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView),
        LayoutContainer

    fun updateData(upDateSignHistoryList: MutableList<Schedule>) {
        signHistoryList = upDateSignHistoryList
        notifyDataSetChanged()
    }
}