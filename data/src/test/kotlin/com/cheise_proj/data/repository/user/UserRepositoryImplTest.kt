package com.cheise_proj.data.repository.user

import com.cheise_proj.data.mapper.user.UserEntityDataMapper
import com.cheise_proj.data.mapper.user.UserProfileEntityDataMapper
import com.cheise_proj.data.repository.LocalDataSource
import com.cheise_proj.data.repository.RemoteDataSource
import com.cheise_proj.data.utils.UserDataGenerator
import io.reactivex.Observable
import io.reactivex.Single
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.times
import org.mockito.MockitoAnnotations
import java.util.logging.Level
import java.util.logging.Logger

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
    fun `Authenticate user locally success`() {
        Mockito.`when`(
            localDataSource.getUserDataWithCredentials(
                userData.username,
                userData.password
            )
        )
            .thenReturn(Single.just(userData))

        Mockito.`when`(
            remoteDataSource.fetchUserDataWithCredentials(
                userData.username,
                userData.password
            )
        )
            .thenReturn(Observable.empty())

        userRepositoryImpl.authenticateUser(userData.username, userData.password)
            .test()
            .assertSubscribed()
            .assertValueCount(1)
            .assertValue {
                it == userDataMapper.from(userData)
            }
            .assertComplete()
        Mockito.verify(remoteDataSource, times(1))
            .fetchUserDataWithCredentials(userData.username, userData.password)
        Mockito.verify(localDataSource, times(1))
            .getUserDataWithCredentials(userData.username, userData.password)
    }

    @Test
    fun `Authenticate user locally failed, try remote success`() {
        val errorMsg = "No user found exception from local db"
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
            .thenReturn(Single.error(Throwable(errorMsg)))

        userRepositoryImpl.authenticateUser(userData.username, userData.password)
            .test()
            .assertSubscribed()
            .assertValueCount(1)
            .assertValue {
                it == userDataMapper.from(userData)
            }
            .assertComplete()
        Mockito.verify(remoteDataSource, times(1))
            .fetchUserDataWithCredentials(userData.username, userData.password)
        Mockito.verify(localDataSource, times(1))
            .getUserDataWithCredentials(userData.username, userData.password)
    }

    @Test
    fun `Get profile data locally success`() {
        Mockito.`when`(localDataSource.getProfileData(userData.id))
            .thenReturn(Single.just(userProfileData))

        Mockito.`when`(remoteDataSource.fetchUserProfile(userData.id))
            .thenReturn(Observable.empty())

        userRepositoryImpl.getUserProfile(userData.id).test()
            .assertSubscribed()
            .assertValueCount(1)
            .assertValue {
                it == userProfileDataMapper.from(userProfileData)
            }
            .assertComplete()
        Mockito.verify(remoteDataSource, times(1)).fetchUserProfile(userData.id)
        Mockito.verify(localDataSource, times(1)).getProfileData(userData.id)
    }

    @Test
    fun `Try getting user profile data local and if error, get from remote`() {
        val errorMsg = "No profile data found exception in local db"
        Mockito.`when`(remoteDataSource.fetchUserProfile(userData.id))
            .thenReturn(Observable.just(userProfileData))

        Mockito.`when`(localDataSource.getProfileData(userData.id))
            .thenReturn(Single.error(Throwable(errorMsg)))

        userRepositoryImpl.getUserProfile(userData.id).test()
            .assertSubscribed()
            .assertValueCount(1)
            .assertValue {
                it == userProfileDataMapper.from(userProfileData)
            }
            .assertComplete()
        Mockito.verify(remoteDataSource, times(1)).fetchUserProfile(userData.id)
        Mockito.verify(localDataSource, times(1)).getProfileData(userData.id)
    }

    @Test
    fun `Update user password remote, if success update local password success`() {
        val updateRow = 1
        val requestPass = UserDataGenerator.generateChangePassword()
        Mockito.`when`(
            remoteDataSource.requestPasswordUpdate(
                requestPass.identifier,
                requestPass.oldPass,
                requestPass.newPass
            )
        ).thenReturn(Observable.just(updateRow))

        Mockito.`when`(
            localDataSource.updateUserPassword(
                requestPass.identifier,
                requestPass.oldPass,
                requestPass.newPass
            )
        ).thenReturn(Single.just(updateRow))

        userRepositoryImpl.updateUserPassword(
            requestPass.identifier,
            requestPass.oldPass,
            requestPass.newPass
        ).test()
            .assertSubscribed()
            .assertValueCount(2)
            .assertValues(updateRow, updateRow)
            .assertComplete()

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

    @Test
    fun `Update user password remote failed then reset local password is success`() {
        val updateRow = 1
        val errorMsg = "Remote error occurred"
        val requestPass = UserDataGenerator.generateChangePassword()
        Mockito.`when`(
            remoteDataSource.requestPasswordUpdate(
                requestPass.identifier,
                requestPass.oldPass,
                requestPass.newPass
            )
        ).thenReturn(Observable.error(Throwable(errorMsg)))

        Mockito.`when`(
            localDataSource.updateUserPassword(
                requestPass.identifier,
                requestPass.oldPass,
                requestPass.newPass
            )
        ).thenReturn(Single.just(updateRow))

        userRepositoryImpl.updateUserPassword(
            requestPass.identifier,
            requestPass.oldPass,
            requestPass.newPass
        ).test()
            .assertSubscribed()
            .assertValueCount(1)
            .assertValue {
                it == updateRow
            }
            .assertComplete()

        Mockito.verify(remoteDataSource, times(1)).requestPasswordUpdate(
            requestPass.identifier,
            requestPass.oldPass,
            requestPass.newPass
        )
        Mockito.verify(localDataSource, times(2)).updateUserPassword(
            requestPass.identifier,
            requestPass.oldPass,
            requestPass.newPass
        )
    }
}