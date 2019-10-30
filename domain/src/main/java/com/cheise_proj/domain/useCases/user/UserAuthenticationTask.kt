package com.cheise_proj.domain.useCases.user

import com.cheise_proj.domain.model.UserEntity
import com.cheise_proj.domain.qualifier.Background
import com.cheise_proj.domain.qualifier.Foreground
import com.cheise_proj.domain.repository.UserRepository
import com.cheise_proj.domain.useCases.base.ObservableUseCase
import io.reactivex.Observable
import io.reactivex.Scheduler
import javax.inject.Inject

class UserAuthenticationTask @Inject constructor(
    private val userRepository: UserRepository,
    @Background backgroundScheduler: Scheduler,
    @Foreground foregroundScheduler: Scheduler
) : ObservableUseCase<UserEntity, UserAuthenticationTask.AuthParams>(
    backgroundScheduler,
    foregroundScheduler
) {
    override fun generateObservable(input: AuthParams?): Observable<UserEntity> {
        requireNotNull(input) { "auth params can't be null" }
        return userRepository.authenticateUser(input.username, input.password)
    }

    data class AuthParams(val username: String, val password: String)
}