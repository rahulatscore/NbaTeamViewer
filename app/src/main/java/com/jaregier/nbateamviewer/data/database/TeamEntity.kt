package com.jaregier.nbateamviewer.data.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "teams")
data class TeamEntity (
        @PrimaryKey val id: Int,
        val wins : Int?,
        val losses: Int?,
        @ColumnInfo(name = "full_name") val fullName: String?
)