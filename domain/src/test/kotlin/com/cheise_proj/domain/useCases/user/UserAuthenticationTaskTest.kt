package com.cheise_proj.domain.useCases.user

import com.cheise_proj.domain.repository.UserRepository
import com.cheise_proj.domain.utils.UserDataGenerator
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import org.junit.Before

import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

@RunWith(JUnit4::class)
class UserAuthenticationTaskTest {
    private lateinit var authenticationTask: UserAuthenticationTask
    @Mock
    private lateinit var userRepository: UserRepository

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        authenticationTask =
            UserAuthenticationTask(userRepository, Schedulers.trampoline(), Schedulers.trampoline())
    }

    @Test
    fun `Authenticate user success`() {
        val user = UserDataGenerator.generateUserData()
        Mockito.`when`(userRepository.authenticateUser(user.username, user.password)).thenReturn(
            Observable.just(user)
        )

        authenticationTask.buildUseCase(
            UserAuthenticationTask.AuthParams(
                user.username,
                user.password
            )
        ).test()
            .assertSubscribed()
            .assertValueCount(1)
            .assertValue {
                it == user
            }
            .assertComplete()

    }

    @Test
    fun `Authenticate user failed`() {
        val user = UserDataGenerator.generateUserData()
        val errorMsg = "An error occurred"
        Mockito.`when`(userRepository.authenticateUser(user.username, user.password)).thenReturn(
            Observable.error(Throwable(errorMsg))
        )

        authenticationTask.buildUseCase(
            UserAuthenticationTask.AuthParams(
                user.username,
                user.password
            )
        ).test()
            .assertSubscribed()
            .assertError {
                it.localizedMessage?.equals(errorMsg) ?: false
            }
            .assertNotComplete()
    }

    @Test(expected = IllegalArgumentException::class)
    fun `Authenticate user with null params throws exception`() {
        authenticationTask.buildUseCase().test()
    }
}