package com.example.demo.app.user

import com.example.demo.app.user.dtos.UserDto
import io.r2dbc.spi.Row
import org.springframework.stereotype.Component
import java.time.ZonedDateTime
import java.util.function.BiFunction

@Component
class UserMapper: BiFunction<Row, Any, UserDto> {
    override fun apply(row: Row, o: Any): UserDto {
        return UserEntity(
                row.get("id") as Long,
                row.get("username") as String,
                row.get("email") as String,
            row.get("password") as String,
            (row.get("createdAt") as ZonedDateTime).toLocalDateTime(),
            (row.get("updatedAt") as ZonedDateTime).toLocalDateTime(),
        ).toUserDto()
    }
}