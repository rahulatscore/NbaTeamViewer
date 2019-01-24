package com.jaregier.nbateamviewer.di

import androidx.room.Room
import com.jaregier.nbateamviewer.MyApplication
import com.jaregier.nbateamviewer.data.database.AppDatabase
import com.jaregier.nbateamviewer.data.network.TeamService
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

    @Provides
    @Singleton
    fun provideDatabase() = Room.databaseBuilder(
            MyApplication.getInstance(),
            AppDatabase::class.java, "nba-database"
    ).build()

    @Provides
    fun provideTeamDao(appDatabase: AppDatabase) = appDatabase.teamDao()

    @Provides
    fun providePlayerDao(appDatabase: AppDatabase) = appDatabase.playerDao()
}