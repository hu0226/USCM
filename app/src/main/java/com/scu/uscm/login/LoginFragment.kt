package com.scu.uscm.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.afollestad.materialdialogs.MaterialDialog
import com.scu.uscm.R
import com.scu.uscm.database.local.UscmDao
import com.scu.uscm.database.local.UscmDatabase
import com.scu.uscm.database.model.Schedule
import com.scu.uscm.database.model.Student
import com.scu.uscm.database.remote.Firebase
import kotlinx.android.synthetic.main.frag_login.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class LoginFragment : Fragment() {

    private var db: UscmDatabase? = null
    private var uscmDao: UscmDao? = null
    private lateinit var scheduleList: MutableList<Schedule>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        context?.let {
            db = UscmDatabase.getInstance(it)
            uscmDao = db?.uscmDao()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? =
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
            val pattern = Regex("[a-zA-Z0-9]")
            if (!pattern.containsMatchIn(edt_student_id.text.toString())) {
                Toast.makeText(context, "請輸入正確學號", Toast.LENGTH_SHORT).show()
                return
            }
            if (edt_grade.text.toString().toInt() !in 1..4) {
                Toast.makeText(context, "請輸入正確年級", Toast.LENGTH_SHORT).show()
                return
            }
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

        Firebase.instance.setStudent(student)
        Firebase.instance.setSchedual(student.id, Schedule("CS001", "計算機概論", "08", "", false))
        Firebase.instance.setSchedual(student.id, Schedule("CS001", "計算機概論", "09", "", false))
        Firebase.instance.setSchedual(student.id, Schedule("CS001", "計算機概論", "10", "", false))
        Firebase.instance.setSchedual(student.id, Schedule("CS002", "資料結構", "11", "", false))
        Firebase.instance.setSchedual(student.id, Schedule("CS002", "資料結構", "13", "", false))
        Firebase.instance.setSchedual(student.id, Schedule("CS002", "資料結構", "14", "", false))
        Firebase.instance.setSchedual(student.id, Schedule("CS003", "作業系統", "15", "", false))
        Firebase.instance.setSchedual(student.id, Schedule("CS003", "作業系統", "16", "", false))

        Firebase.instance.setSchedual(
            student.id,
            Schedule("CS001", "計算機概論", "25", "2019/11/30 08:02", true)
        )
        Firebase.instance.setSchedual(
            student.id,
            Schedule("CS001", "計算機概論", "26", "2019/11/30 09:00", true)
        )
        Firebase.instance.setSchedual(
            student.id,
            Schedule("CS002", "資料結構", "27", "2019/11/30 10:10", true)
        )
        Firebase.instance.setSchedual(
            student.id,
            Schedule("CS002", "資料結構", "28", "2019/11/30 11:25", true)
        )
        Firebase.instance.setSchedual(
            student.id,
            Schedule("CS003", "作業系統", "29", "2019/11/30 13:02", true)
        )
        Firebase.instance.setSchedual(
            student.id,
            Schedule("CS003", "作業系統", "30", "2019/11/30 14:15", true)
        )
        Firebase.instance.setSchedual(
            student.id,
            Schedule("CS004", "編譯器", "31", "2019/11/29 09:10", true)
        )
        Firebase.instance.setSchedual(
            student.id,
            Schedule("CS004", "編譯器", "32", "2019/11/29 10:01", true)
        )
        Firebase.instance.setSchedual(
            student.id,
            Schedule("CS005", "使用者經驗設計", "33", "2019/11/29 15:03", true)
        )
        Firebase.instance.setSchedual(
            student.id,
            Schedule("CS005", "使用者經驗設計", "34", "2019/11/29 16:08", true)
        )
        Firebase.instance.setSchedual(
            student.id,
            Schedule("CS006", "線性代數", "35", "2019/11/28 10:01", true)
        )
        Firebase.instance.setSchedual(
            student.id,
            Schedule("CS006", "線性代數", "36", "2019/11/28 11:00", true)
        )
        Firebase.instance.setSchedual(
            student.id,
            Schedule("CS007", "離散數學", "37", "2019/11/28 13:04", true)
        )
        Firebase.instance.setSchedual(
            student.id,
            Schedule("CS007", "離散數學", "38", "2019/11/28 14:12", true)
        )
        Firebase.instance.setSchedual(student.id, Schedule("CS008", "密碼學", "39", "", false))
        Firebase.instance.setSchedual(
            student.id,
            Schedule("CS008", "密碼學", "40", "2019/11/27 10:13", true)
        )
        Firebase.instance.setSchedual(
            student.id,
            Schedule("CS009", "戀愛心理學", "41", "2019/11/27 13:02", true)
        )
        Firebase.instance.setSchedual(
            student.id,
            Schedule("CS009", "戀愛心理學", "42", "2019/11/27 14:11", true)
        )
        Firebase.instance.setSchedual(
            student.id,
            Schedule("CS010", "資訊素養與倫理", "43", "2019/11/27 15:00", true)
        )
        Firebase.instance.setSchedual(
            student.id,
            Schedule("CS010", "資訊素養與倫理", "44", "2019/11/27 16:05", true)
        )

        findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToMessageDialog())
    }
}