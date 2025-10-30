package com.example.pt_news_app.domain.mapper

import com.example.pt_news_app.data.local.entity.UserEntity

fun UserEntity.toDomain() = UserEntity(id, firstName, lastName, email,  password)
fun UserEntity.toEntity() = UserEntity(id, firstName, lastName, email, password)