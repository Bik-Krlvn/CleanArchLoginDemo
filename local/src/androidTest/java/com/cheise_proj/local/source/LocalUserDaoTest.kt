package com.cheise_proj.local.source

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.platform.app.InstrumentationRegistry
import com.cheise_proj.local.db.LocalAppDatabase
import com.cheise_proj.local.db.dao.UserDao
import com.cheise_proj.local.utils.UserLocalGenerator
import org.junit.After
import org.junit.Before
import org.junit.Rule

import org.junit.Test

class LocalUserDaoTest {

    private lateinit var localAppDatabase: LocalAppDatabase
    private lateinit var userDao: UserDao

    @get:Rule
    val rule = InstantTaskExecutorRule()


    @Before
    fun setUp() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        localAppDatabase =
            Room.inMemoryDatabaseBuilder(context, LocalAppDatabase::class.java)
                .allowMainThreadQueries()
                .build()
        userDao = localAppDatabase.userDao()
    }

    @After
    fun tearDown() {
        localAppDatabase.close()
    }

    @Test
    fun save_user_and_retrieve_data_with_identifier_success() {
        val userData = UserLocalGenerator.generateUserData()
        userDao.saveUserInfo(userData)

        userDao.getUserDataWithIdentifier(userData.id).test()
            .assertSubscribed()
            .assertValueCount(1)
            .assertValue {
                it == userData
            }
            .assertComplete()

    }

    @Test
    fun save_user_and_authenticate_with_credentials_success() {
        val input = UserLocalGenerator.generateUserData()
        userDao.saveUserInfo(input)

        userDao.getAuthenticatedUserWithCredentials(input.username, input.password).test()
            .assertSubscribed()
            .assertValueCount(1)
            .assertValue {
                it == input
            }
            .assertComplete()
    }

    @Test
    fun save_profile_and_retrieve_with_identifier_success() {
        val profileLocal = UserLocalGenerator.generateProfile()
        userDao.saveUserProfile(profileLocal)

        userDao.getUserProfile(profileLocal.id).test()
            .assertSubscribed()
            .assertValueCount(1)
            .assertValue {
                it == profileLocal
            }
            .assertComplete()
    }

    @Test
    fun save_user_and_update_password_success() {
        val userData = UserLocalGenerator.generateUserData()
        val updateData = UserLocalGenerator.generateChangePassword()
        userDao.saveUserInfo(userData)

        userDao.updateUserPassword(updateData.identifier, updateData.oldPass, updateData.newPass)
            .test()
            .assertSubscribed()
            .assertComplete()

        userDao.getUserDataWithIdentifier(userData.id)
            .test()
            .assertSubscribed()
            .assertValueCount(1)
            .assertValue {
                it.password != userData.password
            }
            .assertComplete()
    }

}