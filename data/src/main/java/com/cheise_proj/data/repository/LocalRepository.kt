package com.cheise_proj.data.repository

import com.cheise_proj.data.model.UserData
import com.cheise_proj.data.model.UserProfileData
import io.reactivex.Completable
import io.reactivex.Observable

/**
 * Local Repository
 * @author Kelvin Birikorang
 * @desc Local persistence
 */
interface LocalRepository {

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
    fun getUserDataWithCredentials(username: String, password: String): Observable<UserData>

    /**
     * getUserDataWithCredentials
     * @param identifier
     * @return Observable<UserData>
     */
    fun getUserDataWithIdentifier(identifier: Int): Observable<UserData>

    /**
     * getProfileData
     * @param identifier
     * @return Observable<UserProfileData>
     */
    fun getProfileData(identifier: Int): Observable<UserProfileData>

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
     * @return Completable
     */
    fun updateUserPassword(identifier: Int, oldPass: String, newPass: String): Completable
    //endregion
}