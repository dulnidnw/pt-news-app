package com.example.pt_news_app.domain.usecase

 
import com.example.pt_news_app.domain.model.User
import com.example.pt_news_app.domain.repository.UserRepository

class LoginUserUseCase(private val repo: UserRepository) {

    suspend operator fun invoke(
        email: String,
        password: String
    ): Result<User> {
        if (email.isBlank() || password.isBlank()) {
            return Result.failure(IllegalArgumentException("Email and password are required"))
        }
        return repo.login(email.trim(), password)
    }
}