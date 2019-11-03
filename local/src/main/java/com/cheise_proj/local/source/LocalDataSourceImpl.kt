package com.cheise_proj.local.source

import com.cheise_proj.data.model.UserData
import com.cheise_proj.data.model.UserProfileData
import com.cheise_proj.data.repository.LocalDataSource
import com.cheise_proj.local.db.dao.UserDao
import com.cheise_proj.local.mapper.user.UserDataLocalMapper
import com.cheise_proj.local.mapper.user.UserProfileDataLocalMapper
import io.reactivex.Single
import java.util.logging.Level
import java.util.logging.Logger
import javax.inject.Inject

/**
 * LocalDataSource implementation from data module
 *
 * @author Kelvin Birikorang
 * @constructor
 *
 * Dependencies
 * @property userDao room user dao
 * @property userDataLocalMapper user data mapper
 * @property userProfileDataLocalMapper user profile mapper
 */
class LocalDataSourceImpl @Inject constructor(
    private val userDao: UserDao,
    private val userDataLocalMapper: UserDataLocalMapper,
    private val userProfileDataLocalMapper: UserProfileDataLocalMapper
) : LocalDataSource {
    override fun saveUserData(userData: UserData) {
        Logger.getLogger("saveUserData").log(Level.INFO, "saving local user data")
        userDao.saveUserInfo(userDataLocalMapper.to(userData))
    }

    /**
     * Get mapped userData from local db
     *
     * @param username
     * @param password
     * @return maps userData from local db
     */
    override fun getUserDataWithCredentials(
        username: String,
        password: String
    ): Single<UserData> {
        return userDao.getAuthenticatedUserWithCredentials(username, password)
            .map {
               userDataLocalMapper.from(it)
            }
    }

    /**
     * Get userData from local db using identifier
     *
     * @param identifier type string user id
     * @return maps userData from local db using identifier
     */
    override fun getUserDataWithIdentifier(identifier: String): Single<UserData> {
        return userDao.getUserDataWithIdentifier(identifier)
            .map {
                userDataLocalMapper.from(it)
            }
    }

    /**
     * Get userProfileData from local db
     *
     * @param identifier
     * @return maps userProfileData from local db
     */
    override fun getProfileData(identifier: String): Single<UserProfileData> {
        return userDao.getUserProfile(identifier)
            .map {
                userProfileDataLocalMapper.from(it)
            }
    }

    /**
     * Save userProfile data to local db
     *
     * @param userProfileData
     */
    override fun saveUserProfile(userProfileData: UserProfileData) {
        Logger.getLogger("saveUserProfile").log(Level.INFO, "saving local user profile data")
        userDao.saveUserProfile(userProfileDataLocalMapper.to(userProfileData))
    }

    /**
     * Update user password with new password in local db
     *
     * @param identifier
     * @param oldPass
     * @param newPass
     * @return single type integer
     */
    override fun updateUserPassword(
        identifier: String,
        oldPass: String,
        newPass: String
    ): Single<Int> {
        return userDao.updateUserPassword(identifier, oldPass, newPass)
    }

}