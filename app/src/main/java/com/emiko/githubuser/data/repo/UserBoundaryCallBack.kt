package com.emiko.githubuser.data.repo

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.paging.PagedList
import com.emiko.githubuser.data.local.UsersDatabase
import com.emiko.githubuser.data.models.UsersItem
import com.emiko.githubuser.data.models.UsersResponse
import com.emiko.githubuser.data.remote.ApiService
import com.emiko.githubuser.utils.Constants
import com.emiko.githubuser.utils.NetworkState
import com.emiko.githubuser.utils.Resource
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import retrofit2.Response
import java.util.concurrent.atomic.AtomicBoolean

class UserBoundaryCallBack(
    private val db: UsersDatabase,
    private val apiService : ApiService,
    private val compositeDisposable: CompositeDisposable,
    private val query: String,
    private val networkState: MutableLiveData<NetworkState>
): PagedList.BoundaryCallback<UsersItem>() {

    private var page = Constants.FIRST_PAGE
    private var isRequestRunning = AtomicBoolean(false)

    val TAG = "UserBoundaryCallBack"

    override fun onZeroItemsLoaded() {
        super.onZeroItemsLoaded()
        getUsers()
    }

    override fun onItemAtEndLoaded(itemAtEnd: UsersItem) {
        super.onItemAtEndLoaded(itemAtEnd)
        getUsers()
    }


    private fun getUsers(){
        if(isRequestRunning.get()){
            return
        }
        try {
            networkState.postValue(NetworkState.LOADING)
            compositeDisposable.add(
                apiService.getUser(query, page).subscribeOn(Schedulers.io())
                    .subscribe(
                        {
                            isRequestRunning.set(true)
                            val resource = handleUsersResponse(it)
                            when(resource){
                                is Resource.Success -> {
                                    if (resource.data!!.items.isNotEmpty()) {
                                        db.usersDao().createOrUpdateUsers((resource.data.items))
                                    }
                                    page++
                                    isRequestRunning.set(false)
                                    networkState.postValue(NetworkState.LOADED)
                                }
                                is Resource.Error -> {
                                    isRequestRunning.set(false)
                                    networkState.postValue(NetworkState.ENDOFLIST)
                                    Log.d(TAG, "Sucess -> ${resource.message}")
                                }
                                is Resource.Loading -> {

                                }
                            }
                            Log.d(TAG, "Success -> ${resource.message}")
                        },{
                            Log.d(TAG, "${it.message}")
                            networkState.postValue(NetworkState.ERROR)
                        }
                    )
            )

        }catch (e: Exception){
            Log.d(TAG, "${e.message}")
            networkState.postValue(NetworkState.ERROR)
        }

    }


    private fun handleUsersResponse(response: Response<UsersResponse>) : Resource<UsersResponse> {
        if(response.isSuccessful) {
            response.body()?.let { resultResponse ->
                return Resource.Success(resultResponse)
            }
        }
        return Resource.Error(message = response.message())
    }


}