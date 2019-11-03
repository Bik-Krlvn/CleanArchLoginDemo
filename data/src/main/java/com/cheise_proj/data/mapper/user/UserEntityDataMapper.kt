package com.cheise_proj.data.mapper.user

import com.cheise_proj.data.mapper.base.Mapper
import com.cheise_proj.data.model.UserData
import com.cheise_proj.domain.model.UserEntity
import javax.inject.Inject

class UserEntityDataMapper @Inject constructor(): Mapper<UserEntity, UserData> {
    override fun from(e: UserData): UserEntity {
        return UserEntity(
            id = e.id,
            username = e.username,
            email = e.email,
            password = e.password
        )
    }

    override fun to(t: UserEntity): UserData {
        return UserData(
            id = t.id,
            username = t.username,
            email = t.email,
            password = t.password
        )
    }
}