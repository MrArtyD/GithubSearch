package com.example.githubsearch.data.items

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class User(
    val id: Long,

    val login: String,

    @SerializedName("avatar_url")
    val avatarUrl: String,

    @SerializedName("html_url")
    val accountUrl: String
) : Parcelable
