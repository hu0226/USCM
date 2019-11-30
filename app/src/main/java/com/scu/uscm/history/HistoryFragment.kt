package com.scu.uscm.history

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.scu.uscm.R
import kotlinx.android.synthetic.main.frag_history.*

class HistoryFragment : Fragment() {

    private lateinit var historyRecordAdapter: HistoryRecordAdapter
//    private lateinit var historyRecordList: MutableList<>

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        inflater.inflate(R.layout.frag_history, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        historyRecordAdapter = HistoryRecordAdapter()
        recycler_history_record.layoutManager = LinearLayoutManager(view.context)
        recycler_history_record.adapter = historyRecordAdapter
    }
}
