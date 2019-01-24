package com.jaregier.nbateamviewer.data.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [TeamEntity::class, PlayerEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun teamDao(): TeamDao
    abstract fun playerDao(): PlayerDao
}