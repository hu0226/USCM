package com.scu.uscm.database.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Schedule(
    var classId: String? = "",
    var className: String? = "",
    var classTime: String = "",
    var signTime: String = "",
    var isSign: Boolean? = false
): Parcelable