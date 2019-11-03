package com.cheise_proj.domain.model

data class UserEntity(
    val id: String,
    val username: String,
    val email: String,
    val password: String
)

data class UserProfileEntity(
    val id: String,
    val username: String,
    val email: String,
    val name: String,
    val dob: String
)