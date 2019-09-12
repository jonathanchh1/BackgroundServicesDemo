package com.emi.boundservices

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Messages(var name : String?=null) : Parcelable