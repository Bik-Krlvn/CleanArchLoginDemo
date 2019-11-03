package com.cheise_proj.remote.model.user

import com.google.gson.annotations.SerializedName

data class UserProfileNetwork(
    @SerializedName("user")
    var id: String,
    var username: String,
    var email: String,
    var name: String,
    var dob: String
)