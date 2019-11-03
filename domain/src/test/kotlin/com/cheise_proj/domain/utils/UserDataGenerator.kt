package com.cheise_proj.domain.utils

import com.cheise_proj.domain.model.UserEntity
import com.cheise_proj.domain.model.UserProfileEntity
import com.cheise_proj.domain.useCases.user.UserChangePasswordTask

class UserDataGenerator {
    companion object {
        fun generateProfile(): UserProfileEntity {
            return UserProfileEntity(
                "1",
                "test username",
                "test email",
                "test name",
                "2019-09-12"
            )
        }

        fun generateUserData(): UserEntity {
            return UserEntity(
                "1",
                "test username",
                "test email",
                "test password"
            )
        }

        fun generateChangePassword():UserChangePasswordTask.ChangePasswordParams{
            return UserChangePasswordTask.ChangePasswordParams("1","test password","test new password")
        }
    }
}