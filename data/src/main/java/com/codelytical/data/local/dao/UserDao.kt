package com.codelytical.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.codelytical.data.local.entity.UserEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {

    @Query("SELECT * FROM users WHERE username = :username")
    fun getUserByUsername(username: String): Flow<UserEntity?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun createUser(user: UserEntity)

    @Query("SELECT * FROM users WHERE token = :token")
    fun getUserByToken(token: String): Flow<UserEntity?>
}
