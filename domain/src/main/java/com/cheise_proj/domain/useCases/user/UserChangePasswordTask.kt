package com.cheise_proj.domain.useCases.user

import com.cheise_proj.domain.qualifier.Background
import com.cheise_proj.domain.qualifier.Foreground
import com.cheise_proj.domain.repository.UserRepository
import com.cheise_proj.domain.useCases.base.CompletableUseCase
import io.reactivex.Completable
import io.reactivex.Scheduler
import javax.inject.Inject

class UserChangePasswordTask @Inject constructor(
    private val userRepository: UserRepository,
    @Background backgroundScheduler: Scheduler,
    @Foreground foregroundScheduler: Scheduler
) : CompletableUseCase<UserChangePasswordTask.ChangePasswordParams>(
    backgroundScheduler,
    foregroundScheduler
) {
    override fun generateCompletable(input: ChangePasswordParams?): Completable {
        requireNotNull(input) { "change password params can't be null" }
        return userRepository.updateUserPassword(input.identifier, input.oldPass, input.newPass)
    }

    data class ChangePasswordParams(val identifier: Int, val oldPass: String, val newPass: String)
}