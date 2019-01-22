package com.jaregier.nbateamviewer.di

import com.jaregier.nbateamviewer.data.TeamService
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class MyModule {
    @Singleton
    @Provides
    fun provideRetrofit() = Retrofit.Builder()
            .baseUrl("https://raw.githubusercontent.com/scoremedia/nba-team-viewer/master/")
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    @Provides
    fun provideTeamService(retrofit: Retrofit) = retrofit.create(TeamService::class.java)
}