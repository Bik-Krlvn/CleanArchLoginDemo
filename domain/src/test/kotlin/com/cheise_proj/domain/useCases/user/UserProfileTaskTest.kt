package com.cheise_proj.domain.useCases.user

import com.cheise_proj.domain.repository.UserRepository
import com.cheise_proj.domain.utils.UserDataGenerator
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import org.junit.Before
import org.junit.Test

import org.junit.Assert.*
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import java.lang.IllegalArgumentException

class UserProfileTaskTest {
    private lateinit var userProfileTask: UserProfileTask
    @Mock
    private lateinit var userRepository: UserRepository

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        userProfileTask =
            UserProfileTask(userRepository, Schedulers.trampoline(), Schedulers.trampoline())
    }

    @Test
    fun `Get user profile success`() {
        val genData = UserDataGenerator.generateProfile()
        Mockito.`when`(userRepository.getUserProfile(genData.id))
            .thenReturn(Observable.just(genData))
        userProfileTask.buildUseCase(genData.id).test()
            .assertSubscribed()
            .assertValueCount(1)
            .assertValue {
                it == genData
            }
            .assertComplete()
    }

    @Test
    fun `Get user profile failed`() {
        val genData = UserDataGenerator.generateProfile()
        val errorMsg = "An error occurred"
        Mockito.`when`(userRepository.getUserProfile(genData.id)).thenReturn(
            Observable.error(
                Throwable(errorMsg)
            )
        )
        userProfileTask.buildUseCase(genData.id).test()
            .assertSubscribed()
            .assertError {
                it.localizedMessage?.equals(errorMsg) ?: false
            }
            .assertNotComplete()
    }

    @Test(expected = IllegalArgumentException::class)
    fun `Get user profile with null params`() {
        userProfileTask.buildUseCase().test()
    }


}