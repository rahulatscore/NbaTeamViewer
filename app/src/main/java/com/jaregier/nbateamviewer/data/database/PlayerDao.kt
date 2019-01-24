package com.jaregier.nbateamviewer.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.reactivex.Single

@Dao
interface PlayerDao {

    @Query("SELECT * from players WHERE team_id = :teamId")
    fun getPlayers(teamId: Int): Single<List<PlayerEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg player: PlayerEntity)
}