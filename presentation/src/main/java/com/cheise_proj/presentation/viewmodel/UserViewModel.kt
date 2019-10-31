package com.cheise_proj.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.toLiveData
import com.cheise_proj.domain.useCases.user.UserAuthenticationTask
import com.cheise_proj.domain.useCases.user.UserChangePasswordTask
import com.cheise_proj.domain.useCases.user.UserProfileTask
import com.cheise_proj.presentation.mapper.user.UserEntityMapper
import com.cheise_proj.presentation.mapper.user.UserProfileEntityMapper
import com.cheise_proj.presentation.model.Resource
import com.cheise_proj.presentation.model.User
import com.cheise_proj.presentation.model.UserProfile
import com.cheise_proj.presentation.viewmodel.base.BaseViewModel
import io.reactivex.BackpressureStrategy
import io.reactivex.Observable
import io.reactivex.functions.Function
import javax.inject.Inject

/**
 * User View Model
 * @author Kelvin Birikorang
 * @property UserViewModel
 * @constructor dependency injection
 * @param authenticationTask
 * @param userProfileTask
 * @param changePasswordTask
 * @param userEntityMapper
 */
class UserViewModel @Inject constructor(
    private val authenticationTask: UserAuthenticationTask,
    private val userProfileTask: UserProfileTask,
    private val changePasswordTask: UserChangePasswordTask,
    private val userEntityMapper: UserEntityMapper,
    private val userProfileEntityMapper: UserProfileEntityMapper
) : BaseViewModel() {

    /**
     * authenticateUser
     * @param username
     * @param password
     * @return LiveData<Resource<User>> return user live data resource wrapper
     */
    fun authenticateUser(username: String, password: String): LiveData<Resource<User>> {
        return authenticationTask.buildUseCase(
            UserAuthenticationTask.AuthParams(
                username,
                password
            )
        ).map {
            userEntityMapper.to(it)
        }
            .map {
                Resource.success(it)
            }
            .startWith(Resource.loading())
            .onErrorResumeNext(
                Function {
                    Observable.just(Resource.error(it.localizedMessage))
                }
            )
            .toFlowable(BackpressureStrategy.LATEST)
            .toLiveData()
    }

    fun getUserProfile(identifier: Int): LiveData<Resource<UserProfile>> {
        return userProfileTask.buildUseCase(identifier)
            .map {
                userProfileEntityMapper.to(it)
            }
            .map {
                Resource.success(it)
            }
            .startWith(Resource.loading())
            .onErrorResumeNext(
                Function {
                    Observable.just(Resource.error(it.localizedMessage))
                }
            )
            .toFlowable(BackpressureStrategy.LATEST)
            .toLiveData()
    }

    fun changeUserPassword(identifier: Int, oldPass: String, newPass: String) {
        disposable.add(
            changePasswordTask.buildUseCase(
                UserChangePasswordTask.ChangePasswordParams(
                    identifier,
                    oldPass,
                    newPass
                )
            )
                .onErrorComplete()
                .subscribe()
        )
    }
}