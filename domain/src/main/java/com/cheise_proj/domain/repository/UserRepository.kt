package com.cheise_proj.domain.repository

import com.cheise_proj.domain.model.UserEntity
import com.cheise_proj.domain.model.UserProfileEntity
import io.reactivex.Completable
import io.reactivex.Observable

/**
 * User Repository domain
 *
 */
interface UserRepository {
    /**
     * Get userEntity observable wrapper
     *
     * @param username type string username
     * @param password type string user password
     * @return userEntity observable wrapper
     */
    fun authenticateUser(username: String, password: String): Observable<UserEntity>

    /**
     * Get userProfileEntity observable wrapper
     *
     * @param identifier type integer i.e. user id
     * @return userProfileEntity observable wrapper
     */
    fun getUserProfile(identifier: String): Observable<UserProfileEntity>

    /**
     * Update a user password
     *
     * @param identifier
     * @param oldPass
     * @param newPass
     * @return completable
     */
    fun updateUserPassword(identifier: String, oldPass: String, newPass: String): Observable<Int>
}