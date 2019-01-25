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

    var currentSortOrder = SortOptions.ALPHABETICAL
        set(value) {
            field = value
            sort()
        }

    fun fetchTeams() {
        compositeDisposable.addAll(teamsUseCase.getTeams()
                .map { sortTeams(it) }
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .subscribe({ teams ->
                    if (teams != null) teamsSubject.onNext(teams)
                }, {
                    // handle errors
                })
        )
    }

    private fun sort() {
        val sortedList = sortTeams(teamsSubject.value)
        sortedList?.let {
            teamsSubject.onNext(it)
        }
    }

    private fun sortTeams(teams: List<Team>?): List<Team>? {
        return when (currentSortOrder) {
            SortOptions.ALPHABETICAL -> teams?.sortedBy { it.fullName }
            SortOptions.WINS -> teams?.sortedByDescending { it.wins }
            SortOptions.LOSSES -> teams?.sortedByDescending { it.losses }
        }
    }

    override fun onCleared() {
        super.onCleared()

        compositeDisposable.clear()
    }
}

enum class SortOptions {
    ALPHABETICAL,
    WINS,
    LOSSES
}

