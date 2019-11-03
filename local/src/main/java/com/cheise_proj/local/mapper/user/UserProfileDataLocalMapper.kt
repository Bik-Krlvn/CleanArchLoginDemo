package com.cheise_proj.local.mapper.user

import com.cheise_proj.data.model.UserProfileData
import com.cheise_proj.local.mapper.base.Mapper
import com.cheise_proj.local.model.UserProfileLocal
import javax.inject.Inject

/**
 * UserProfileDataLocalMapper maps to userProfileData from data module
 *
 * @author Kelvin Birikorang
 */
class UserProfileDataLocalMapper @Inject constructor() : Mapper<UserProfileData, UserProfileLocal> {
    /**
     * Maps userProfileLocal model to UserProfileData model
     *
     * @param e type UserProfileLocal
     * @return maps userProfileLocal model to UserProfileData model
     */
    override fun from(e: UserProfileLocal): UserProfileData {
        return UserProfileData(
            id = e.id,
            username = e.username,
            email = e.email,
            name = e.name,
            dob = e.dob
        )
    }

    /**
     * Maps userProfileData to UserProfileLocal
     *
     * @param t type UserProfileData
     * @return maps userProfileData to UserProfileLocal
     */
    override fun to(t: UserProfileData): UserProfileLocal {
        return UserProfileLocal(
            id = t.id,
            username = t.username,
            email = t.email,
            name = t.name,
            dob = t.dob
        )
    }
}