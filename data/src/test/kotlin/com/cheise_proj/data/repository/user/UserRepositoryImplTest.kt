package com.cheise_proj.data.repository.user

import com.cheise_proj.data.mapper.user.UserEntityDataMapper
import com.cheise_proj.data.mapper.user.UserProfileEntityDataMapper
import com.cheise_proj.data.repository.LocalDataSource
import com.cheise_proj.data.repository.RemoteDataSource
import com.cheise_proj.data.utils.UserDataGenerator
import io.reactivex.Completable
import io.reactivex.Observable
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.times
import org.mockito.MockitoAnnotations

@RunWith(JUnit4::class)
class UserRepositoryImplTest {
    private lateinit var userRepositoryImpl: UserRepositoryImpl
    @Mock
    private lateinit var localDataSource: LocalDataSource
    @Mock
    private lateinit var remoteDataSource: RemoteDataSource

    private val userData = UserDataGenerator.generateUserData()
    private val userDataMapper = UserEntityDataMapper()
    private val userProfileDataMapper = UserProfileEntityDataMapper()
    private val userProfileData = UserDataGenerator.generateProfile()

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        userRepositoryImpl = UserRepositoryImpl(
            localDataSource, remoteDataSource,
            userDataMapper, userProfileDataMapper
        )
    }

    @Test
    fun `Authenticate user remote and local success`() {
        Mockito.`when`(
            remoteDataSource.fetchUserDataWithCredentials(
                userData.username,
                userData.password
            )
        )
            .thenReturn(Observable.just(userData))
        Mockito.`when`(
            localDataSource.getUserDataWithCredentials(
                userData.username,
                userData.password
            )
        )
            .thenReturn(Observable.just(userData))

        userRepositoryImpl.authenticateUser(userData.username, userData.password)
            .test()
            .assertSubscribed()
            .assertValueCount(2)
            .assertValues(userDataMapper.from(userData), userDataMapper.from(userData))
            .assertComplete()
        Mockito.verify(remoteDataSource, times(1))
            .fetchUserDataWithCredentials(userData.username, userData.password)
        Mockito.verify(localDataSource, times(1))
            .getUserDataWithCredentials(userData.username, userData.password)
    }

    @Test
    fun `Get user profile remote and save local success`() {
        Mockito.`when`(remoteDataSource.fetchUserProfile(userData.id))
            .thenReturn(Observable.just(userProfileData))

        Mockito.`when`(localDataSource.getProfileData(userData.id))
            .thenReturn(Observable.just(userProfileData))

        userRepositoryImpl.getUserProfile(userData.id).test()
            .assertSubscribed()
            .assertValueCount(2)
            .assertValues(
                userProfileDataMapper.from(userProfileData),
                userProfileDataMapper.from(userProfileData)
            )
            .assertComplete()
        Mockito.verify(remoteDataSource, times(1)).fetchUserProfile(userData.id)
        Mockito.verify(localDataSource, times(1)).getProfileData(userData.id)
    }

    @Test
    fun `Update user password remote and local success`() {
        val requestPass = UserDataGenerator.generateChangePassword()
        Mockito.`when`(
            remoteDataSource.requestPasswordUpdate(
                requestPass.identifier,
                requestPass.oldPass,
                requestPass.newPass
            )
        ).thenReturn(Completable.complete())

        Mockito.`when`(
            localDataSource.updateUserPassword(
                requestPass.identifier,
                requestPass.oldPass,
                requestPass.newPass
            )
        ).thenReturn(Completable.complete())

        userRepositoryImpl.updateUserPassword(
            requestPass.identifier,
            requestPass.oldPass,
            requestPass.newPass
        ).test()
            .assertSubscribed()

        Mockito.verify(remoteDataSource, times(1)).requestPasswordUpdate(
            requestPass.identifier,
            requestPass.oldPass,
            requestPass.newPass
        )
        Mockito.verify(localDataSource, times(1)).updateUserPassword(
            requestPass.identifier,
            requestPass.oldPass,
            requestPass.newPass
        )
    }
}