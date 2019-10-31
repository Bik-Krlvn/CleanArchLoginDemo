package com.cheise_proj.data.repository.user

import com.cheise_proj.data.mapper.user.UserEntityDataMapper
import com.cheise_proj.data.mapper.user.UserProfileEntityDataMapper
import com.cheise_proj.data.repository.LocalDataSource
import com.cheise_proj.data.repository.RemoteDataSource
import com.cheise_proj.domain.model.UserEntity
import com.cheise_proj.domain.model.UserProfileEntity
import com.cheise_proj.domain.repository.UserRepository
import io.reactivex.Completable
import io.reactivex.Observable
import javax.inject.Inject

/**
 * UserRepository Actions
 * @author Kelvin Birikorang
 * @param localDataSource provide local repository interface
 * @param remoteDataSource provide remote repository interface
 * @param userEntityDataMapper provide user entity mapper
 * @param userProfileEntityDataMapper provide profile entity mapper
 * @constructor create user repository object
 * @property UserRepositoryImpl
 */
class UserRepositoryImpl @Inject constructor(
    private val localDataSource: LocalDataSource,
    private val remoteDataSource: RemoteDataSource,
    private val userEntityDataMapper: UserEntityDataMapper,
    private val userProfileEntityDataMapper: UserProfileEntityDataMapper
) : UserRepository {
    override fun authenticateUser(username: String, password: String): Observable<UserEntity> {
        val localObservable = localDataSource.getUserDataWithCredentials(username, password)
            .map {
                userEntityDataMapper.from(it)
            }
        return remoteDataSource.fetchUserDataWithCredentials(username, password)
            .map {
                it.password = password
                localDataSource.saveUserData(it)
                userEntityDataMapper.from(it)
            }
            .concatWith(localObservable)

    }

    override fun getUserProfile(identifier: Int): Observable<UserProfileEntity> {
        val localObservable = localDataSource.getProfileData(identifier)
            .map {
                userProfileEntityDataMapper.from(it)
            }
        return remoteDataSource.fetchUserProfile(identifier)
            .map {
                localDataSource.saveUserProfile(it)
                userProfileEntityDataMapper.from(it)
            }
            .concatWith(localObservable)

    }

    override fun updateUserPassword(
        identifier: Int,
        oldPass: String,
        newPass: String
    ): Completable {
        return remoteDataSource.requestPasswordUpdate(identifier, oldPass, newPass)
            .concatWith {
                localDataSource.updateUserPassword(identifier, oldPass, newPass)
            }
    }
}