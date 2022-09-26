package com.emiko.githubuser.data.repo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.PagedList
import com.emiko.githubuser.data.models.UsersItem
import com.emiko.githubuser.utils.NetworkState
import io.reactivex.disposables.CompositeDisposable

interface UsersRepository {

    fun fetchOrGetUser(
        compositeDisposable: CompositeDisposable,
        query: String,
        networkState: MutableLiveData<NetworkState>
    ): LiveData<PagedList<UsersItem>>


}