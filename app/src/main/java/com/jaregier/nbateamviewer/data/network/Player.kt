package com.jaregier.nbateamviewer.data.network

import com.google.gson.annotations.SerializedName

data class Player (
        val id: Int?,
        @SerializedName("first_name")
        val firstName: String?,
        @SerializedName("last_name")
        val lastName: String?,
        val position: String?,
        val number: Int?
)