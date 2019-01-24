package com.jaregier.nbateamviewer.data.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "players")
data class PlayerEntity (
        @PrimaryKey val id: Int,
        @ColumnInfo(name = "team_id") val teamId: Int,
        @ColumnInfo(name = "first_name")
        val firstName: String?,
        @ColumnInfo(name = "last_name")
        val lastName: String?,
        val position: String?,
        val number: Int?
)