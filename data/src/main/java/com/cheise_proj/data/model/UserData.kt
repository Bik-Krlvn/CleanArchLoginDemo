package com.cheise_proj.data.model

data class UserData(
    var id: String,
    var username: String,
    var email: String,
    var password: String
)

data class UserProfileData(
   var id: String,
   var username: String,
   var email: String,
   var name: String,
   var dob: String
)