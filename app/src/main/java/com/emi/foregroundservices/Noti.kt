package com.emi.foregroundservices

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class Noti(var title : String?=null,  var body : String?=null) : Parcelable