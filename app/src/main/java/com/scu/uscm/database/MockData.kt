package com.scu.uscm.database

import com.scu.uscm.database.model.Event
import com.scu.uscm.database.model.Schedule
import com.scu.uscm.database.model.Student
import com.scu.uscm.database.remote.Firebase

fun getEventMockData() : MutableList<Event> {
    val eventList: MutableList<Event> = mutableListOf()
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

    return eventList
}

fun setSchedule(student: Student) {
    Firebase.instance.setStudent(student)
    Firebase.instance.setSchedual(student.id, Schedule("CS001", "計算機概論", "08", "", false))
    Firebase.instance.setSchedual(student.id, Schedule("CS001", "計算機概論", "09", "", false))
    Firebase.instance.setSchedual(student.id, Schedule("CS001", "計算機概論", "10", "", false))
    Firebase.instance.setSchedual(student.id, Schedule("CS002", "資料結構", "11", "", false))
    Firebase.instance.setSchedual(student.id, Schedule("CS002", "資料結構", "13", "", false))
    Firebase.instance.setSchedual(student.id, Schedule("CS002", "資料結構", "14", "", false))
    Firebase.instance.setSchedual(student.id, Schedule("CS003", "作業系統", "15", "", false))
    Firebase.instance.setSchedual(student.id, Schedule("CS003", "作業系統", "16", "", false))

    Firebase.instance.setSchedual(student.id, Schedule("CS001", "計算機概論", "25", "2019/11/30 08:02", true))
    Firebase.instance.setSchedual(student.id, Schedule("CS001", "計算機概論", "26", "2019/11/30 09:00", true))
    Firebase.instance.setSchedual(student.id, Schedule("CS002", "資料結構", "27", "2019/11/30 10:10", true))
    Firebase.instance.setSchedual(student.id, Schedule("CS002", "資料結構", "28", "2019/11/30 11:25", true))
    Firebase.instance.setSchedual(student.id, Schedule("CS003", "作業系統", "29", "2019/11/30 13:02", true))
    Firebase.instance.setSchedual(student.id, Schedule("CS003", "作業系統", "30", "2019/11/30 14:15", true))
    Firebase.instance.setSchedual(student.id, Schedule("CS004", "編譯器", "31", "2019/11/29 09:10", true))
    Firebase.instance.setSchedual(student.id, Schedule("CS004", "編譯器", "32", "2019/11/29 10:01", true))
    Firebase.instance.setSchedual(student.id, Schedule("CS005", "使用者經驗設計", "33", "2019/11/29 15:03", true))
    Firebase.instance.setSchedual(student.id, Schedule("CS005", "使用者經驗設計", "34", "2019/11/29 16:08", true))
    Firebase.instance.setSchedual(student.id, Schedule("CS006", "線性代數", "35", "2019/11/28 10:01", true))
    Firebase.instance.setSchedual(student.id, Schedule("CS006", "線性代數", "36", "2019/11/28 11:00", true))
    Firebase.instance.setSchedual(student.id, Schedule("CS007", "離散數學", "37", "2019/11/28 13:04", true))
    Firebase.instance.setSchedual(student.id, Schedule("CS007", "離散數學", "38", "2019/11/28 14:12", true))
    Firebase.instance.setSchedual(student.id, Schedule("CS008", "密碼學", "39", "", false))
    Firebase.instance.setSchedual(student.id, Schedule("CS008", "密碼學", "40", "2019/11/27 10:13", true))
    Firebase.instance.setSchedual(student.id, Schedule("CS009", "戀愛心理學", "41", "2019/11/27 13:02", true))
    Firebase.instance.setSchedual(student.id, Schedule("CS009", "戀愛心理學", "42", "2019/11/27 14:11", true))
    Firebase.instance.setSchedual(student.id, Schedule("CS010", "資訊素養與倫理", "43", "2019/11/27 15:00", true))
    Firebase.instance.setSchedual(student.id, Schedule("CS010", "資訊素養與倫理", "44", "2019/11/27 16:05", true))
}