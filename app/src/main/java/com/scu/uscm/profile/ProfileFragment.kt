package com.scu.uscm.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.scu.uscm.R
import com.scu.uscm.database.local.UscmDao
import com.scu.uscm.database.local.UscmDatabase
import com.scu.uscm.database.model.Student
import kotlinx.android.synthetic.main.frag_profile.*
import kotlinx.coroutines.*

class ProfileFragment : Fragment() {

    private var db: UscmDatabase? = null
    private var uscmDao: UscmDao? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        context?.let {
            db = UscmDatabase.getInstance(it)
            uscmDao = db?.uscmDao()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        inflater.inflate(R.layout.frag_profile, container, false)


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getData()
    }

    private fun getData() {
        GlobalScope.launch {
            val student: Student? = db?.uscmDao()?.getStudent()

            withContext(Dispatchers.Main) {
                tv_name.text = student?.name
                tv_st_id.text = student?.id
                tv_class.text = "${student?.department}  ${student?.grade}年${student?._class}班"
                tv_mail.text = student?.email
            }
        }
    }

}