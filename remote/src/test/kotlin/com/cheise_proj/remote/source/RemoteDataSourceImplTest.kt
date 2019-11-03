package com.cheise_proj.remote.source

import com.cheise_proj.remote.api.ApiService
import com.cheise_proj.remote.mapper.user.UserDataRemoteMapper
import com.cheise_proj.remote.mapper.user.UserProfileDataRemoteMapper
import com.cheise_proj.remote.utils.UserRemoteDataGenerator
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
class RemoteDataSourceImplTest {
    private lateinit var remoteDataSourceImpl: RemoteDataSourceImpl
    @Mock
    private lateinit var apiService: ApiService
    private val userDataRemoteMapper = UserDataRemoteMapper()
    private val userProfileDataRemoteMapper = UserProfileDataRemoteMapper()

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        remoteDataSourceImpl =
            RemoteDataSourceImpl(apiService, userDataRemoteMapper, userProfileDataRemoteMapper)
    }

    @Test
    fun `Get authenticated user data from remote`() {
        val res = UserRemoteDataGenerator.generateUserDataResponse()
        Mockito.`when`(
            apiService.requestUserAuthentication(
                res.userNetwork.username,
                res.userNetwork.password
            )
        )
            .thenReturn(Observable.just(res))

        remoteDataSourceImpl.fetchUserDataWithCredentials(
            res.userNetwork.username,
            res.userNetwork.password
        ).test()
            .assertSubscribed()
            .assertValueCount(1)
            .assertValue {
                it == userDataRemoteMapper.from(res.userNetwork)
            }
            .assertComplete()
        Mockito.verify(apiService, times(1)).requestUserAuthentication(
            res.userNetwork.username,
            res.userNetwork.password
        )

    }

    @Test
    fun `Get user profile from remote`() {
        val res = UserRemoteDataGenerator.generateProfileDataResponse()
        Mockito.`when`(apiService.fetchUserProfile(res.profileNetwork?.id!!))
            .thenReturn(Observable.just(res))
        remoteDataSourceImpl.fetchUserProfile(res.profileNetwork?.id!!).test()
            .assertSubscribed()
            .assertValueCount(1)
            .assertValue {
                it == userProfileDataRemoteMapper.from(res.profileNetwork!!)
            }
            .assertComplete()
        Mockito.verify(apiService, times(1)).fetchUserProfile(res.profileNetwork?.id!!)
    }

    @Test
    fun `Request change password from remote`() {
        val res = UserRemoteDataGenerator.generateChangePasswordResponse()
        val req = UserRemoteDataGenerator.generateChangePassword()
        Mockito.`when`(
            apiService.requestUserChangePassword(
                req.identifier,
                req.oldPass,
                req.newPass
            )
        ).thenReturn(
            Observable.just(res)
        )

        remoteDataSourceImpl.requestPasswordUpdate(req.identifier, req.oldPass, req.newPass).test()
            .assertSubscribed()
            .assertComplete()
        Mockito.verify(apiService, times(1))
            .requestUserChangePassword(req.identifier, req.oldPass, req.newPass)
    }
}