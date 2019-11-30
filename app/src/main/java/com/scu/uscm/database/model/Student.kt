package com.scu.uscm.database.model

import android.os.Parcelable
import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity
data class Student(
    @PrimaryKey val id: String = "",
    val name: String? = "",
    val email: String? = "",
    val department: String? = "",
    val grade: Int? = -1,
    val _class: String? = ""
) : Parcelable