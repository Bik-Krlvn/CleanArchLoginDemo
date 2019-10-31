package com.cheise_proj.remote.mapper.user

import com.cheise_proj.data.model.UserProfileData
import com.cheise_proj.remote.mapper.base.Mapper
import com.cheise_proj.remote.model.user.UserProfileNetwork
import javax.inject.Inject

/**
 * User Profile Data mapper from data module
 *
 */
class UserProfileDataRemoteMapper @Inject constructor() :
    Mapper<UserProfileData, UserProfileNetwork> {
    /**
     * Maps userProfileNetwork to userProfileData
     *
     * @param e type UserProfileNetwork
     * @return userProfileNetwork to userProfileData
     */
    override fun from(e: UserProfileNetwork): UserProfileData {
        return UserProfileData(
            id = e.id,
            username = e.username,
            email = e.email,
            name = e.name,
            dob = e.dob
        )
    }

    /**
     * Maps userProfileData to UserProfileNetwork
     *
     * @param t type UserProfileData
     * @return userProfileData to UserProfileNetwork
     */
    override fun to(t: UserProfileData): UserProfileNetwork {
        return UserProfileNetwork(
            id = t.id,
            username = t.username,
            email = t.email,
            name = t.name,
            dob = t.dob
        )
    }
}