package com.codelytical.core.repository

import com.codelytical.core.model.User
import kotlinx.coroutines.flow.Flow
import javax.inject.Singleton

@Singleton
interface NotyUserRepository {

    /**
     * Returns a user by username
     *
     * @param username A username.
     */
    fun getUserByUsername(username: String): Flow<User?>

    /**
     * Creates a new user
     *
     * @param username Username
     * @param password Password
     */
    suspend fun createUser(username: String, password: String): Either<String>

    /**
     * Logs in a user
     *
     * @param username Username
     * @param password Password
     */
    suspend fun login(username: String, password: String): Either<String>

    companion object {
        private const val TOKEN_LENGTH = 30
        fun generateToken(): String {
            val chars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789"
            return (1..TOKEN_LENGTH)
                .map { chars.random() }
                .joinToString("")
        }
    }
}
