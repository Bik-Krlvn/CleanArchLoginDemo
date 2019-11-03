package com.cheise_proj.local.mapper.user

import com.cheise_proj.data.model.UserData
import com.cheise_proj.local.mapper.base.Mapper
import com.cheise_proj.local.model.UserLocal
import javax.inject.Inject

/**
 * UserDataLocalMapper maps to userData from data module
 *
 * @author Kelvin Birikorang
 */
class UserDataLocalMapper @Inject constructor() : Mapper<UserData, UserLocal> {
    /**
     * Maps from userLocal to UserData model
     *
     * @param e type UserLocal
     * @return maps userLocal to UserData model
     */
    override fun from(e: UserLocal): UserData {
        return UserData(
            id = e.id,
            username = e.username,
            email = e.email,
            password = e.password
        )
    }

    /**
     * Maps userData to UserLocal model
     *
     * @param t type UserData
     * @return maps userData to UserLocal model
     */
    override fun to(t: UserData): UserLocal {
        return UserLocal(
            id = t.id,
            username = t.username,
            email = t.email,
            password = t.password
        )
    }

}