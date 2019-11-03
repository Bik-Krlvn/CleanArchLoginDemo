package com.cheise_proj.cleanarchlogin.di.module.local

import android.content.Context
import com.cheise_proj.data.model.UserData
import com.cheise_proj.data.model.UserProfileData
import com.cheise_proj.data.repository.LocalDataSource
import com.cheise_proj.local.db.LocalAppDatabase
import com.cheise_proj.local.db.dao.UserDao
import com.cheise_proj.local.mapper.base.Mapper
import com.cheise_proj.local.mapper.user.UserDataLocalMapper
import com.cheise_proj.local.mapper.user.UserProfileDataLocalMapper
import com.cheise_proj.local.model.UserLocal
import com.cheise_proj.local.model.UserProfileLocal
import com.cheise_proj.local.source.LocalDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module(includes = [LocalModule.Binders::class])
class LocalModule {
    @Module
    interface Binders {
        @Binds
        fun bindUserDataLocalMapper(userDataLocalMapper: UserDataLocalMapper):
                Mapper<UserData, UserLocal>

        @Binds
        fun bindUserProfileDataLocalMapper(userProfileDataLocalMapper: UserProfileDataLocalMapper):
                Mapper<UserProfileData, UserProfileLocal>

        @Binds
        fun bindLocalDataSourceImpl(localDataSourceImpl: LocalDataSourceImpl): LocalDataSource
    }

    @Singleton
    @Provides
    fun provideLocalAppDatabase(context: Context): LocalAppDatabase =
        LocalAppDatabase.getInstance(context)

    @Singleton
    @Provides
    fun provideUserDao(localAppDatabase: LocalAppDatabase): UserDao = localAppDatabase.userDao()
}