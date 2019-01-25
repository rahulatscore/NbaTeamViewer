package com.jaregier.nbateamviewer.di

import com.jaregier.nbateamviewer.ui.players.PlayerActivity
import com.jaregier.nbateamviewer.ui.teams.MainActivity
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [MyModule::class, ViewModelModule::class])
interface MyComponent {
    fun inject(activity: MainActivity)
    fun inject(activity: PlayerActivity)
}