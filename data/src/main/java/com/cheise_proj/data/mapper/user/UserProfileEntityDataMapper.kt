package com.cheise_proj.data.mapper.user

import com.cheise_proj.data.mapper.base.Mapper
import com.cheise_proj.data.model.UserProfileData
import com.cheise_proj.domain.model.UserProfileEntity
import javax.inject.Inject

class UserProfileEntityDataMapper @Inject constructor() : Mapper<UserProfileEntity, UserProfileData> {
    override fun from(e: UserProfileData): UserProfileEntity {
        return UserProfileEntity(
            id = e.id,
            username = e.username,
            email = e.email,
            name = e.name,
            dob = e.dob
        )
    }

    override fun to(t: UserProfileEntity): UserProfileData {
        return UserProfileData(
            id = t.id,
            username = t.username,
            email = t.email,
            name = t.name,
            dob = t.dob
        )
    }
}