package com.cheise_proj.data.repository

import com.cheise_proj.data.model.UserData
import com.cheise_proj.data.model.UserProfileData
import io.reactivex.Completable
import io.reactivex.Observable

/**
 * RemoteDataSource
 * @author Kelvin Birikorang
 * @desc Retrieve remote data
 */
interface RemoteDataSource {

    //region User
    /**
     * fetchUserDataWithCredentials
     * @param username
     * @param password
     * @return Observable<UserData> returns rx observable
     */
    fun fetchUserDataWithCredentials(username: String, password: String): Observable<UserData>

    /**
     * fetchUserProfile
     * @param identifier provide identifier (i.e. user id) for user
     * @return Observable<UserProfileData> returns rx observable
     */
    fun fetchUserProfile(identifier: Int): Observable<UserProfileData>

    /**
     * requestPasswordUpdate
     * @param identifier
     * @param oldPass
     * @param newPass
     * @return Completable returns rx completable
     */
    fun requestPasswordUpdate(identifier: Int, oldPass: String, newPass: String): Completable
    //endregion
}