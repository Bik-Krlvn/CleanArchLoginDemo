package com.cheise_proj.local.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.cheise_proj.local.model.UserLocal
import com.cheise_proj.local.model.UserProfileLocal
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single

/**
 * Room UserDao
 *
 */
@Dao
interface UserDao {
    /**
     * Save userLocal to local db
     *
     * @param userLocal
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveUserInfo(userLocal: UserLocal)

    /**
     * Authenticate user with credentials from local db
     *
     * @param username
     * @param password
     * @return
     */
    @Query("SELECT * FROM users WHERE username = :username AND password = :password")
    fun getAuthenticatedUserWithCredentials(
        username: String,
        password: String
    ): Single<UserLocal>


    /**
     * Get user data from local db using identifier
     *
     * @param identifier type string i.e. user id
     * @return user data from local db using identifier
     */
    @Query("SELECT * FROM users WHERE id = :identifier")
    fun getUserDataWithIdentifier(
        identifier: String
    ): Single<UserLocal>

    /**
     * Save user profile data to local db
     *
     * @param userProfileLocal
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveUserProfile(userProfileLocal: UserProfileLocal)

    /**
     * Get user profile using identifier
     *
     * @param identifier type string i.e. user id
     * @return user profile using identifier
     */
    @Query("SELECT * FROM profile WHERE id = :identifier")
    fun getUserProfile(identifier: String): Single<UserProfileLocal>

    /**
     * Update user password in local db
     *
     * @param identifier type string i.e. user id
     * @param oldPass type string input current password
     * @param newPass type string input new password
     * @return completable
     */
    @Query("UPDATE users SET password = :newPass WHERE id = :identifier AND password = :oldPass")
    fun updateUserPassword(identifier: String, oldPass: String, newPass: String): Completable

}