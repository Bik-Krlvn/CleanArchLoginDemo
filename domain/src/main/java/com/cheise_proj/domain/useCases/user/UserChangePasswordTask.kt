package com.cheise_proj.domain.useCases.user

import com.cheise_proj.domain.qualifier.Background
import com.cheise_proj.domain.qualifier.Foreground
import com.cheise_proj.domain.repository.UserRepository
import com.cheise_proj.domain.useCases.base.CompletableUseCase
import io.reactivex.Completable
import io.reactivex.Scheduler
import javax.inject.Inject

/**
 * Build use case user Change password
 *
 * @property userRepository
 * @constructor
 * Dependencies
 *
 * @param backgroundScheduler
 * @param foregroundScheduler
 */
class UserChangePasswordTask @Inject constructor(
    private val userRepository: UserRepository,
    @Background backgroundScheduler: Scheduler,
    @Foreground foregroundScheduler: Scheduler
) : CompletableUseCase<UserChangePasswordTask.ChangePasswordParams>(
    backgroundScheduler,
    foregroundScheduler
) {
    /**
     * Return Completable
     *
     * @param input type ChangePasswordParams object
     * @return completable
     * @throws IllegalArgumentException
     */
    override fun generateCompletable(input: ChangePasswordParams?): Completable {
        requireNotNull(input) { "change password params can't be null" }
        return userRepository.updateUserPassword(input.identifier.trim(), input.oldPass.trim(), input.newPass.trim())
    }

    /**
     * Change Password Params
     *
     * @property identifier
     * @property oldPass
     * @property newPass
     */
    data class ChangePasswordParams(val identifier: String, val oldPass: String, val newPass: String)
}