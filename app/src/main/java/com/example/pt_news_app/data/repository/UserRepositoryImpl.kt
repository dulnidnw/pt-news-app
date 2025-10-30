package com.example.pt_news_app.data.repository

import android.database.sqlite.SQLiteConstraintException
import com.example.pt_news_app.data.local.dao.UserDao
import com.example.pt_news_app.data.local.entity.UserEntity
import com.example.pt_news_app.domain.model.User
import com.example.pt_news_app.domain.repository.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class UserRepositoryImpl(private val dao: UserDao) : UserRepository {
    suspend fun registeruser(fName: String, user: UserEntity): Boolean {
        return if (dao.isUserExists(user.email) == 0) {
            dao.registerUser(user)
            true
        } else {
            false
        }
    }

    fun loginUser(username: String, password: String): UserEntity? {
        return dao.loginUser(username, password)
    }

    override suspend fun create(
        firstName: String,
        lastName: String,
        email: String,
        password: String
    ): Result<User> = withContext(Dispatchers.IO) {
        runCatching {
            val id = dao.registerUser(
                UserEntity(
                    firstName = firstName,
                    lastName = lastName,
                    email = email,
                    password = password
                )
            )
            User(0, firstName, lastName, email, password)
        }.recoverCatching{throwable ->
            if (throwable is SQLiteConstraintException) {
                throw IllegalStateException("Email is already registered")
            } else throw throwable
        }
    }

    override suspend fun login(email: String, password: String): Result<User> = withContext(Dispatchers.IO) {
        runCatching {
            val entity = dao.loginUser(email, password)
                ?: throw IllegalArgumentException("Invalid email or password")
            User(
                id = entity.id,
                firstName = entity.firstName,
                lastName = entity.lastName,
                email = entity.email,
                password = entity.password
            )
        }
    }

//    override suspend fun createUser(user: User) {
//        TODO("Not yet implemented")
//    }

    override suspend fun getUserByEmail(email: String): User? {
        val entity = dao.getUserByEmail(email) ?: return null
        return User(entity.id, firstName = entity.firstName,lastName = entity.lastName, email = entity.email, password = entity.password)
    }
    override suspend fun getAllUsers(): List<User> {
        return dao.getAllUsers().map { User(it.id,  firstName = it.firstName,lastName = it.lastName, email = it.email, password = it.password) }
    }

    override suspend fun updateUser(user: User) {
        dao.update(UserEntity(user.id, firstName = user.firstName,lastName = user.lastName, email = user.email, password = user.password))
    }

    override suspend fun deleteUser(user: User) {
        dao.delete(UserEntity(user.id, firstName = user.firstName,lastName = user.lastName, email = user.email, password = user.password))
    }
}