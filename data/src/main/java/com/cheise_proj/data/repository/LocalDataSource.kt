package com.cheise_proj.data.repository

import com.cheise_proj.data.model.UserData
import com.cheise_proj.data.model.UserProfileData
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single

/**
 * Local Repository
 * @author Kelvin Birikorang
 * @desc Local persistence
 */
interface LocalDataSource {

    //region User actions
    /**
     * saveUserData
     * @param userData
     * @return Unit
     */
    fun saveUserData(userData: UserData)

    /**
     * getUserDataWithCredentials
     * @param username
     * @param password
     * @return Observable<UserData>
     */
    fun getUserDataWithCredentials(username: String, password: String): Single<UserData>

    /**
     * getUserDataWithCredentials
     * @param identifier
     * @return Observable<UserData>
     */
    fun getUserDataWithIdentifier(identifier: String): Single<UserData>

    /**
     * getProfileData
     * @param identifier
     * @return Observable<UserProfileData>
     */
    fun getProfileData(identifier: String): Single<UserProfileData>

    /**
     * saveUserProfile
     * @param userProfileData
     */
    fun saveUserProfile(userProfileData: UserProfileData)

    /**
     * updateUserPassword
     * @param identifier
     * @param oldPass
     * @param newPass
     * @return single type integer
     */
    fun updateUserPassword(identifier: String, oldPass: String, newPass: String): Single<Int>
    //endregion
}