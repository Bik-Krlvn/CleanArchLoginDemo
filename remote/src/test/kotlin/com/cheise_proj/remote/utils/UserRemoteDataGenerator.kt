package com.cheise_proj.remote.utils

import com.cheise_proj.remote.model.user.*

class UserRemoteDataGenerator {
    companion object {
        fun generateProfileDataResponse(): UserProfileResponse {
            return UserProfileResponse(
                status = 200,
                message = "available profile",
                profileNetwork = UserProfileNetwork(
                    1,
                    "test username",
                    "test email",
                    "test name",
                    "2019-09-12"
                )
            )
        }

        fun generateUserDataResponse(): UserResponse {
            return UserResponse(
                status = 200,
                message = "login successful",
                userNetwork = UserNetwork(
                    1,
                    "test username",
                    "test email",
                    "test password"
                )
            )
        }

        fun generateChangePasswordResponse(): UserChangePasswordResponse {
            return UserChangePasswordResponse(
                status = 200,
                message = "change password request successful"
            )
        }

        fun generateChangePassword(): ChangePasswordParams {
            return ChangePasswordParams(1, "test password", "test new password")
        }
    }

    data class ChangePasswordParams(val identifier: Int, val oldPass: String, val newPass: String)

}