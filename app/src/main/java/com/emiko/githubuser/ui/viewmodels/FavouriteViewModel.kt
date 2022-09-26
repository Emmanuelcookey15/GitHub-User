package com.emiko.githubuser.ui.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.emiko.githubuser.data.models.UsersItem
import com.emiko.githubuser.data.repo.FavouriteRepositoryImpl
import com.emiko.githubuser.utils.Resource
import com.emiko.githubuser.utils.userFriendlyResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

@HiltViewModel
class FavouriteViewModel @Inject constructor(
    private val favouriteRepositoryImpl: FavouriteRepositoryImpl
) : ViewModel() {

    private val compositeDisposable = CompositeDisposable()
    val listOfFavouriteUsers = MutableLiveData<Resource<List<UsersItem>>>()

    init {
       favouriteUsers(true)
    }

    fun favouriteUsers(fav: Boolean) {
        compositeDisposable.add(
            favouriteRepositoryImpl.myUsers().map {
                it.filter {
                    it.favourite == fav
                }
            }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe (
                    {
                    listOfFavouriteUsers.postValue(Resource.Success(it))
                }, { error ->
                        listOfFavouriteUsers.postValue(
                            Resource.Error(cause = userFriendlyResponse(error))
                        )
                })
        )
    }

    fun addToFavourite(usersItem: UsersItem) {
        compositeDisposable.add(
            favouriteRepositoryImpl.myUsers().map {
                favouriteRepositoryImpl.saveUser(usersItem)
            }.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe ()
        )
    }

    override fun onCleared() {
        compositeDisposable.dispose()
        super.onCleared()
    }

}