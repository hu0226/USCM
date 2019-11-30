package com.scu.uscm.database.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Schedule(
    var classId: String? = "",
    var className: String? = "",
    var classTime: String? = "",
    var signTime: Long? = -1L,
    var isSign: Boolean? = false
): Parcelable