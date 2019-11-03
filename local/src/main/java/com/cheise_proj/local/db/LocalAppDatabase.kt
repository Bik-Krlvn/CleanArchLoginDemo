package com.cheise_proj.local.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.cheise_proj.local.db.dao.UserDao
import com.cheise_proj.local.model.UserLocal
import com.cheise_proj.local.model.UserProfileLocal

@Database(entities = [UserLocal::class, UserProfileLocal::class], version = 1, exportSchema = false)
abstract class LocalAppDatabase : RoomDatabase() {
    companion object {
        private val DATABASE_NAME = "local.db"
        private val LOCK = Any()
        @Volatile
        private var INSTANCE: LocalAppDatabase? = null

        fun getInstance(context: Context): LocalAppDatabase {
            if (INSTANCE == null) {
                synchronized(LOCK) {
                    if (INSTANCE == null) {
                        INSTANCE = Room.databaseBuilder(
                            context,
                            LocalAppDatabase::class.java,
                            DATABASE_NAME
                        ).build()
                    }
                }
            }
            return INSTANCE!!
        }
    }

    abstract fun userDao(): UserDao
}