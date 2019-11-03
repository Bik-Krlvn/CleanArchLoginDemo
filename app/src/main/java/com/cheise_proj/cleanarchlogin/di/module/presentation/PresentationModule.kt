package com.cheise_proj.cleanarchlogin.di.module.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.cheise_proj.cleanarchlogin.di.key.ViewModelKey
import com.cheise_proj.domain.model.UserEntity
import com.cheise_proj.domain.model.UserProfileEntity
import com.cheise_proj.presentation.factory.ViewModelFactory
import com.cheise_proj.presentation.mapper.base.Mapper
import com.cheise_proj.presentation.mapper.user.UserEntityMapper
import com.cheise_proj.presentation.mapper.user.UserProfileEntityMapper
import com.cheise_proj.presentation.model.User
import com.cheise_proj.presentation.model.UserProfile
import com.cheise_proj.presentation.viewmodel.UserViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module(includes = [PresentationModule.Binders::class])
class PresentationModule {

    @Module
    interface Binders {
        @Binds
        fun bindUserEntityMapper(userEntityMapper: UserEntityMapper): Mapper<UserEntity, User>

        @Binds
        fun bindUserProfileEntityMapper(userProfileEntityMapper: UserProfileEntityMapper):
                Mapper<UserProfileEntity, UserProfile>

        @Binds
        fun bindViewModelFactory(viewModelFactory: ViewModelFactory): ViewModelProvider.Factory

        @Binds
        @ViewModelKey(UserViewModel::class)
        @IntoMap
        fun bindUserViewModel(userViewModel: UserViewModel):ViewModel
    }


}