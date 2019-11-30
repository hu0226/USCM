package com.scu.uscm.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.scu.uscm.R
import com.scu.uscm.database.local.UscmDao
import com.scu.uscm.database.local.UscmDatabase

class ProfileFragment : Fragment() {

    private var db: UscmDatabase? = null
    private var uscmDao: UscmDao? = null

    init {
        context?.let {
            db = UscmDatabase.getInstance(it)
            uscmDao = db?.uscmDao()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.frag_profile, container, false)


        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}