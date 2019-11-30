package com.scu.uscm.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.scu.uscm.R
import com.scu.uscm.database.model.Student
import kotlinx.android.synthetic.main.frag_login.*

class LoginFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        inflater.inflate(R.layout.frag_login, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btn_login.setOnClickListener {
            saveData()
        }
    }

    private fun saveData() {
        val student = Student(
            edt_student_id.text.toString(),
            edt_student_name.text.toString(),
            edt_email.text.toString(),
            edt_department.text.toString(),
            edt_grade.text.toString().toInt(),
            edt_class.text.toString()
        )
    }
}