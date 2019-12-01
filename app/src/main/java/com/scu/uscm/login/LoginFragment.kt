package com.scu.uscm.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.afollestad.materialdialogs.MaterialDialog
import com.scu.uscm.R
import com.scu.uscm.database.local.UscmDao
import com.scu.uscm.database.local.UscmDatabase
import com.scu.uscm.database.model.Student
import com.scu.uscm.database.setSchedule
import kotlinx.android.synthetic.main.frag_login.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class LoginFragment : Fragment() {

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
        inflater.inflate(R.layout.frag_login, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btn_login.setOnClickListener {
            checkColumn()
        }
    }

    private fun checkColumn() {
        if (
            edt_student_id.text.isNullOrBlank() ||
            edt_student_name.text.isNullOrBlank() ||
            edt_email.text.isNullOrBlank() ||
            edt_department.text.isNullOrBlank() ||
            edt_grade.text.isNullOrBlank() ||
            edt_class.text.isNullOrBlank()
        ) {
            context?.let {
                MaterialDialog.Builder(it)
                    .title(resources.getString(R.string.dialog_title))
                    .alwaysCallSingleChoiceCallback()
                    .canceledOnTouchOutside(true)
                    .cancelable(true)
                    .positiveText(resources.getString(R.string.dialog_sure))
                    .onPositive { dialog, _ ->
                        dialog.dismiss()
                    }
                    .show()
            }
        } else {
            saveData()
        }
    }

    private fun saveData() {
        val student = Student(
            edt_student_id.text.toString().trim(),
            edt_student_name.text.toString().trim(),
            edt_email.text.toString().trim(),
            edt_department.text.toString().trim(),
            edt_grade.text.toString().trim().toInt(),
            edt_class.text.toString().trim()
        )

        GlobalScope.launch {
            db?.uscmDao()?.insertStudent(student)
        }

        setSchedule(student)

        findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToMessageDialog())
    }
}