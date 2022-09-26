package com.emiko.githubuser.ui.viewmodels

import androidx.lifecycle.ViewModel
import com.emiko.githubuser.data.models.UsersItem
import com.emiko.githubuser.data.repo.FavouriteRepositoryImpl
import com.emiko.githubuser.data.repo.UsersRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val favouriteRepositoryImpl: FavouriteRepositoryImpl
) : ViewModel() {

    private val compositeDisposable = CompositeDisposable()

    fun addToFavourite(usersItem: UsersItem) {
        compositeDisposable.add(
            favouriteRepositoryImpl.myUsers().map {
                favouriteRepositoryImpl.saveUser(usersItem)
            }.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe()
        )
    }


    override fun onCleared() {
        compositeDisposable.dispose()
        super.onCleared()
    }


}