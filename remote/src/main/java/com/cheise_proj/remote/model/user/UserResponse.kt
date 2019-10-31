package com.cheise_proj.remote.model.user

data class UserResponse(
    val status:Int,
    val message:String,
    val userNetwork: UserNetwork?
)