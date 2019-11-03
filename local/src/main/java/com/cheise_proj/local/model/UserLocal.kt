package com.cheise_proj.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class UserLocal(
    @PrimaryKey
    var id: String,
    var username: String,
    var email: String,
    var password: String
)

@Entity(tableName = "profile")
data class UserProfileLocal(
    @PrimaryKey
    var id: String,
    var username: String,
    var email: String,
    var name: String,
    var dob: String
)