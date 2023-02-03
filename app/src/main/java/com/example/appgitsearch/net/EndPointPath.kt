package com.example.appgitsearch.net

import com.example.appgitsearch.model.Repository
import com.example.appgitsearch.model.User
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface EndPointPath {
    @GET("/users/{userName}")
    fun getUsers(
        @Path("userName")
        userName: String
    ) : Call<User>

    @GET("/users/{userName}/repos")
    fun getRepos(
        @Path("userName")
        userName: String
    ) : Call<List<Repository>>
}