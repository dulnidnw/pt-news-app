package com.example.pt_news_app.domain.usecase

import android.util.Patterns
import com.example.pt_news_app.domain.model.User
import com.example.pt_news_app.domain.repository.UserRepository
import java.security.MessageDigest

class SignUpUseCase(private val repo: UserRepository) {

    suspend operator fun invoke(
        firstname: String,
        lastname: String,
        email: String,
        password: String,
        confirmPassword: String

    ): Result<User> {

        if (firstname.isBlank()) {
            return Result.failure(IllegalArgumentException("First name is required!"))
        }

        if (lastname.isBlank()) {
            return Result.failure(IllegalArgumentException("Last name is required!"))
        }

        if (email.isBlank()) {
            return Result.failure(IllegalArgumentException("Email is required!"))
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            return Result.failure(IllegalArgumentException("Please enter a valid email address"))
        }

        if (password.isBlank()) {
            return Result.failure(IllegalArgumentException("Password is required!"))
        }

        if (confirmPassword.isBlank()) {
            return Result.failure(IllegalArgumentException("Confirm password is required!"))
        }

        if (password != confirmPassword) {
            return Result.failure(IllegalArgumentException("Passwords do not match"))
        }

        if (password.length < 8) {
            return Result.failure(IllegalArgumentException("Password must be at least 8 characters long"))
        }
        val passwordHash = hashPassword(password)
        return try {
            repo.create(
                firstname.trim(),
                lastname.trim(),
                email.trim(),
                passwordHash
            )
        } catch (e: Exception) {
            Result.failure(e)
        }


        return repo.create(firstname.trim(), lastname.trim(), email.trim(), passwordHash)
    }
    private fun hashPassword(password: String): String {
        val md = MessageDigest.getInstance("SHA-256")
        val bytes = md.digest(password.toByteArray())
        return bytes.joinToString("") { "%02x".format(it) }
    }
}