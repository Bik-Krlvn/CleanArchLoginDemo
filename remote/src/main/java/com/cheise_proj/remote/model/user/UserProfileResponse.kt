package com.cheise_proj.remote.model.user

import com.google.gson.annotations.SerializedName

/**
 * Remote user profile json response object
 *
 * @property status http response code
 * @property message remote message
 * @property profileNetwork UserProfileNetwork model
 */
data class UserProfileResponse(
    val status: Int,
    val message: String,
    @SerializedName("data")
    val profileNetwork: UserProfileNetwork?
)