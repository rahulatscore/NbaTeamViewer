package com.jaregier.nbateamviewer.ui.teams

import androidx.lifecycle.ViewModel
import com.jaregier.nbateamviewer.data.network.Team
import com.jaregier.nbateamviewer.domain.TeamsUseCase
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject
import javax.inject.Inject

class TeamListViewModel @Inject constructor(private val teamsUseCase: TeamsUseCase) : ViewModel() {

    val teamsSubject = BehaviorSubject.create<List<Team>>()

    private val compositeDisposable = CompositeDisposable()

    fun fetchTeams() {
        compositeDisposable.addAll(teamsUseCase.getTeams()
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .subscribe({ teams ->
                    if (teams != null) teamsSubject.onNext(teams)
                }, {
                    // handle errors
                })
        )
    }

    override fun onCleared() {
        super.onCleared()

        compositeDisposable.clear()
    }
}

