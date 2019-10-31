package com.cheise_proj.remote.mapper.user

import com.cheise_proj.data.model.UserData
import com.cheise_proj.remote.mapper.base.Mapper
import com.cheise_proj.remote.model.user.UserNetwork
import javax.inject.Inject

/**
 * User Data Remote mapper from data module
 *
 */
class UserDataRemoteMapper @Inject constructor() : Mapper<UserData, UserNetwork> {
    /**
     * Maps userNetwork to userData from data module
     *
     * @param e type UserNetwork
     * @return userNetwork to userData from data module
     */
    override fun from(e: UserNetwork): UserData {
        return UserData(
            id = e.id,
            username = e.username,
            email = e.email,
            password = e.password
        )
    }

    /**
     * Maps userData from data module to UserNetwork
     *
     * @param t type UserData
     * @return userData from data module to UserNetwork
     */
    override fun to(t: UserData): UserNetwork {
        return UserNetwork(
            id = t.id,
            username = t.username,
            email = t.email,
            password = t.password
        )
    }
}