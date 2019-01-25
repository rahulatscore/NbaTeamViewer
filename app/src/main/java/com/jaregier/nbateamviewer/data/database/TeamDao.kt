package com.jaregier.nbateamviewer.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.reactivex.Single

@Dao
interface TeamDao {
    @Query("SELECT * from teams")
    fun getTeams(): Single<List<TeamEntity>>

    @Query("SELECT * from teams WHERE id = :teamId")
    fun getTeam(teamId: Int): Single<TeamEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg team: TeamEntity)
}