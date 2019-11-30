package com.scu.uscm.database.model

import android.os.Parcelable
import androidx.room.Entity
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity
data class Event (
    val eventName: String? = "",
    val eventTime: String? = ""
) : Parcelable