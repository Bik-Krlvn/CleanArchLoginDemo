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
import io.reactivex.functions.Function
import java.util.logging.Level
import java.util.logging.Logger
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
        val remoteObservable = remoteDataSource.fetchUserDataWithCredentials(username, password)
            .map {
                localDataSource.saveUserData(it)
                userEntityDataMapper.from(it)
            }

        return localDataSource.getUserDataWithCredentials(username, password)
            .map { userEntityDataMapper.from(it) }
            .toObservable()
            .onErrorResumeNext(
                Function {
                    remoteObservable
                }
            )
    }

    override fun getUserProfile(identifier: String): Observable<UserProfileEntity> {
        val remoteObservable = remoteDataSource.fetchUserProfile(identifier)
            .map {
                localDataSource.saveUserProfile(it)
                userProfileEntityDataMapper.from(it)
            }
        return localDataSource.getProfileData(identifier)
            .map {
                userProfileEntityDataMapper.from(it)
            }
            .toObservable()
            .onErrorResumeNext(
                Function { remoteObservable }
            )
    }

    override fun updateUserPassword(
        identifier: String,
        oldPass: String,
        newPass: String
    ): Observable<Int> {
        return remoteDataSource.requestPasswordUpdate(identifier, oldPass, newPass)
            .map {
                Logger.getLogger("updateUserPassword").log(Level.INFO, "remote status: $it")
                return@map it
            }
            .concatWith(
                localDataSource.updateUserPassword(
                    identifier,
                    oldPass,
                    newPass
                )
                    .toObservable()
            )
            .onErrorResumeNext(
                Function {
                    localDataSource.updateUserPassword(
                        identifier,
                        oldPass,
                        newPass
                    ).toObservable()
                }
            )
    }
}