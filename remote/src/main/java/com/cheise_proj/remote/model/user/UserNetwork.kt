package com.cheise_proj.remote.model.user

import com.google.gson.annotations.SerializedName

data class UserNetwork(
    @SerializedName("_id")
    var id: String,
    var username: String,
    var email: String,
    var password: String
)