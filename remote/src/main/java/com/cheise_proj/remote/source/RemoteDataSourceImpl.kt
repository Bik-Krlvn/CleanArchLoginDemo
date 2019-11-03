package com.cheise_proj.remote.source

import com.cheise_proj.data.model.UserData
import com.cheise_proj.data.model.UserProfileData
import com.cheise_proj.data.repository.RemoteDataSource
import com.cheise_proj.remote.api.ApiService
import com.cheise_proj.remote.mapper.user.UserDataRemoteMapper
import com.cheise_proj.remote.mapper.user.UserProfileDataRemoteMapper
import io.reactivex.Completable
import io.reactivex.Observable
import java.util.logging.Level
import java.util.logging.Logger
import javax.inject.Inject

/**
 * Remote source implementation from data module
 *
 * @property apiService
 * @property userDataRemoteMapper
 * @property userProfileDataRemoteMapper
 */
class RemoteDataSourceImpl @Inject constructor(
    private val apiService: ApiService,
    private val userDataRemoteMapper: UserDataRemoteMapper,
    private val userProfileDataRemoteMapper: UserProfileDataRemoteMapper
) : RemoteDataSource {
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
                println("remote auth status response: ${it.status} message: ${it.message}")
                userDataRemoteMapper.from(it.userNetwork)
            }
    }

    /**
     * Get userProfileData from remote
     *
     * @param identifier type int i.e. user id
     * @return userProfileData from remote
     */
    override fun fetchUserProfile(identifier: String): Observable<UserProfileData> {
        return apiService.fetchUserProfile(identifier)
            .map {
                Logger.getLogger("fetchUserProfile")
                    .log(Level.INFO, "remote profile status response: ${it.status}")
//                println("remote profile status response: ${it.status}" )
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
        identifier: String,
        oldPass: String,
        newPass: String
    ): Completable {
        val observable = apiService.requestUserChangePassword(identifier, oldPass, newPass)
            .map {
                println("remote change-password status: ${it.status} message: ${it.message}")
            }
        return Completable.fromObservable(observable)
    }
}