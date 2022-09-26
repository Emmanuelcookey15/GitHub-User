package com.emiko.githubuser.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.emiko.githubuser.data.models.UsersItem

@Database(entities = [UsersItem::class], version = 1, exportSchema = false)
abstract class UsersDatabase : RoomDatabase() {

    abstract fun usersDao(): UsersDao

}