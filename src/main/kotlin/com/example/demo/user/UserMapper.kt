package com.example.demo.user

import com.example.demo.post.Post
import com.example.demo.post.toDto
import io.r2dbc.spi.Row
import org.springframework.stereotype.Component
import java.util.function.BiFunction

@Component
class UserMapper: BiFunction<Row, Any, UserDto> {
    override fun apply(row: Row, o: Any): UserDto {
        val user = User(
                row.get("id") as Long,
                row.get("username") as String,
                row.get("email") as String,
                row.get("phoneNumber") as String,
        ).toDto()
        return user;
    }
}