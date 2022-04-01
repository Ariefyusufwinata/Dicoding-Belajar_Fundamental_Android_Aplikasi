package com.rief.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class UserGithub(
    var username : String?,
    var name : String?,
    var location : String?,
    var repository : String?,
    var company : String?,
    var followers : String?,
    var following : String?,
    var avatar : Int
) : Parcelable
