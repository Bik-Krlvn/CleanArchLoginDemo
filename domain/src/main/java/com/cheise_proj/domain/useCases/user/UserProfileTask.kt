package com.cheise_proj.domain.useCases.user

import com.cheise_proj.domain.model.UserProfileEntity
import com.cheise_proj.domain.qualifier.Background
import com.cheise_proj.domain.qualifier.Foreground
import com.cheise_proj.domain.repository.UserRepository
import com.cheise_proj.domain.useCases.base.ObservableUseCase
import io.reactivex.Observable
import io.reactivex.Scheduler
import javax.inject.Inject

/**
 * Build use case user profile
 *
 * @property userRepository
 * @constructor
 * Dependencies
 *
 * @param backgroundScheduler
 * @param foregroundScheduler
 */
class UserProfileTask @Inject constructor(
    private val userRepository: UserRepository,
    @Background backgroundScheduler: Scheduler,
    @Foreground foregroundScheduler: Scheduler
) : ObservableUseCase<UserProfileEntity, Int>(backgroundScheduler, foregroundScheduler) {
    /**
     * Get userProfileEntity observable wrapper
     *
     * @param input type integer
     * @throws IllegalArgumentException
     * @return userProfileEntity observable wrapper
     */
    override fun generateObservable(input: Int?): Observable<UserProfileEntity> {
        requireNotNull(input) { "user identifier can't be null" }
        return userRepository.getUserProfile(input)
    }
}