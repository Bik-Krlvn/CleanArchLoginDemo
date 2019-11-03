package com.cheise_proj.local.source

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.cheise_proj.local.db.dao.UserDao
import com.cheise_proj.local.mapper.user.UserDataLocalMapper
import com.cheise_proj.local.mapper.user.UserProfileDataLocalMapper
import com.cheise_proj.local.utils.UserLocalDataGenerator
import io.reactivex.Completable
import io.reactivex.Single
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.times
import org.mockito.MockitoAnnotations

class LocalDataSourceImplTest {
    private lateinit var localDataSourceImpl: LocalDataSourceImpl
    @Mock
    private lateinit var userDao: UserDao
    private val userDataLocalMapper = UserDataLocalMapper()
    private val userProfileDataLocalMapper = UserProfileDataLocalMapper()

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        localDataSourceImpl = LocalDataSourceImpl(
            userDao, userDataLocalMapper, userProfileDataLocalMapper
        )
    }

    @Test
    fun `Authenticate user locally with credentials`() {
        val input = UserLocalDataGenerator.generateUserData()
        Mockito.`when`(userDao.getAuthenticatedUserWithCredentials(input.username, input.password))
            .thenReturn(
                Single.just(input)
            )
        localDataSourceImpl.getUserDataWithCredentials(input.username, input.password).test()
            .assertSubscribed()
            .assertValueCount(1)
            .assertValue {
                it == userDataLocalMapper.from(input)
            }
            .assertComplete()

        Mockito.verify(userDao, times(1))
            .getAuthenticatedUserWithCredentials(input.username, input.password)
    }

    @Test
    fun `Get user data with identifier`() {
        val userData = UserLocalDataGenerator.generateUserData()
        Mockito.`when`(userDao.getUserDataWithIdentifier(userData.id))
            .thenReturn(Single.just(userData))
        localDataSourceImpl.getUserDataWithIdentifier(userData.id).test()
            .assertSubscribed()
            .assertValueCount(1)
            .assertValue {
                it == userDataLocalMapper.from(userData)
            }
            .assertComplete()
        Mockito.verify(userDao, times(1)).getUserDataWithIdentifier(userData.id)
    }

    @Test
    fun `Get user profile with identifier`() {
        val profileLocal = UserLocalDataGenerator.generateProfile()
        Mockito.`when`(userDao.getUserProfile(profileLocal.id))
            .thenReturn(Single.just(profileLocal))
        localDataSourceImpl.getProfileData(profileLocal.id).test()
            .assertSubscribed()
            .assertValueCount(1)
            .assertValue {
                it == userProfileDataLocalMapper.from(profileLocal)
            }
            .assertComplete()
        Mockito.verify(userDao, times(1)).getUserProfile(profileLocal.id)
    }

    @Test
    fun `Save user data locally `() {
        val userData = UserLocalDataGenerator.generateUserData()
        localDataSourceImpl.saveUserData(userDataLocalMapper.from(userData))
        Mockito.verify(userDao, times(1)).saveUserInfo(userData)
    }

    @Test
    fun `Save user profile data locally `() {
        val profileLocal = UserLocalDataGenerator.generateProfile()
        localDataSourceImpl.saveUserProfile(userProfileDataLocalMapper.from(profileLocal))
        Mockito.verify(userDao, times(1)).saveUserProfile(profileLocal)
    }

    @Test
    fun `Update user password locally`() {
        val input = UserLocalDataGenerator.generateChangePassword()
        Mockito.`when`(userDao.updateUserPassword(input.identifier, input.oldPass, input.newPass))
            .thenReturn(
                Completable.complete()
            )
        localDataSourceImpl.updateUserPassword(input.identifier, input.oldPass, input.newPass)
            .test()
            .assertSubscribed()
            .assertComplete()
    }
}