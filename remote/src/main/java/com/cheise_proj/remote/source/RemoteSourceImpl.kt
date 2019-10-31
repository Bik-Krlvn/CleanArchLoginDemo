package com.cheise_proj.remote.source

import com.cheise_proj.data.model.UserData
import com.cheise_proj.data.model.UserProfileData
import com.cheise_proj.data.repository.RemoteRepository
import com.cheise_proj.remote.api.ApiService
import com.cheise_proj.remote.mapper.user.UserDataRemoteMapper
import com.cheise_proj.remote.mapper.user.UserProfileDataRemoteMapper
import com.cheise_proj.remote.model.user.UserChangePasswordResponse
import io.reactivex.Completable
import io.reactivex.Observable
import timber.log.Timber
import timber.log.info
import javax.inject.Inject

/**
 * Remote source implementation from data module
 *
 * @property apiService
 * @property userDataRemoteMapper
 * @property userProfileDataRemoteMapper
 */
class RemoteSourceImpl @Inject constructor(
    private val apiService: ApiService,
    private val userDataRemoteMapper: UserDataRemoteMapper,
    private val userProfileDataRemoteMapper: UserProfileDataRemoteMapper
) : RemoteRepository {
    /**
     * retrieve userData from remote
     *
     * @param username type string input username
     * @param password type string input password
     * @return maps user data remote to observable userData wrapper
     */
    override fun fetchUserDataWithCredentials(
        username: String,
        password: String
    ): Observable<UserData> {

        return apiService.requestUserAuthentication(username, password)
            .map {
                Timber.info { "remote auth status response: ${it.status} message: ${it.message}" }
                it.userNetwork?.let { it1 -> userDataRemoteMapper.from(it1) }
            }
    }

    /**
     * Get userProfileData from remote
     *
     * @param identifier type int i.e. user id
     * @return userProfileData from remote
     */
    override fun fetchUserProfile(identifier: Int): Observable<UserProfileData> {
        return apiService.fetchUserProfile(identifier)
            .map {
                Timber.info { "remote profile status response: ${it.status}" }
                it.profileNetwork?.let { it1 -> userProfileDataRemoteMapper.from(it1) }
            }
    }

    /**
     * Request remote password change
     *
     * @param identifier type integer i.e. user id
     * @param oldPass type string input current password
     * @param newPass type string input new password
     * @return completable
     */
    override fun requestPasswordUpdate(
        identifier: Int,
        oldPass: String,
        newPass: String
    ): Completable {
        val observable = apiService.requestUserChangePassword(identifier, oldPass, newPass)
            .map {
                Timber.info { "remote change-password status: ${it.status} message: ${it.message}" }
            }
        return Completable.fromObservable(observable)
    }
}