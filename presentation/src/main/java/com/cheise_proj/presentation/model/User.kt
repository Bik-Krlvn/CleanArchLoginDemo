package com.cheise_proj.presentation.model

data class User(
    val id: String,
    val username: String,
    val email: String,
    val password: String
)

data class UserProfile(
    val id: String,
    val username: String,
    val email: String,
    val name: String,
    val dob: String
)