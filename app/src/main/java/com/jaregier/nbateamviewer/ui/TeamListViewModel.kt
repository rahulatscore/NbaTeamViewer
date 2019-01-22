package com.jaregier.nbateamviewer.ui

import android.arch.lifecycle.ViewModel
import com.jaregier.nbateamviewer.data.Team
import com.jaregier.nbateamviewer.data.TeamService
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject
import javax.inject.Inject

class TeamListViewModel @Inject constructor(private val teamService: TeamService) : ViewModel() {

    val teamsSubject = BehaviorSubject.create<List<Team>>()

    private val compositeDisposable = CompositeDisposable()

    fun fetchTeams() {
        compositeDisposable.addAll(teamService.getTeams()
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .subscribe { teams, _ ->
                    if (teams != null) teamsSubject.onNext(teams)
                }
        )
    }

    override fun onCleared() {
        super.onCleared()

        compositeDisposable.clear()
    }
}

