package com.cheise_proj.presentation.mapper.user

import com.cheise_proj.domain.model.UserEntity
import com.cheise_proj.presentation.mapper.base.Mapper
import com.cheise_proj.presentation.model.User

/**
 * User Entity Mapper
 * @property UserEntityMapper
 * @author Kelvin Birikorang
 */
class UserEntityMapper : Mapper<UserEntity, User> {
    /**
     * @param e provide user model
     * @return UserEntity maps user to userEntity model
     */
    override fun from(e: User): UserEntity {
        return UserEntity(
            id = e.id,
            username = e.username,
            email = e.email,
            password = e.password
        )
    }

    /**
     * @param t provide userEntity model
     * @return User maps userEntity to user model
     */
    override fun to(t: UserEntity): User {
        return User(
            id = t.id,
            username = t.username,
            email = t.email,
            password = t.password
        )
    }
}