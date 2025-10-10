package com.example.pt_news_app.domain.usecase

import com.example.pt_news_app.domain.model.User
import com.example.pt_news_app.domain.repository.UserRepository

class SignUpUseCase(private val repo: UserRepository) {

    suspend operator fun invoke(
        firstname: String,
        lastname: String,
        email: String,
        password: String,
        confirmPassword: String

        // match the password and confirm password
    ): Result<User> {

        if (firstname.isBlank()) {
            return Result.failure(IllegalArgumentException("First name is required!"))
        } // validation is pending
        //password needs to be sha
        if (password != confirmPassword) return Result.failure(IllegalArgumentException("Passwords do not match"))

        val passwordHash = password



        return repo.create(firstname.trim(), lastname.trim(), email.trim(), passwordHash)
    }
}