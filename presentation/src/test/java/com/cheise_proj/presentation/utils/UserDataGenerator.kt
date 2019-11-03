package com.cheise_proj.presentation.utils

import com.cheise_proj.presentation.model.User
import com.cheise_proj.presentation.model.UserProfile

class UserDataGenerator {
    companion object{
        fun generateProfile(): UserProfile {
            return UserProfile(
                "1",
                "test username",
                "test email",
                "test name",
                "2019-09-12"
            )
        }

        fun generateUserData(): User {
            return User(
                "1",
                "test username",
                "test email",
                "test password"
            )
        }

        fun generateChangePassword(): ChangePasswordParams {
            return ChangePasswordParams("1", "test password", "test new password")
        }
    }

    data class ChangePasswordParams(val identifier: String, val oldPass: String, val newPass: String)
}