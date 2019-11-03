package com.cheise_proj.local.utils

import com.cheise_proj.local.model.UserLocal
import com.cheise_proj.local.model.UserProfileLocal

class UserLocalGenerator {
    companion object {
        fun generateUserData(): UserLocal {
            return UserLocal(
                "1",
                "test username",
                "test email",
                "test password"
            )
        }

        fun generateProfile(): UserProfileLocal {
            return UserProfileLocal(
                "1",
                "test username",
                "test email",
                "test name",
                "2019-09-12"
            )
        }

        fun generateChangePassword(): ChangePasswordParams {
            return ChangePasswordParams("1", "test password", "test new password")
        }
    }

}

data class ChangePasswordParams(val identifier: String, val oldPass: String, val newPass: String)
