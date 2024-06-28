package com.codelytical.data.local.repository

import com.codelytical.core.model.User
import com.codelytical.core.repository.Either
import com.codelytical.core.repository.NotyUserRepository
import com.codelytical.data.local.dao.UserDao
import com.codelytical.data.local.entity.UserEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class NotyLocalUserRepository @Inject constructor(
    private val userDao: UserDao
) : NotyUserRepository {

    override fun getUserByUsername(username: String): Flow<User?> = userDao.getUserByUsername(username)
        .map { it?.let { User(it.username, it.password, it.token) } }

    override suspend fun createUser(username: String, password: String): Either<String> = runCatching {
        val token = NotyUserRepository.generateToken()
        userDao.createUser(UserEntity(username, password, token))
        Either.success(token)
    }.getOrDefault(Either.error("Unable to create a new user"))

    override suspend fun login(username: String, password: String): Either<String> = runCatching {
        val user = userDao.getUserByUsername(username).first()
        if (user != null && user.password == password) {
            Either.success(user.token)
        } else {
            Either.error("Invalid username or password")
        }
    }.getOrDefault(Either.error("Unable to login"))
}

