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
 * User View Model, responsible for user view related ui
 *
 * @author Kelvin Birikorang
 * @constructor dependencies
 * @property authenticationTask
 * @property userProfileTask
 * @property changePasswordTask
 * @property userEntityMapper
 */
class UserViewModel @Inject constructor(
    private val authenticationTask: UserAuthenticationTask,
    private val userProfileTask: UserProfileTask,
    private val changePasswordTask: UserChangePasswordTask,
    private val userEntityMapper: UserEntityMapper,
    private val userProfileEntityMapper: UserProfileEntityMapper
) : BaseViewModel() {

    /**
     * Authorize user with provided credentials
     *
     * @param username string input of username
     * @param password string input of user password
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

    /**
     * Retrieve user profile in live data wrapper
     *
     * @param identifier user id
     * @return live data of user profile
     */
    fun getUserProfile(identifier: String): LiveData<Resource<UserProfile>> {
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

    /**
     * Request user password change
     *
     * @param identifier user id
     * @param oldPass current password
     * @param newPass new password
     */
    fun changeUserPassword(identifier: String, oldPass: String, newPass: String) {
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