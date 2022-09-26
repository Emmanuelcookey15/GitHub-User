package com.emiko.githubuser.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.emiko.githubuser.data.models.UsersItem
import com.emiko.githubuser.utils.NetworkState
import com.emiko.githubuser.data.repo.UsersRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(
    private val usersRepositoryImpl: UsersRepositoryImpl
) : ViewModel() {

    private val compositeDisposable = CompositeDisposable()

    val state = MutableLiveData<NetworkState>()

    fun usersPagedList(query: String): LiveData<PagedList<UsersItem>> =
        usersRepositoryImpl.fetchOrGetUser(compositeDisposable, query, state)

    override fun onCleared() {
        compositeDisposable.dispose()
        super.onCleared()
    }

}