package com.cheise_proj.cleanarchlogin.di.module.data

import com.cheise_proj.data.mapper.base.Mapper
import com.cheise_proj.data.mapper.user.UserEntityDataMapper
import com.cheise_proj.data.mapper.user.UserProfileEntityDataMapper
import com.cheise_proj.data.model.UserData
import com.cheise_proj.data.model.UserProfileData
import com.cheise_proj.data.repository.user.UserRepositoryImpl
import com.cheise_proj.domain.model.UserEntity
import com.cheise_proj.domain.model.UserProfileEntity
import com.cheise_proj.domain.repository.UserRepository
import dagger.Binds
import dagger.Module

@Module(includes = [DataModule.Binders::class])
class DataModule {
    @Module
    interface Binders {
        @Binds
        fun bindUserRepositoryImpl(userRepositoryImpl: UserRepositoryImpl): UserRepository

        @Binds
        fun bindUserEntityDataMapper(userEntityDataMapper: UserEntityDataMapper):
                Mapper<UserEntity, UserData>

        @Binds
        fun bindUserProfileEntityDataMapper(userProfileEntityDataMapper: UserProfileEntityDataMapper):
                Mapper<UserProfileEntity, UserProfileData>
    }
}