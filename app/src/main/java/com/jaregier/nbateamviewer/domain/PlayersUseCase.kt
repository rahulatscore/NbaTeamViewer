package com.jaregier.nbateamviewer.domain

import com.jaregier.nbateamviewer.data.database.PlayerDao
import javax.inject.Inject

class PlayersUseCase @Inject constructor(private val playerDao: PlayerDao){

    fun getPlayers(teamId: Int) = playerDao.getPlayers(teamId)
}