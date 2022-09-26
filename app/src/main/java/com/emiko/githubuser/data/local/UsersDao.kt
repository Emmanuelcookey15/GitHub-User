package com.emiko.githubuser.data.local

import androidx.paging.DataSource
import androidx.room.*
import com.emiko.githubuser.data.models.UsersItem
import io.reactivex.Flowable
import io.reactivex.Maybe

@Dao
interface UsersDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun createOrUpdateUsers(usersItem: List<UsersItem>)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun updateUser(usersItem: UsersItem)

    @Query("SELECT * FROM users")
    fun getAllUsers(): Flowable<List<UsersItem>>

    @Query("SELECT * FROM users")
    fun getUserListPaged(): DataSource.Factory<Int, UsersItem>

    @Query("Select * from users where favourite= :fav")
    fun userByFav(fav: Boolean): Flowable<List<UsersItem>>

    @Query("Select * from users where id= :usersItemId LIMIT 1")
    fun userByID(usersItemId: Int): Maybe<UsersItem>

    @Query("DELETE FROM users WHERE id= :usersItemId")
    fun deleteUser(usersItemId: Int)

    @Query("DELETE FROM users")
    fun deleteAllUsers()
}