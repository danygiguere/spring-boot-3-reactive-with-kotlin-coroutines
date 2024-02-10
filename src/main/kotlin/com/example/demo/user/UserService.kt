package com.example.demo.user

import org.springframework.stereotype.Service

@Service
class UserService(val repository: UserRepository) {
    suspend fun findById(id: Long): UserDto? =
            repository.findById(id)

    suspend fun create(userDto: UserDto): UserDto? =
            repository.create(userDto)
}