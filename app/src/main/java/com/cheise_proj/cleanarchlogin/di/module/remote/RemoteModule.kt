package com.cheise_proj.cleanarchlogin.di.module.remote

import com.cheise_proj.cleanarchlogin.utils.BASE_URL
import com.cheise_proj.data.model.UserData
import com.cheise_proj.data.model.UserProfileData
import com.cheise_proj.data.repository.RemoteDataSource
import com.cheise_proj.remote.api.ApiService
import com.cheise_proj.remote.mapper.base.Mapper
import com.cheise_proj.remote.mapper.user.UserDataRemoteMapper
import com.cheise_proj.remote.mapper.user.UserProfileDataRemoteMapper
import com.cheise_proj.remote.model.user.UserNetwork
import com.cheise_proj.remote.model.user.UserProfileNetwork
import com.cheise_proj.remote.source.RemoteDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module(includes = [RemoteModule.Binders::class])
class RemoteModule {

    @Module
    interface Binders {
        @Binds
        fun bindRemoteDataSourceImpl(remoteDataSourceImpl: RemoteDataSourceImpl): RemoteDataSource

        @Binds
        fun bindUserDataRemoteMapper(userDataRemoteMapper: UserDataRemoteMapper):
                Mapper<UserData, UserNetwork>

        @Binds
        fun bindUserProfileDataRemoteMapper(userProfileDataRemoteMapper: UserProfileDataRemoteMapper):
                Mapper<UserProfileData, UserProfileNetwork>
    }

    @Singleton
    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .build()
    }

    @Provides
    fun provideOkttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .retryOnConnectionFailure(true)
            .writeTimeout(5, TimeUnit.MINUTES)
            .readTimeout(5, TimeUnit.MINUTES)
            .connectTimeout(5, TimeUnit.MINUTES)
            .build()
    }

    @Singleton
    @Provides
    fun provideApiService(retrofit: Retrofit): ApiService = retrofit.create(ApiService::class.java)

}