package com.cheise_proj.data.utils

import com.cheise_proj.data.model.UserData
import com.cheise_proj.data.model.UserProfileData

class UserDataGenerator {
   companion object{
       fun generateProfile(): UserProfileData {
           return UserProfileData(
               "1",
               "test username",
               "test email",
               "test name",
               "2019-09-12"
           )
       }

       fun generateUserData(): UserData {
           return UserData(
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