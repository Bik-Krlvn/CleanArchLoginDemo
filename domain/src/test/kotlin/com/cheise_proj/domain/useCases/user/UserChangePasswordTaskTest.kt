package com.cheise_proj.domain.useCases.user

import com.cheise_proj.domain.repository.UserRepository
import com.cheise_proj.domain.utils.UserDataGenerator
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import org.junit.Before
import org.junit.Test

import org.junit.Assert.*
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import java.lang.IllegalArgumentException

class UserChangePasswordTaskTest {
    private lateinit var userChangePasswordTask: UserChangePasswordTask
    @Mock
    private lateinit var userRepository: UserRepository

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        userChangePasswordTask =
            UserChangePasswordTask(userRepository, Schedulers.trampoline(), Schedulers.trampoline())
    }

    @Test
    fun `update user password success`() {
        val updateRow = 1
        val genData = UserDataGenerator.generateChangePassword()
        Mockito.`when`(
            userRepository.updateUserPassword(
                genData.identifier,
                genData.oldPass,
                genData.newPass
            )
        ).thenReturn(
            Observable.just(updateRow)
        )

        userChangePasswordTask.buildUseCase(
            UserChangePasswordTask.ChangePasswordParams(
                genData.identifier,
                genData.oldPass,
                genData.newPass
            )
        ).test()
            .assertSubscribed()
            .assertValueCount(1)
            .assertValue {
                it == updateRow
            }
            .assertComplete()
    }

    @Test
    fun `update user password failed`() {
        val genData = UserDataGenerator.generateChangePassword()
        val errorMsg = "An error occurred"
        Mockito.`when`(
            userRepository.updateUserPassword(
                genData.identifier,
                genData.oldPass,
                genData.newPass
            )
        ).thenReturn(
            Observable.error(Throwable(errorMsg))
        )

        userChangePasswordTask.buildUseCase(
            UserChangePasswordTask.ChangePasswordParams(
                genData.identifier,
                genData.oldPass,
                genData.newPass
            )
        ).test()
            .assertSubscribed()
            .assertError {
                it.localizedMessage?.equals(errorMsg) ?: false
            }
            .assertNotComplete()
    }

    @Test(expected = IllegalArgumentException::class)
    fun `update user password with null params`() {
        userChangePasswordTask.buildUseCase()
    }
}