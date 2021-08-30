package com.example.githubsearch.api

import com.example.githubsearch.api.responses.GithubResponseUsers
import retrofit2.http.GET
import retrofit2.http.Query

interface GithubApi {
    companion object{
        const val BASE_URL = "https://api.github.com/"
    }


    @GET("search/users")
    suspend fun searchUsers(
        @Query("q") query: String,
        @Query("per_page") perPage: Int,
        @Query("page") page: Int
    ) : GithubResponseUsers
}