package com.scu.uscm.board

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.scu.uscm.R
import com.scu.uscm.database.getEventMockData
import kotlinx.android.synthetic.main.frag_board.*

class BoardFragment : Fragment() {

    private lateinit var boardAdapter: BoardAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        inflater.inflate(R.layout.frag_board, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        boardAdapter = BoardAdapter(getEventMockData())
        recycler_board.layoutManager = LinearLayoutManager(view.context)
        recycler_board.adapter = boardAdapter
    }
}
