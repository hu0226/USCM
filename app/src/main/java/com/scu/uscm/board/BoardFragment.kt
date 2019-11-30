package com.scu.uscm.board

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.scu.uscm.R
import com.scu.uscm.database.model.Event
import kotlinx.android.synthetic.main.frag_board.*

class BoardFragment : Fragment() {

    private lateinit var boardAdapter: BoardAdapter
    private lateinit var eventList: MutableList<Event>

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        inflater.inflate(R.layout.frag_board, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        eventList = mutableListOf()
        eventList.add(Event("經濟部工業局首度以跨領域合作【第六屆跨界超越競賽 】", "2019-09-10"))
        eventList.add(Event("2019年第13期「ERP專業資訊人才培育計畫」 開始接受報名了!!!（至 108年9月25日(三)截止）", "2019-09-06"))
        eventList.add(Event("【2019NASA黑客松台北場】歡迎系上同學踴躍組隊參加黑客松", "2019-08-12"))
        eventList.add(Event("108學年度資訊系-新生入學說明會", "2019-08-06"))
        eventList.add(Event("士林高級商業職業來校參訪", "2019-05-27"))
        eventList.add(Event("2019全球量化投資研究錦標賽(IQC)開跑囉! ", "2019-04-24"))
        eventList.add(Event("系友回娘家", "2019-04-16"))
        eventList.add(Event("107學年度第十屆校際軟體程式設計競賽", "2019-03-19"))
        eventList.add(Event("大數據在產銷模式之應用", "2018-03-23"))
        eventList.add(Event("106學年度 資訊科技與管理學系 系友回娘家", "2018-03-19"))
        eventList.add(Event("第9屆校際軟體程式競賽", "2018-02-08"))
        eventList.add(Event("解密如何進行社群資料分析與ifeel議題觀測", "2017-12-25"))
        eventList.add(Event("用Python打造第1個聊天機器人", "2017-12-12"))
        eventList.add(Event("程式設計輔導教學", "2017-12-06"))
        eventList.add(Event("學生社群-資訊安全與駭客", "2017-12-01"))
        eventList.add(Event("106學年度親師座談會", "2017-12-01"))
        eventList.add(Event("106學年度管院主辦之「大學好好玩-探索管院系列活動」活動開跑囉！", "2017-11-29"))
        eventList.add(Event("食字路口-士林夜市", " 2017-11-28"))
        eventList.add(Event("業界導師座談會", "2017-11-19"))
        eventList.add(Event("產業專題-校友業師技術指導", "017-11-16"))

        boardAdapter = BoardAdapter(eventList)
        recycler_board.layoutManager = LinearLayoutManager(view.context)
        recycler_board.adapter = boardAdapter
    }



}
