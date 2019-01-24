package com.jaregier.nbateamviewer.data.network

import com.google.gson.annotations.SerializedName

data class Team(
        val wins : Int?,
        val losses: Int?,
        @SerializedName("full_name")
        val fullName: String?,
        val id: Int?,
        val players: List<Player>
)