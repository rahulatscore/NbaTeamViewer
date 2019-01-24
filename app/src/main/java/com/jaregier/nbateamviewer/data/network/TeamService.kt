package com.jaregier.nbateamviewer.data.network

import io.reactivex.Single
import retrofit2.http.GET

interface TeamService {

    @GET("input.json")
    fun getTeams(): Single<List<Team>>
}