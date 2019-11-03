package com.cheise_proj.remote.api

import com.cheise_proj.remote.model.user.UserChangePasswordResponse
import com.cheise_proj.remote.model.user.UserProfileResponse
import com.cheise_proj.remote.model.user.UserResponse
import io.reactivex.Observable
import retrofit2.http.*

/**
 * Remote Api Service request
 *
 *@author Kelvin Birikorang
 */
interface ApiService {

    /**
     * Get user data from remote with user credentials
     *
     * @param username type string input username
     * @param password type sting input password
     * @return observable UserResponse wrapper
     */
    @FormUrlEncoded
    @POST("users/login")
    fun requestUserAuthentication(
        @Field("username") username: String,
        @Field("password") password: String
    ): Observable<UserResponse>

    /**
     * Get user profile data from remote
     *
     * @param identifier type int i.e. user id
     * @return observable UserProfileResponse wrapper
     */
    @GET("users/{id}/profile")
    fun fetchUserProfile(
        @Path("id") identifier: String
    ): Observable<UserProfileResponse>

    /**
     * Get change password response from remote
     *
     * @param identifier type int i.e. user id
     * @param oldPass type string user current password
     * @param newPass type string user new password
     * @return observable UserChangePasswordResponse wrapper
     */
    @FormUrlEncoded
    @POST("users/change-password")
    fun requestUserChangePassword(
        @Field("id") identifier: String,
        @Field("oldPass") oldPass: String,
        @Field("newPass") newPass: String
    ): Observable<UserChangePasswordResponse>

}