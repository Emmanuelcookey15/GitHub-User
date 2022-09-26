package com.emiko.githubuser.data.repo

import com.emiko.githubuser.data.local.UsersDatabase
import com.emiko.githubuser.data.models.UsersItem
import com.emiko.githubuser.data.remote.ApiService
import io.reactivex.Flowable
import io.reactivex.Maybe
import javax.inject.Inject

class FavouriteRepositoryImpl @Inject constructor(
    private val db: UsersDatabase
): FavouriteRepository {

    override fun myUsers(): Flowable<List<UsersItem>> {
        return db.usersDao().getAllUsers().switchMap{ Flowable.just(it) }
    }

    override fun saveUser(user: UsersItem) {
        db.usersDao().updateUser(user)
    }

    override fun getUserById(id: Int): Maybe<UsersItem> {
        return db.usersDao().userByID(id)
    }
}