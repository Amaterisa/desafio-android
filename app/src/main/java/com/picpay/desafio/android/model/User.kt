package com.picpay.desafio.android.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

data class User(
    val img: String,
    val name: String,
    val id: Int,
    val username: String
)