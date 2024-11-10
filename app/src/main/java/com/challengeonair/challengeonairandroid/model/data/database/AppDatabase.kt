package com.challengeonair.challengeonairandroid.model.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.challengeonair.challengeonairandroid.model.data.dao.AlarmDao
import com.challengeonair.challengeonairandroid.model.data.dao.SearchHistoryDao
import com.challengeonair.challengeonairandroid.model.data.dao.UserProfileDao
import com.challengeonair.challengeonairandroid.model.data.entity.UserProfile

@Database(entities = [UserProfile::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userProfileDao(): UserProfileDao
    abstract fun alarmDao(): AlarmDao
    abstract fun searchHistoryDao(): SearchHistoryDao

    companion object {
        @Volatile private var instance: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase =
            instance ?: synchronized(this) {
                instance ?: Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java, "app_database"
                ).build().also { instance = it }
            }
    }
}