package com.jaregier.nbateamviewer.di

import com.jaregier.nbateamviewer.MainActivity
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [MyModule::class, ViewModelModule::class])
interface MyComponent {
    fun inject(activity: MainActivity)
}