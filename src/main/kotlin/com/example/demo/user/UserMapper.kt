package com.example.demo.user

import io.r2dbc.spi.Row
import org.springframework.stereotype.Component
import java.util.function.BiFunction

@Component
class UserMapper: BiFunction<Row, Any, UserDto> {
    override fun apply(row: Row, o: Any): UserDto {
        return User(
                row.get("id") as Long,
                row.get("username") as String,
                row.get("email") as String,
                null.toString()
        ).toDto()
    }
}