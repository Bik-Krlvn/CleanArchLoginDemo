package com.cheise_proj.remote.model.user

import com.google.gson.annotations.SerializedName

data class UserResponse(
    val status:Int,
    val message:String,
    @SerializedName("data")
    val userNetwork: UserNetwork
)