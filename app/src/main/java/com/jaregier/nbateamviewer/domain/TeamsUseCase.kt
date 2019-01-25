package com.jaregier.nbateamviewer.domain

import com.jaregier.nbateamviewer.data.database.PlayerDao
import com.jaregier.nbateamviewer.data.database.PlayerEntity
import com.jaregier.nbateamviewer.data.database.TeamDao
import com.jaregier.nbateamviewer.data.database.TeamEntity
import com.jaregier.nbateamviewer.data.network.Team
import com.jaregier.nbateamviewer.data.network.TeamService
import io.reactivex.Flowable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class TeamsUseCase @Inject constructor(
        private val teamService: TeamService,
        private val teamDao: TeamDao,
        private val playerDao: PlayerDao
) {
    fun getTeams() = Flowable.concat(fetchTeamsFromDb().toFlowable(), fetchTeamsFromNetwork().toFlowable())
            .subscribeOn(Schedulers.io())

    fun getTeam(teamId: Int) = teamDao.getTeam(teamId)

    private fun fetchTeamsFromDb() = teamDao.getTeams()
            .flatMap { teams ->
                Flowable.fromIterable(teams)
                        .map { Team(it.wins, it.losses, it.fullName, it.id, emptyList()) } // Ideally you would do a join to get the players as well, but it's not needed for the UI in this case
                        .toList()
            }

    private fun fetchTeamsFromNetwork() = teamService.getTeams()
            .doAfterSuccess(::addTeamsToDatabase)
            .subscribeOn(Schedulers.io())

    private fun addTeamsToDatabase(teams: List<Team>) {
        val teamEntities = mutableListOf<TeamEntity>()
        val playerEntities = mutableListOf<PlayerEntity>()

        for (team in teams) {
            if (team.id != null) {
                teamEntities.add(TeamEntity(team.id, team.wins, team.losses, team.fullName))
                playerEntities.addAll(getPlayersForDatabase(team))
            }
        }

        teamDao.insertAll(*teamEntities.toTypedArray())
        playerDao.insertAll(*playerEntities.toTypedArray())

    }

    private fun getPlayersForDatabase(team: Team): List<PlayerEntity> {
        if (team.id == null) return emptyList()

        val playerEntities = mutableListOf<PlayerEntity>()

        for (player in team.players) {
            if (player.id != null) {
                playerEntities.add(PlayerEntity(player.id, team.id, player.firstName, player.lastName, player.position, player.number))
            }
        }

        return playerEntities
    }
}