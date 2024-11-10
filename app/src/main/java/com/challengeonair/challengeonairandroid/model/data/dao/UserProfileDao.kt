package com.challengeonair.challengeonairandroid.model.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.challengeonair.challengeonairandroid.model.data.entity.UserProfile

@Dao
interface UserProfileDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: UserProfile)

    @Update
    suspend fun updateUser(user: UserProfile)

    @Query("SELECT * FROM user_profile LIMIT 1")
    suspend fun getUser(): UserProfile?

    @Delete
    suspend fun deleteUser(user: UserProfile)
}