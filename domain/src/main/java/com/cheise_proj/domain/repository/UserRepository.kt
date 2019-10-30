package com.cheise_proj.domain.repository

import com.cheise_proj.domain.model.UserEntity
import com.cheise_proj.domain.model.UserProfileEntity
import io.reactivex.Completable
import io.reactivex.Observable

interface UserRepository {
    fun authenticateUser(username: String, password: String): Observable<UserEntity>
    fun getUserProfile(identifier: Int): Observable<UserProfileEntity>
    fun updateUserPassword(identifier: Int, oldPass: String, newPass: String): Completable
}