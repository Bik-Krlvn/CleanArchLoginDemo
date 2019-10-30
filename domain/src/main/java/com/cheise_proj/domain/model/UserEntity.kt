package com.cheise_proj.domain.model

data class UserEntity(
    val id: Int,
    val username: String,
    val email: String,
    val password: String
)

data class UserProfileEntity(
    val id: Int,
    val username: String,
    val email: String,
    val name: String,
    val dob: String
)