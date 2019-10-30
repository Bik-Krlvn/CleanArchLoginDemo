package com.cheise_proj.data.repository.user

import com.cheise_proj.data.mapper.user.UserEntityDataMapper
import com.cheise_proj.data.mapper.user.UserProfileEntityDataMapper
import com.cheise_proj.data.repository.LocalRepository
import com.cheise_proj.data.repository.RemoteRepository
import com.cheise_proj.domain.model.UserEntity
import com.cheise_proj.domain.model.UserProfileEntity
import com.cheise_proj.domain.repository.UserRepository
import io.reactivex.Completable
import io.reactivex.Observable
import javax.inject.Inject

/**
 * UserRepository Actions
 * @author Kelvin Birikorang
 * @param localRepository provide local repository interface
 * @param remoteRepository provide remote repository interface
 * @param userEntityDataMapper provide user entity mapper
 * @param userProfileEntityDataMapper provide profile entity mapper
 * @constructor create user repository object
 * @property UserRepositoryImpl
 */
class UserRepositoryImpl @Inject constructor(
    private val localRepository: LocalRepository,
    private val remoteRepository: RemoteRepository,
    private val userEntityDataMapper: UserEntityDataMapper,
    private val userProfileEntityDataMapper: UserProfileEntityDataMapper
) : UserRepository {
    override fun authenticateUser(username: String, password: String): Observable<UserEntity> {
        val localObservable = localRepository.getUserDataWithCredentials(username, password)
            .map {
                userEntityDataMapper.from(it)
            }
        return remoteRepository.fetchUserDataWithCredentials(username, password)
            .map {
                it.password = password
                localRepository.saveUserData(it)
                userEntityDataMapper.from(it)
            }
            .concatWith(localObservable)

    }

    override fun getUserProfile(identifier: Int): Observable<UserProfileEntity> {
        val localObservable = localRepository.getProfileData(identifier)
            .map {
                userProfileEntityDataMapper.from(it)
            }
        return remoteRepository.fetchUserProfile(identifier)
            .map {
                localRepository.saveUserProfile(it)
                userProfileEntityDataMapper.from(it)
            }
            .concatWith(localObservable)

    }

    override fun updateUserPassword(
        identifier: Int,
        oldPass: String,
        newPass: String
    ): Completable {
        return remoteRepository.requestPasswordUpdate(identifier, oldPass, newPass)
            .concatWith {
                localRepository.updateUserPassword(identifier, oldPass, newPass)
            }
    }
}