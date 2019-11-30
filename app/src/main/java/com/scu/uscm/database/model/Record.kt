package com.scu.uscm.database.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Record(
    var className: String? = "",
    var time: Long? = -1L,
    var isSign: Boolean? = false
): Parcelable