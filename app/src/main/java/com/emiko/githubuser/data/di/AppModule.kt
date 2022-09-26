package com.emiko.githubuser.data.di


import android.app.Application
import androidx.room.Room
import com.emiko.githubuser.BuildConfig
import com.emiko.githubuser.data.local.UsersDatabase
import com.emiko.githubuser.data.remote.ApiService
import com.emiko.githubuser.utils.Constants.TIME_OUT
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {


    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        val loggerInterceptor = HttpLoggingInterceptor().apply {
            level = if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY
            else HttpLoggingInterceptor.Level.NONE
        }

        return OkHttpClient.Builder()
            .callTimeout(TIME_OUT, TimeUnit.SECONDS)
            .addInterceptor(loggerInterceptor)
            .build()

    }

    // Base URLs
    @Singleton
    @Provides
    fun provideRetrofit(gson: Gson, okHttpClient: OkHttpClient) =
        Retrofit.Builder()
            .client(okHttpClient)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(gson))



    // Gson
    @Provides
    fun provideGson(): Gson = GsonBuilder().setLenient().create()


    @Provides
    @Singleton
    fun provideResultServiceApi(retrofit: Retrofit.Builder): ApiService =
        retrofit.baseUrl(BuildConfig.BASE_URL).build().create(ApiService::class.java)


    @Provides
    @Singleton
    fun provideDatabase(app: Application) : UsersDatabase =
        Room.databaseBuilder(app, UsersDatabase::class.java, "UsersDatabase")
            .build()

}