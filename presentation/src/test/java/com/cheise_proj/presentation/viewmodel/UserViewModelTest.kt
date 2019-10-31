package com.cheise_proj.presentation.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.cheise_proj.domain.repository.UserRepository
import com.cheise_proj.domain.useCases.user.UserAuthenticationTask
import com.cheise_proj.domain.useCases.user.UserChangePasswordTask
import com.cheise_proj.domain.useCases.user.UserProfileTask
import com.cheise_proj.presentation.mapper.user.UserEntityMapper
import com.cheise_proj.presentation.mapper.user.UserProfileEntityMapper
import com.cheise_proj.presentation.model.Status
import com.cheise_proj.presentation.utils.UserDataGenerator
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.times
import org.mockito.MockitoAnnotations

@RunWith(JUnit4::class)
class UserViewModelTest {
    private lateinit var userViewModel: UserViewModel
    @Mock
    private lateinit var userRepository: UserRepository
    private val userEntityMapper = UserEntityMapper()
    private val userProfileEntityMapper = UserProfileEntityMapper()
    @get:Rule
    val rule = InstantTaskExecutorRule()

    private val userData = UserDataGenerator.generateUserData()
    private val userProfileData = UserDataGenerator.generateProfile()

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        val authenticationTask =
            UserAuthenticationTask(userRepository, Schedulers.trampoline(), Schedulers.trampoline())
        val userProfileTaskTest =
            UserProfileTask(userRepository, Schedulers.trampoline(), Schedulers.trampoline())
        val changePasswordTask =
            UserChangePasswordTask(userRepository, Schedulers.trampoline(), Schedulers.trampoline())
        userViewModel = UserViewModel(
            authenticationTask,
            userProfileTaskTest,
            changePasswordTask,
            userEntityMapper,
            userProfileEntityMapper
        )
    }

    @Test
    fun `Authenticate user with credentials success`() {
        Mockito.`when`(userRepository.authenticateUser(userData.username, userData.password))
            .thenReturn(Observable.just(userEntityMapper.from(userData)))
        val authLiveData = userViewModel.authenticateUser(userData.username, userData.password)
        authLiveData.observeForever { }
        assertTrue(
            authLiveData.value?.status == Status.SUCCESS &&
                    authLiveData.value?.data == userData
        )
    }

    @Test
    fun `Authenticate user with credentials failed`() {
        val errorMsg = "An error occurred"
        Mockito.`when`(userRepository.authenticateUser(userData.username, userData.password))
            .thenReturn(Observable.error(Throwable(errorMsg)))
        val authLiveData = userViewModel.authenticateUser(userData.username, userData.password)
        authLiveData.observeForever { }
        assertTrue(
            authLiveData.value?.status == Status.ERROR &&
                    authLiveData.value?.data == null
        )
    }

    @Test
    fun `Get user profile with identifier success`() {
        Mockito.`when`(userRepository.getUserProfile(userData.id))
            .thenReturn(Observable.just(userProfileEntityMapper.from(userProfileData)))

        val profileLiveData = userViewModel.getUserProfile(userData.id)
        profileLiveData.observeForever { }
        assertTrue(
            profileLiveData.value?.status == Status.SUCCESS &&
                    profileLiveData.value?.data == userProfileData
        )

    }

    @Test
    fun `Get user profile with identifier failed`() {
        val errorMsg = "An error occurred"
        Mockito.`when`(userRepository.getUserProfile(userData.id))
            .thenReturn(Observable.error(Throwable(errorMsg)))
        val profileLiveData = userViewModel.getUserProfile(userData.id)
        profileLiveData.observeForever { }
        assertTrue(
            profileLiveData.value?.message == errorMsg &&
                    profileLiveData.value?.data == null
        )
        userViewModel
    }

    @Test
    fun `Change user password`() {
        val reqPass = UserDataGenerator.generateChangePassword()
        Mockito.`when`(
            userRepository.updateUserPassword(
                reqPass.identifier,
                reqPass.oldPass,
                reqPass.newPass
            )
        ).thenReturn(Completable.complete())

        userViewModel.changeUserPassword(
            reqPass.identifier,
            reqPass.oldPass,
            reqPass.newPass
        )

        Mockito.verify(userRepository, times(1))
            .updateUserPassword(
                reqPass.identifier,
                reqPass.oldPass,
                reqPass.newPass
            )
    }
}