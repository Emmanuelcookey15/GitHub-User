package com.emiko.githubuser.data.repo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.emiko.githubuser.data.local.UsersDatabase
import com.emiko.githubuser.data.models.UsersItem
import com.emiko.githubuser.data.remote.ApiService
import com.emiko.githubuser.utils.Constants.QUERY_PAGE_SIZE
import com.emiko.githubuser.utils.NetworkState
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class UsersRepositoryImpl @Inject constructor(
    private val db: UsersDatabase,
    private val apiService: ApiService,
): UsersRepository {

    override fun fetchOrGetUser(
        compositeDisposable: CompositeDisposable,
        query: String,
        networkState: MutableLiveData<NetworkState>
    ): LiveData<PagedList<UsersItem>> {
        val userBoundaryCallBack = UserBoundaryCallBack(db, apiService, compositeDisposable, query, networkState)


        return LivePagedListBuilder(db.usersDao().getUserListPaged(), QUERY_PAGE_SIZE)
            .setBoundaryCallback(userBoundaryCallBack)
            .build()

    }

}