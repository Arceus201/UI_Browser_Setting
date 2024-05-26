package com.example.ui_browser_setting

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class UrlData(
    var url: String? = ""
) : Parcelable
