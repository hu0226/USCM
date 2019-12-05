package com.scu.uscm.history

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.scu.uscm.R
import com.scu.uscm.database.local.UscmDao
import com.scu.uscm.database.local.UscmDatabase
import com.scu.uscm.database.model.Schedule
import com.scu.uscm.database.remote.Firebase
import kotlinx.android.synthetic.main.frag_history.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class HistoryFragment : Fragment() {

    private var db: UscmDatabase? = null
    private var uscmDao: UscmDao? = null
    private lateinit var historyRecordAdapter: HistoryRecordAdapter
    private var historyRecordList: MutableList<Schedule> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        context?.let {
            db = UscmDatabase.getInstance(it)
            uscmDao = db?.uscmDao()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        inflater.inflate(R.layout.frag_history, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        historyRecordAdapter = HistoryRecordAdapter(historyRecordList)
        recycler_history_record.layoutManager = LinearLayoutManager(view.context)
        recycler_history_record.adapter = historyRecordAdapter

        GlobalScope.launch {
            Firebase.instance.getSignHistory(db!!.uscmDao().getStudent().id)
        }

        Firebase.instance.liveSignHistory.observe(this@HistoryFragment, Observer{
            progressBar.visibility = View.GONE
            historyRecordList = it
            historyRecordAdapter.updateData(historyRecordList)
        })
    }
}
