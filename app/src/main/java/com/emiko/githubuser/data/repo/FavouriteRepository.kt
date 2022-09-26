package com.emiko.githubuser.data.repo

import com.emiko.githubuser.data.models.UsersItem
import io.reactivex.Flowable
import io.reactivex.Maybe

interface FavouriteRepository {

    fun myUsers(): Flowable<List<UsersItem>>

    fun saveUser(user: UsersItem)

    fun getUserById(id: Int): Maybe<UsersItem>
}