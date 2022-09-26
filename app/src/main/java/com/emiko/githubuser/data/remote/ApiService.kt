package com.emiko.githubuser.data.remote

import com.emiko.githubuser.data.models.UsersResponse
import io.reactivex.Observable
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("search/users")
    fun getUser(
        @Query("q") location: String,
        @Query("page") pageNumber: Int = 1
    ): Observable<Response<UsersResponse>>

}
