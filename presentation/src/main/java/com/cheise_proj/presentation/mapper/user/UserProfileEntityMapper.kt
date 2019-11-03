package com.cheise_proj.presentation.mapper.user

import com.cheise_proj.domain.model.UserProfileEntity
import com.cheise_proj.presentation.mapper.base.Mapper
import com.cheise_proj.presentation.model.UserProfile
import javax.inject.Inject

/**
 * User Profile entity mapper
 *
 * @author Kelvin Birikorang
 * @constructor
 */
class UserProfileEntityMapper  @Inject constructor(): Mapper<UserProfileEntity, UserProfile> {
    /**
     * @param e provide userProfile model
     * @return UserProfileEntity maps userProfile to userProfileEntity model
     */
    override fun from(e: UserProfile): UserProfileEntity {
        return UserProfileEntity(
            id = e.id,
            username = e.username,
            email = e.email,
            name = e.name,
            dob = e.dob
        )
    }

    /**
     * @param t provide userProfileEntity model
     * @return UserProfile maps userProfileEntity to userProfile model
     */
    override fun to(t: UserProfileEntity): UserProfile {
        return UserProfile(
            id = t.id,
            username = t.username,
            email = t.email,
            name = t.name,
            dob = t.dob
        )
    }
}