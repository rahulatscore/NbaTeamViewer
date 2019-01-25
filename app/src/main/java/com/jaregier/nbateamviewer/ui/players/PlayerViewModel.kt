package com.jaregier.nbateamviewer.ui.players

import androidx.lifecycle.ViewModel
import com.jaregier.nbateamviewer.data.database.PlayerEntity
import com.jaregier.nbateamviewer.domain.PlayersUseCase
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject
import javax.inject.Inject

class PlayerViewModel @Inject constructor(private val playersUseCase: PlayersUseCase) : ViewModel() {
    var teamId: Int? = null

    val playersSubject = BehaviorSubject.create<List<PlayerEntity>>()

    private val compositeDisposable = CompositeDisposable()

    fun fetchPlayers() {
        val teamId = teamId ?: return
        compositeDisposable.addAll(playersUseCase.getPlayers(teamId)
                .observeOn(Schedulers.io())
                .subscribeOn(Schedulers.io())
                .subscribe ({
                    playersSubject.onNext(it)
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